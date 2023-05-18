package com.hh.commons.pojo.entity.chat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Description:
 * @Author: hh
 * @Date: 2023/4/16 21:24
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_msg_sign")
@ApiModel(value = "MsgSign对象", description = "消息标记表")
public class MsgSign {
    @ApiModelProperty("房间id")
    @TableField("room_id")
    private String roomId;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("最新接收时间")
    @TableField("sign_time")
    private Date signTime;
}
