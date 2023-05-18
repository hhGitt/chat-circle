package com.hh.commons.pojo.entity.chat;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 好友申请表
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
@Getter
@Setter
@TableName("t_friend_request")
@ApiModel(value = "FriendsRequest对象", description = "好友申请表")
public class FriendsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty("请求用户id")
    private String sendUserId;

    @ApiModelProperty("接收用户id")
    private String acceptUserId;

    @ApiModelProperty("请求发送时间")
    private LocalDateTime requestDateTime;


}
