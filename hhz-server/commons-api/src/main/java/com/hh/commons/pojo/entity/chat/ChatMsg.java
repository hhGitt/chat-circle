package com.hh.commons.pojo.entity.chat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 聊天信息表
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_chat_msg")
@ApiModel(value = "ChatMsg对象", description = "聊天信息表")
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("消息id")
    @TableId
    private String id;

    @ApiModelProperty("发送信息用户id")
    @TableField("send_user_id")
    private String sendUserId;

    @ApiModelProperty("接收信息用户id")
    @TableField("accept_user_id")
    private String acceptUserId;

    @ApiModelProperty("发送的信息")
    @TableField("msg")
    private String msg;

    @ApiModelProperty("信息发送时间")
    @TableField("create_time")
    private Date createTime;
}
