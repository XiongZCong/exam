package com.xzc.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class CodeUtil {

    public void createCode(HttpServletResponse response) throws IOException {
        char[] codeList = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9'};
        int width = 80;
        int height = 25;
        Random rand = new Random();
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics pen = img.getGraphics();
        Color color = new Color(255, 228, 225);
        pen.setColor(color);
        pen.fillRect(0, 0, width, height);
        pen.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD | Font.ITALIC, 20);
        pen.setFont(font);
        int x = 10;
        String code = "";
        for (int i = 0; i < 4; i++) {
            Color color2 = new Color(rand.nextInt(225), rand.nextInt(225),
                    rand.nextInt(225));
            pen.setColor(color2);
            char c = codeList[rand.nextInt(codeList.length)];
            pen.drawString(c + "", x, 20);
            x += 15;
            code += c;
        }
        for (int i = 0; i < 3; i++) {
            Color color2 = new Color(rand.nextInt(225), rand.nextInt(225),
                    rand.nextInt(225));
            pen.setColor(color2);
            pen.drawLine(rand.nextInt(width), rand.nextInt(height),
                    rand.nextInt(width), rand.nextInt(height));
        }
        ImageIO.write(img, "jpg", response.getOutputStream());
    }

}
