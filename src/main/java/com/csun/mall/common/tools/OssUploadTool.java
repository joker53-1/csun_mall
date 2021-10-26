package com.csun.mall.common.tools;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.csun.mall.common.config.OssConfig;
import org.assertj.core.annotations.Beta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author Joker Zheng
 * @create 2021/10/26 11:22
 */
@Component
public class OssUploadTool {

    @Autowired
    private OssConfig ossConfig;

    public String uploadOss(MultipartFile fileUpdate) {
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        String path = String.format("%s/", "mall");
        try {
            System.out.println(path + fileUpdate.getOriginalFilename());
            boolean exist = ossClient.doesObjectExist("ceeg-global-bidding", path + fileUpdate.getOriginalFilename());
            if (exist) {
                path = path + UUID.randomUUID() + "/";
            }
            PutObjectResult putObjectResult = ossClient.putObject("ceeg-global-bidding", path + fileUpdate.getOriginalFilename(), fileUpdate.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return path+fileUpdate.getOriginalFilename();

    }
}
