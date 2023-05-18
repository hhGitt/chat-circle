package com.hh.commons.pojo.entity.chat;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户朋友表
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
@Getter
@Setter
@TableName("t_user_friend")
@ApiModel(value = "MyFriends对象", description = "用户朋友表")
public class MyFriends implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty("用户id")
    private String myUserId;

    @ApiModelProperty("用户朋友id")
    private String myFriendUserId;


}
