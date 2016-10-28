package com.xiaoli.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Description: [在remember-me的基础上加上ip的校验]
 * Created on 2016/10/27
 *
 * @author <a href="mailto: liruifeng@camelotchina.com">中文名字</a>
 * @version 1.0
 *          Copyright (c) 2016年 北京柯莱特科技有限公司 交付部
 */
public class IPTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
    public IPTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    //定义线程变量
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    public HttpServletRequest getContext() {
        return requestHolder.get();
    }

    public void setContext(HttpServletRequest context) {
        requestHolder.set(context);
    }


//    步骤1：在登陆后执行一系列的操作
//            super.onLoginSuccess触发步骤2
    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        try {
            setContext(request);
            super.onLoginSuccess(request, response, successfulAuthentication);
        } finally {
            setContext(null);
        }

    }

//    步骤2：md5的时候加上ip的值
    @Override
    protected String makeTokenSignature(long tokenExpiryTime, String username, String password) {
        return DigestUtils.md5DigestAsHex((username + ":" + tokenExpiryTime + ":" + password + ":" + getKey() + ":" + getUserIPAddress(getContext())).getBytes());
    }

//    步骤3：将ip放入cookie参数中
    @Override
    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        // append the IP adddress to the cookie
        String[] tokensWithIPAddress =
                Arrays.copyOf(tokens, tokens.length + 1);
        tokensWithIPAddress[tokensWithIPAddress.length - 1] =
                getUserIPAddress(request);
        super.setCookie(tokensWithIPAddress, maxAge, request, response);
    }

//    步骤4：在常规验证remember-me前，先验证ip不通过直接抛错
    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
        try {
            setContext(request);
            // take off the last token
            String ipAddressToken = cookieTokens[cookieTokens.length - 1];
            if (!getUserIPAddress(request).equals(ipAddressToken)) {
                throw new InvalidCookieException("Cookie IP Address did not contain a matching IP (contained '" + ipAddressToken + "')");
            }
        } finally {
            setContext(null);
        }
        return super.processAutoLoginCookie(cookieTokens, request, response);
    }

    //获取用户ip
    protected String getUserIPAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
