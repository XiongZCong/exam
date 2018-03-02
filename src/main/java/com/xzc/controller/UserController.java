package com.xzc.controller;

import com.xzc.bean.User;
import com.xzc.redis.RedisService;
import com.xzc.redis.UserKey;
import com.xzc.result.Result;
import com.xzc.service.UserService;
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

    /*@ResponseBody
    @RequestMapping("/login")
    public Result login(User user) {
        System.out.println(user.toString());
        userService.login(user);
        return Result.success(user);
    }*/
    @RequestMapping("login")
    public String login(HttpServletResponse response,User user) {
        System.out.println(user.toString());
        //userService.getByToken(response)
        return "hello";
    }

    @ResponseBody
    @RequestMapping("/redis")
    public Result redis(Model model) {
        User user = new User();
        user.setUserId(123L);
        user.setUsername("456");
        user.setPassword("123456");
        redisService.set(UserKey.getById, "123", user);
        return Result.success(user);
    }

}
