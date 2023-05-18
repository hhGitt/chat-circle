package com.hh.commons.pojo.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 用户聊天信息传输对象
 * @Author: hh
 * @Date: 2023/1/28 14:39
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsgDto {
    private String id;
    private String senderId; //发送者id
    private String senderImg;  // 发送者头像url
    private String senderName;  // 发送者姓名
    private String receiverId;  //接收者id
    private String msg;  //聊天内容
    private Date sendTime;  // 发送时间
}
