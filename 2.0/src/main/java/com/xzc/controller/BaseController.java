package com.xzc.controller;

import com.xzc.model.User;

/**
 * 基础controller
 *
 * @author 熊智聪
 * @create 2019-03-09 18:03
 **/
public class BaseController extends HttpController {

    public User getUser() {
        return (User) getSession().getAttribute("user");
    }

    public Long getUserId() {
        return getUser().getUserId();
    }
}
