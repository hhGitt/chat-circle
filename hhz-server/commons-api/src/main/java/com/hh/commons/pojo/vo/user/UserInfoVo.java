package com.hh.commons.pojo.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 用户信息
 * @Author: hh
 * @Date: 2023/1/9 20:10
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("头像url")
    private String imgUrl;

    @ApiModelProperty("头像BigUrl")
    private String imgBigUrl;

    @ApiModelProperty("性别(默认0) 0:未知 1:男 2:女")
    private Integer sex;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("出生日期")
    private Date birthday;

    @ApiModelProperty("兴趣爱好")
    private String hobbies;

    @ApiModelProperty("地区id")
    private String regionId;

    @ApiModelProperty("判断是否为好友")
    private boolean friendIs;
}
