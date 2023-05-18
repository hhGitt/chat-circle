package com.hh.commons.pojo.vo.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 返回房间信息
 * @Author: hh
 * @Date: 2023/3/29 20:09
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomVo {
    private String id;  // 房间id
    private String name;  // 房间名称
    private String brief;  // 房间简介
    private String typeId;  // 房间类型id
    private String roomImgUrl; // 图片url
    private Integer people; // 在线人数
    private Integer nums; // 未接受消息条数
}
