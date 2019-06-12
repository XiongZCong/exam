package com.xzc.util;

import com.xzc.result.CodeMsg;
import com.xzc.result.exception.GlobalException;

public class NumberUtil {
    public static Long toLong(String numStr) {
        if (numStr.trim().length() <= 0) {
            throw new GlobalException(CodeMsg.NumberFormatException);
        }
        try {
            return Long.parseLong(numStr);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.NumberFormatException);
        }
    }
}
