package com.hh.commons.pojo.dto.chat;

import lombok.*;

/**
 * @Description: 用于记录查找热门房间信息数据
 * @Author: hh
 * @Date: 2023/4/16 18:19
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostRoomDto {
    private String roomId;
    private Integer people;
}
