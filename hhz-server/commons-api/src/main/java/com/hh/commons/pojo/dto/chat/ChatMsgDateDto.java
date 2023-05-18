package com.hh.commons.pojo.dto.chat;

import lombok.*;

import java.util.Date;

/**
 * @Description: 接收前端请求获取对应时间的消息
 * @Author: hh
 * @Date: 2023/4/7 21:07
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsgDateDto {
    private String id;  // 要排除的一条消息的id
    private String acceptId;  // 接收方id
    private Integer size;  // 条数限制
    private Date date;  // 下限日期
}
