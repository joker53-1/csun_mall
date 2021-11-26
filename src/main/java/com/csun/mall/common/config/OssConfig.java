package com.csun.mall.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

    private String bucket;

    private String urlPrefix;

}
