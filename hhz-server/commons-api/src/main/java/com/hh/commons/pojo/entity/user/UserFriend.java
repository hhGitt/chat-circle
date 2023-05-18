package com.hh.commons.pojo.entity.user;

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
 * @Description: 用户好友表
 * @Author: hh
 * @Date: 2023/4/21 11:04
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserFriend对象", description = "用户好友表")
@TableName("t_user_friend")
public class UserFriend {

    @ApiModelProperty("用户id")
    @TableField("user1_id")
    private String user1Id;

    @ApiModelProperty("用户好友id")
    @TableField("user2_id")
    private String user2Id;

    @ApiModelProperty("添加时间")
    @TableField("create_time")
    private Date createTime;

    public UserFriend(String user1Id, String user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }
}
