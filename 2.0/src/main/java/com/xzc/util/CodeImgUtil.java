package com.xzc.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 验证码
 *
 * @author 熊智聪
 * @create 2018-05-29 12:40
 **/
public class CodeImgUtil {

    final private int WIDTH = 100;
    final private int HEIGHT = 34;

    public BufferedImage codeImg(String code) {
        BufferedImage img = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.fillRect(0, 0, WIDTH, WIDTH);
        g.setColor(Color.BLUE);
        drawLines(g);
        drawCode(g, code);
        return img;
    }

    private void drawLines(Graphics g) {
        int count = randomNumber(11);
        for (int i = 0; i < count; i++) {
            g.setColor(randomColor());
            int x1 = randomNumber(WIDTH);
            int x2 = randomNumber(WIDTH);
            int y1 = randomNumber(HEIGHT);
            int y2 = randomNumber(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawCode(Graphics g, String string) {
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        for (int i = 0; i < string.length(); i++) {
            g.setColor(randomColor());
            g.drawString("" + string.charAt(i), 20 * i + 11, 25);
        }
    }

    private int randomNumber(int max) {
        return (int) (max * Math.random());
    }

    public String randomChar(int num) {
        String string = "";
        String stringArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < num; i++) {
            string += stringArray.charAt(randomNumber(stringArray.length()));
        }
        return string;
    }

    private Color randomColor() {
        return new Color(randomNumber(256), randomNumber(256), randomNumber(256));
    }

}
