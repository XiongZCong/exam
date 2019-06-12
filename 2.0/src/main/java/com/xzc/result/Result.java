package com.xzc.result;

public class Result {

    private int code;
    private String msg;
    private Object data;

    /**
     * 成功时候的调用
     */
    public static Result success() {
        return new Result("成功");
    }

    public static Result success(Object data) {
        return new Result(data);
    }

    /**
     * 失败时候的调用
     */
    public static Result error(CodeMsg cm) {
        return new Result(cm);
    }

    private Result(Object data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
