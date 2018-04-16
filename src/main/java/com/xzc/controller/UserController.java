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
import java.util.ArrayList;
import java.util.List;

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
        User user1 = new User(14L, "25", "36");
        User user2 = new User(147L, "258", "369");
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        list.add(user2);
        redisService.set(UserKey.getById, "111", user);
        redisUtil.set(user, RedisUtil.Field_ID, "333", 60);
        RedisUtil.set(user, RedisUtil.Field_ID, "555", 60);
        RedisUtil.set(list, RedisUtil.Field_ID, "777", 60);
        System.out.println(redisService.get(UserKey.getById, "111", User.class).toString());
        System.out.println(redisUtil.get(User.class, RedisUtil.Field_ID, "333"));
        System.out.println(RedisUtil.get(User.class, RedisUtil.Field_ID, "555").getUsername());
        System.out.println(((User) RedisUtil.getList(RedisUtil.Type_ArrayList, RedisUtil.Field_ID, "777", User.class).get(0)).getUsername());
        return Result.success(user);
    }

}
