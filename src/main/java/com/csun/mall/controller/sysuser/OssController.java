package com.csun.mall.controller.sysuser;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.csun.mall.common.config.OssConfig;
import com.csun.mall.common.tools.OssUploadTool;
import com.csun.mall.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Joker Zheng
 * @create 2021/10/26 10:29
 */
@RestController
@RequestMapping("/api/file")
public class OssController {

    @Autowired
    private OssUploadTool ossUploadTool;

    @PostMapping("/upload")
    public ResponseData<String> fileUpload(@RequestPart("file") MultipartFile file, HttpServletRequest request) {

        return ResponseData.success(ossUploadTool.uploadOss(file));
    }


}
