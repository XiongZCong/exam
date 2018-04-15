package com.xzc.util;

import com.alibaba.fastjson.JSON;
import com.xzc.bean.User;

import java.util.ArrayList;
import java.util.List;

public class SBUtil {
    public static void main(String[] args) {
        User user = new User();
        User user2 = new User(147L, "258", "369");
        user.setUserId(123L);
        user.setUsername("456");
        user.setPassword("123456");
        User user1 = user;
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        list.add(user2);
        System.out.println(JSON.toJSONString(user));
        System.out.println(SBUtil.beanToString(user));
        System.out.println(JSON.parse(JSON.toJSONString(user)).toString());
        System.out.println(JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(user)), User.class).toString());
    }

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}
