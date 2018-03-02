package com.xzc.service;

import com.xzc.bean.User;
import com.xzc.mapper.UserMapper;
import com.xzc.redis.MiaoshaUserKey;
import com.xzc.redis.RedisService;
import com.xzc.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class UserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    public UserMapper userMapper;
    @Autowired
    public RedisService redisService;


    public User login(HttpServletResponse response, User user) {
        User user2 = userMapper.login(user.getUsername(), user.getPassword());
        System.out.println(user.toString());
        String token = UUIDUtil.uuid();
        addCookie(response, token, user2);
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(MiaoshaUserKey.token, token, User.class);
        //延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    public User login(User user) {
        Integer login = userMapper.login2(user);
        System.out.println(login);

        return user;
    }

    public List<User> selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    public List<User> selectUserByName(String userName) {
        return userMapper.selectUserByName(userName);
    }

    public Integer register(User user) {
        return userMapper.register(user);
    }

}
