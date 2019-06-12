package com.xzc.util;

import java.awt.*;
import java.util.Arrays;
import java.util.UUID;

/**
 * 基础工具类
 *
 * @author 熊智聪
 * @create 2018-04-13 14:38
 **/
public class BaseUtil {

    /**
     * 获取UUID字符串
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取0-max的随机整数
     *
     * @param max 最大值
     * @return
     */
    public static int getNumber(int max) {
        return (int) (max * Math.random());
    }

    /**
     * 获取随机字符串
     *
     * @param num 随机字符串的位数
     * @return
     */
    public static String getString(int num) {
        String string = "";
        String stringArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < num; i++) {
            string += stringArray.charAt(getNumber(stringArray.length()));
        }
        return string;
    }

    /**
     * 获取随机颜色
     *
     * @return
     */
    public static Color getColor() {
        return new Color(getNumber(256), getNumber(256), getNumber(256));
    }

    /**
     * 字符串排序
     *
     * @param string 需要排序的字符串
     * @return 排序后字符串
     */
    public static String sortString(String string) {
        char[] chars = string.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
