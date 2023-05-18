package com.hh.commons.pojo.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 用户账号信息表
 * @Param:
 * @return:
 * @Author: hh
 * @Date: 2023/1/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User对象", description = "用户信息表")
@TableName("t_user")
public class User {
    @TableId
    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("用户email")
    private String email;

    @ApiModelProperty("用户手机号")
    private String phone_number;

    @ApiModelProperty("用户账号密码")
    private String password;

    @ApiModelProperty("用户创建时间")
    private Date create_time;

    @ApiModelProperty("用户信息更新时间")
    private Date update_time;
    /**
     * 登录时间
     */
    @TableField(exist = false)
    private Long loginTime;

    /**
     * 令牌过期时间
     */
    @TableField(exist = false)
    private Long expireTime;

    public User(String id, String email, String phone_number, String password, Date create_time, Date update_time) {
        this.id = id;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.create_time = create_time;
        this.update_time = update_time;
    }
}
