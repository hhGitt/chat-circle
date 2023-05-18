package com.hh.commons.pojo.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

/**
 * @Description: 头像表
 * @Author: hh
 * @Date: 2023/1/10 16:55
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_head_portrait")
public class HeadPortrait {

    @TableId()
    private String id;
    @TableField("image")
    private String image;
    @TableField("image_big")
    private String imageBig;
}
