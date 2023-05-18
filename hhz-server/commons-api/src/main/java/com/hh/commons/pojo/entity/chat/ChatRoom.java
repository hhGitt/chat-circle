package com.hh.commons.pojo.entity.chat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 聊天室信息表
 * @Author: hh
 * @Date: 2023/3/14 22:32
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("t_chat_room")
@ApiModel(value = "ChatMsg对象", description = "聊天信息表")
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty("房间号id")
    private String id;

    @ApiModelProperty("房间名")
    private String name;

    @ApiModelProperty("简介")
    private String brief;

    @ApiModelProperty("房间类型id")
    @TableField("type_id")
    private String typeId;

    @ApiModelProperty("房间头像id")
    @TableField("room_img_id")
    private String roomImgId;

    @ApiModelProperty("房间创建者id")
    @TableField("creator_id")
    private String creatorId;

    @ApiModelProperty("房间状态 0未开启，1开启")
    @TableField("state")
    private Integer state;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Date createTime;

    public ChatRoom(String id){
        this.id = id;
    }
    public ChatRoom(String type,Integer state){
        this.typeId = type;
        this.state = state;
    }

}
