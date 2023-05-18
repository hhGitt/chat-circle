package com.hh.commons.config;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.hh.commons.condition.FastDFSCondition;
import com.hh.commons.utils.FastDFSClient;
import com.hh.commons.utils.FastDFSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * @Description: FastDFSClientConfig配置类
 * @Param:
 * @return:
 * @Author: hh
 * @Date: 2023/5/15
 */

@Slf4j
@Configuration
@Conditional(FastDFSCondition.class)
public class FastDFSClientConfig {

    @Value("${fdfs.thumb-image.width}")
    private String width;

    @Value("${fdfs.thumb-image.height}")
    private String height;

    @Value("${fdfs.url}")
    private String url;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Bean
    public FastDFSClient fastDFSClient() {
        return new FastDFSClient(fastFileStorageClient);
    }

    @Bean
    public FastDFSUtils fastDFSUtils(FastDFSClient fastDFSClient) {
        log.info("FastDFS相关配置信息: 图片宽度: {}, 图片高度: {}, 图片存放地址: {}",width,height,url);
        return new FastDFSUtils(width, height, url, fastDFSClient);
    }
}
