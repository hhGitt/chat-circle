package com.hh.commons.pojo.entity.chat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 聊天室类型表
 * @Author: hh
 * @Date: 2023/3/29 17:14
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_chat_room_type")
@ApiModel(value = "ChatRoomType对象", description = "聊天室类型表")
public class ChatRoomType implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty("类型id")
    private String id;

    @ApiModelProperty("类型名称")
    private String name;
}
