package com.hh.commons.utils;

import com.hh.commons.condition.FastDFSCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description: 文件上传工具
 * @Author: hh
 * @Date: 2023/3/16 19:37
 * @Version: 1.0
 */
@Slf4j
public class FastDFSUtils {

    private String thump;
    private String fastDFSUrl;
    private FastDFSClient fastDFSClient;
    private FileUtils fileUtils;

    public FastDFSUtils(String width, String height, String fastDFSUrl, FastDFSClient fastDFSClient) {
        this.thump = "_" + width + "x" + height + ".";
        this.fastDFSUrl = fastDFSUrl;
        this.fastDFSClient = fastDFSClient;
        this.fileUtils = new FileUtils();
    }

    /**
     * 上传图片
     *
     * @param base64Data 图片数据
     * @param Id         用户id，作为唯一值
     * @return 返回图片对应url, [缩略图地址, 清晰图片地址]
     * @throws Exception
     */
    public String[] uploadImg(String base64Data, String Id) {
        String userFacePath = "D:\\" + Id + "userFaceBase64.png";
        // 获取fastDFS上传图片路径
        try {
            // 调用FileUtils类中方法将base64转为文件对象
            boolean flag = fileUtils.base64ToFile(userFacePath, base64Data);  // 是否创建好了文件
            if (flag) {
                MultipartFile multipartFile = fileUtils.fileToMultipart(userFacePath);
                String url = fastDFSClient.uploadBase64(multipartFile);
                String[] arr = url.split("\\.");
                String thumpImgUrl = arr[0] + thump + arr[1];
                return new String[]{fastDFSUrl + thumpImgUrl, fastDFSUrl + url};
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileUtils.removeFile(userFacePath);
        }
        return null;
    }

}
