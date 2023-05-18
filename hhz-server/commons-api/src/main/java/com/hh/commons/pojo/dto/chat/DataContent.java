package com.hh.commons.pojo.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 用户信息传输对象
 * @Author: hh
 * @Date: 2023/1/28 14:39
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataContent implements Serializable {
    private Integer action; // 动作类型
    private ChatMsgDto chatMsg; // 用户聊天内容
    private String expand; // 拓展字段
}
