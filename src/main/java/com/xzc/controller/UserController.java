package com.xzc.controller;

import com.xzc.bean.User;
import com.xzc.redis.RedisService;
import com.xzc.redis.UserKey;
import com.xzc.result.Result;
import com.xzc.service.UserService;
import com.xzc.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    public RedisService redisService;
    @Autowired
    public com.xzc.redis.RedisUtil redisUtil;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "xzc");
        model.addAttribute("name", "nnn");
        return "hello";
    }

    @RequestMapping("toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("login")
    public String login(HttpServletResponse response, User user) {
        System.out.println(user.toString());
        //userService.getByToken(response)
        return "hello";
    }

    @ResponseBody
    @RequestMapping("/redis")
    public Result redis() {
        User user = new User();
        user.setUserId(123L);
        user.setUsername("456");
        user.setPassword("123456");
        redisService.set(UserKey.getById, "111", user);
        redisService.set(UserKey.getByName, "222", user);
        redisUtil.set(user, RedisUtil.Field_ID, "333");
        redisUtil.set(user, RedisUtil.Field_NAME, "444", 60);
        RedisUtil.set(user, RedisUtil.Field_ID, "555", 60);
        RedisUtil.set(user, RedisUtil.Field_NAME, "666", 60);
        System.out.println(redisUtil.get(User.class, RedisUtil.Field_ID, "333"));
        System.out.println(redisService.get(UserKey.getById, "111", User.class).toString());
        System.out.println(RedisUtil.get(User.class, RedisUtil.Field_ID, "555").toString());
        return Result.success(user);
    }

}
