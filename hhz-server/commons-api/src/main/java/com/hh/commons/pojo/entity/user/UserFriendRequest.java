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
 * @Description:用户好友添加请求表
 * @Author: hh
 * @Date: 2023/4/21 11:17
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UserFriendRequest对象", description = "用户好友添加请求表")
@TableName("t_user_friend_request")
public class UserFriendRequest {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("请求用户id")
    @TableField("send_user_id")
    private String sendId;

    @ApiModelProperty("接收用户id")
    @TableField("accept_user_id")
    private String acceptId;

    @ApiModelProperty("处理情况（0 未处理，1同意，2拒绝，默认0）")
    @TableField("flag")
    private Integer flag;

    @ApiModelProperty("请求发送时间")
    @TableField("request_date_time")
    private Date requestDate;

    public UserFriendRequest(String sendId, String acceptId, Integer flag) {
        this.sendId = sendId;
        this.acceptId = acceptId;
        this.flag = flag;
    }

}
