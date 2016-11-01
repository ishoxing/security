package com.xiaoli.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Description: [描述该类概要功能介绍]
 * Created on 2016/10/28
 *
 * @author <a href="mailto: liruifeng@camelotchina.com">中文名字</a>
 * @version 1.0
 *          Copyright (c) 2016年 北京柯莱特科技有限公司 交付部
 */
public interface IChangePassword extends UserDetailsService {

    void changePassword(String username, String oldPassword,String newPassword);
}
