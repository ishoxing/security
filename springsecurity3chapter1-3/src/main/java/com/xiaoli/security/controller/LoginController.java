package com.xiaoli.security.controller;

import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: [描述该类概要功能介绍]
 * Created on 2016/10/25
 *
 * @author <a href="mailto: liruifeng@camelotchina.com">中文名字</a>
 * @version 1.0
 *          Copyright (c) 2016年 北京柯莱特科技有限公司 交付部
 */
@Controller
public class LoginController {

    @RequestMapping("login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("welcome")
    public String welcome(HttpServletRequest request){
        String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
        request.getSession().setAttribute("name",currentName);
        return "index";
    }

    @RequestMapping
    public String index(HttpServletRequest request){
        String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
        request.getSession().setAttribute("name",currentName);
        return "index";
    }



}
