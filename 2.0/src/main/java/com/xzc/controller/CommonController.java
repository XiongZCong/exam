package com.xzc.controller;

import com.xzc.result.Result;
import com.xzc.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 公共控制器
 *
 * @author 熊智聪
 * @create 2018-06-26 8:52
 **/
@RestController
public class CommonController {
    @Autowired
    QiniuUtil qiniuUtil;

    @RequestMapping("/common/qiniu")
    public Result qiniu(MultipartFile file) throws Exception {
        System.out.println(file.getOriginalFilename());
        qiniuUtil.uploadImg(file);
        return Result.success();
    }
}
