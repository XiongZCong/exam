package com.xzc.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 七牛云使用
 *
 * @author 熊智聪
 * @create 2018-06-26 7:49
 **/
@Component
public class QiniuUtil {
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.path}")
    private String path;

//    //密钥配置
//    private Auth auth = Auth.create(accessKey, secretKey);
//    //创建上传对象
//    private Configuration cfg = new Configuration(Zone.zone0());//华东机房
//    private UploadManager uploadManager = new UploadManager(cfg);
//    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
//    private String token = auth.uploadToken(bucket);

    //普通上传
    public void uploadImg(MultipartFile file) throws IOException {
        Auth auth = Auth.create(accessKey, secretKey);
        Configuration cfg = new Configuration(Zone.zone0());//华东机房
        UploadManager uploadManager = new UploadManager(cfg);
        String token = auth.uploadToken(bucket);
        String key = getKey(file);
        FileInputStream inputStream = (FileInputStream) file.getInputStream();
        try {
            //调用put方法上传
            Response res = uploadManager.put(inputStream, key, token,null,null);
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }

    private String getKey(MultipartFile file) {
        String prefix = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")).replace(" ", "");
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        return prefix + UUIDUtil.uuid() + suffix;
    }
}
