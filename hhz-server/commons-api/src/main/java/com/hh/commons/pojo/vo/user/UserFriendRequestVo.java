package com.hh.commons.pojo.vo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

/**
 * @Description: 前端展示的好友请求消息
 * @Author: hh
 * @Date: 2023/4/23 10:22
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendRequestVo {
    private String id;  // 唯一id
    private String sendId;  // 发送者id
    private String sendName; // 发送者姓名
    private String ImgUrl;  // 头像url
    private Integer flag;  // 处理情况
    private Date requestDate;  // 发送时间
}
