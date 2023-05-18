package com.hh.commons.pojo.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 用户信息表
 * @Param:
 * @return:
 * @Author: hh
 * @Date: 2023/1/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("t_user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId("user_id")
    private String userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("头像id")
    @TableField("head_portrait_id")
    private String headPortraitId;

    @ApiModelProperty("性别(默认0) 0:未知 1:男 2:女")
    private Integer sex;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @ApiModelProperty("兴趣爱好")
    private String hobbies;

    @ApiModelProperty("地区id")
    @TableField("region_id")
    private String regionId;
}
