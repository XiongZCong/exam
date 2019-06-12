package com.xzc.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * http相关
 *
 * @author 熊智聪
 * @create 2019-03-09 12:11
 **/
public class HttpController {

    public static String getIp() {
        return HttpController.getRequest().getRemoteHost();
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static void printSession() {
        System.err.println(getSession().getId());
        Enumeration<?> enumeration = getSession().getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement().toString();
            Object value = getSession().getAttribute(name);
            System.out.println(name + "<<<<<<=========================>>>>>>>" + value);
        }
    }

    public static Cookie getCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(Integer.MAX_VALUE);
        return cookie;
    }
}
