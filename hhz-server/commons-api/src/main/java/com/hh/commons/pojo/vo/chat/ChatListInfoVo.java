package com.hh.commons.pojo.vo.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Description: 返回用户聊天消息
 * @Author: hh
 * @Date: 2023/4/27 16:55
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatListInfoVo {
    private String id;
    private String name;
    private String imgUrl;
    private String msg;
    private Date time;
    private Integer msgNumber;
}
