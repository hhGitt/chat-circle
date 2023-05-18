package com.hh.commons.pojo.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 接收前端请求创建的房间信息
 * @Author: hh
 * @Date: 2023/3/16 19:05
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private String userId;  // 创建者Id
    private String typeId; // 房间类型
    private String roomName;  // 房间名
    private String brief; // 简介
    private String imgData;  // 图片数据
}
