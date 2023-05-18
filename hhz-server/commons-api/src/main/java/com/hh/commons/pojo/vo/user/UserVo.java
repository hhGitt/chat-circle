package com.hh.commons.pojo.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description: 用户自身信息
 * @Author: hh
 * @Date: 2023/1/9 20:10
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private String userId;  // id
    private String username;  // 用户名
    private String faceImg;  // 头像地址
    private String signature;  // 签名
    private String token;  // 用户认证token
}
