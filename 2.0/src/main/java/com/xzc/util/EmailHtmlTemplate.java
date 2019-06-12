package com.xzc.util;

import com.xzc.model.*;

public class EmailHtmlTemplate {
    public static String registerTemplate(String text) {
        text = String.format("用户注册\n" +
                "\n" +
                "\n" +
                "这封信是由 xzc 发送的。\n" +
                "\n" +
                "您收到这封邮件，是由于在 xzc网站 获取了新用户注册地址使用了这个邮箱地址。如果您并没有访问过 xzc，或没有进行上述操作，请忽略这封邮件。\n" +
                "\n" +
                "\n" +
                "----------------------------------------------------------------------\n" +
                "新用户注册说明\n" +
                "----------------------------------------------------------------------\n" +
                "\n" +
                "\n" +
                "如果您是 xzc 的新用户，或在修改您的注册 Email 时使用了本地址，我们需要对您的地址有效性进行验证以避免垃圾邮件或地址被滥用。\n" +
                "\n" +
                "您只需点击下面的链接即可进行用户注册，以下链接有效期为1天。过期可以重新请求发送一封新的邮件验证：\n" +
                "%s \n" +
                "\n" +
                "感谢您的访问，祝您使用愉快！\n" +
                "\n" +
                "此致\n" +
                "xzc 管理团队.\n" +
                "http://www.xzc.com/\n", text);
        return text;
    }

    public static Email registerTemplate(String to, String text) {
        text = String.format("<h1 style='text-align:center'>用户注册</h1>" +
                "<p style='text-align:center'>欢迎您注册本网站，点击下面链接完成注册(1天有效时间)</p>" +
                "<p style='text-align:center'><a href='%s'>点击完成注册</a></p>", text);
        return new Email(to, "注册用户", text);
    }

    public static Email getVerificationTemplate(String to, String text) {
        text = String.format("<h1 style='text-align:center'>验证码</h1>" +
                "<p style='text-align:center'>尊敬的用户你好，请复制下列验证码完成账号登录(1小时内有效时间)</p>" +
                "<p style='text-align:center'>%s</p>", text);
        return new Email(to, "验证码", text);
    }
}
