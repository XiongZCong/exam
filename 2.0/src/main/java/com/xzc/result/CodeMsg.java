package com.xzc.result;

public class CodeMsg {

    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "成功");
    public static CodeMsg BIND_ERROR = new CodeMsg(500100, "参数校验异常：%s");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500101, "服务端异常");
    public static CodeMsg FORMAT_ERROR = new CodeMsg(500102, "格式错误");
    public static CodeMsg IS_EXIST = new CodeMsg(500103, "数据已存在");
    //登录模块 5002XX
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500200, "用户不存在");
    public static CodeMsg CODE_ERROR = new CodeMsg(500201, "验证码错误");
    public static CodeMsg EMAIL_EXIST = new CodeMsg(500202, "邮箱已被注册");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500203, "密码错误");
    public static CodeMsg SESSION_ERROR = new CodeMsg(500204, "Session不存在或者已失效");

    public static CodeMsg UnauthorizedException = new CodeMsg(500206, "没有访问权限，访问异常");
    public static CodeMsg NumberFormatException = new CodeMsg(500207, "输入异常，请输入数字");
    public static CodeMsg EMAIL_CODE_ERROR = new CodeMsg(500208, "邮箱验证码错误或失效");
    public static CodeMsg USERNAME_ERROR = new CodeMsg(500209, "用户名称错误");

    //考试模块 5003XX
    public static CodeMsg ENDTIME = new CodeMsg(500301, "考试还未结束，不可查看答案！");
    public static CodeMsg BEBINTIMEAFTERENDTIME = new CodeMsg(500302, "考试还未结束，不可查看答案！");
    public static CodeMsg BEBINTIMETOENDTIME = new CodeMsg(500303, "不在考试的规定时间范围内！");

    private CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

}
