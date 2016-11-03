package com.xiaoli.security.service.impl;

import com.xiaoli.security.model.CustomGrantedAuthority;
import com.xiaoli.security.model.SaltUser;
import com.xiaoli.security.service.IChangePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import sun.misc.BASE64Encoder;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Description: [描述该类概要功能介绍]
 * Created on 2016/10/28
 *
 * @author <a href="mailto: liruifeng@camelotchina.com">中文名字</a>
 * @version 1.0
 *          Copyright (c) 2016年 北京柯莱特科技有限公司 交付部
 */
public class CustomJdbcDaoImpl extends JdbcDaoImpl implements IChangePassword {
    @Autowired
    Md5PasswordEncoder passwordEncoder;
    @Autowired
    private SaltSource saltSource;

    private String groupAuthoritiesByUsernameQuery;

    public void changePassword(String username, String oldPassword, String newPassword) {
        try {
            UserDetails user = loadUserByUsername(username);
            String encodedNewPassword = passwordEncoder.encodePassword
                    (newPassword, saltSource.getSalt(user));
            String encodedOldPassword = passwordEncoder.encodePassword
                    (oldPassword, saltSource.getSalt(user));
            int update = getJdbcTemplate().update("update users set password =? where username =? and password =?", encodedNewPassword, username, encodedOldPassword);
            if (update < 1) {
                System.out.println("修改密码失败！！！");
            }
        } catch (Exception e) {
            System.out.println("修改密码失败！！！");
            System.out.println(e);
        }

    }


    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[]{username},
                new RowMapper<UserDetails>() {
                    public UserDetails mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        String username = rs.getString(1);
                        String password = rs.getString(2);
                        boolean enabled = rs.getBoolean(3);
                        String salt = rs.getString(4);
                        return new SaltUser(username, password, enabled, true, true, true,
                                AuthorityUtils.NO_AUTHORITIES, salt);
                    }

                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return super.loadUserByUsername(username);
    }

    @Override
    protected List<GrantedAuthority> loadGroupAuthorities(String username) {
        return getJdbcTemplate().query(getGroupAuthoritiesByUsernameQuery(),
                new String[] { username }, new RowMapper<GrantedAuthority>() {
                    public GrantedAuthority mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        String role = getRolePrefix() + rs.getString(3);
                        String roleDesc = rs.getString(2);

                        return new CustomGrantedAuthority(role,roleDesc);
                    }
                });
    }

    @Override
    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();
        if (!isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }

        return new SaltUser(returnUsername, userFromUserQuery.getPassword(),
                userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities, ((SaltUser) userFromUserQuery).getSalt());
    }

    public static void main(String[] args) {
        final Random r = new SecureRandom();
        for (int i = 0; i <2 ; i++) {
            byte[] salt = new byte[32];
            r.nextBytes(salt);
            String encodedSalt = new BASE64Encoder().encode(salt);
            System.out.println(encodedSalt);
        }
    }

    public String getGroupAuthoritiesByUsernameQuery() {
        return groupAuthoritiesByUsernameQuery;
    }

    @Override
    public void setGroupAuthoritiesByUsernameQuery(String groupAuthoritiesByUsernameQuery) {
        this.groupAuthoritiesByUsernameQuery = groupAuthoritiesByUsernameQuery;
    }
}
