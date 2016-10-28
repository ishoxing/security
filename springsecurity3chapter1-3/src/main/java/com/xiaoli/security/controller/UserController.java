package com.xiaoli.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description: [描述该类概要功能介绍]
 * Created on 2016/10/25
 *
 * @author <a href="mailto: liruifeng@camelotchina.com">中文名字</a>
 * @version 1.0
 *          Copyright (c) 2016年 北京柯莱特科技有限公司 交付部
 */
@Controller
public class UserController {
    @Autowired
    InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @RequestMapping("account")
    public String toAccount() {
        return "account";
    }

    @RequestMapping("toChangePassword")
    public String toChangePassword() {
        return "change_password";
    }

    @RequestMapping("changePassword")
    public String changePassword(String oldPassword, String newPassword) {
        inMemoryUserDetailsManager.changePassword(oldPassword, newPassword);
        SecurityContextHolder.clearContext();
        return "redirect:/welcome";
    }

}
