package com.hh.commons.pojo.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 地区表
 * @Author: hh
 * @Date: 2023/1/14 14:51
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Region对象", description = "地区表")
@TableName("sys_region")
public class Region {
    @ApiModelProperty("id")
    @TableId("region_id")
    private String regionId;

    @ApiModelProperty("地区名称")
    @TableField("region_name")
    private String regionName;

    @ApiModelProperty("地区缩写")
    @TableField("region_short_name")
    private String regionShortName;

    @ApiModelProperty("行政地区编号")
    @TableField("region_code")
    private String regionCode;

    @ApiModelProperty("地区父id")
    @TableField("region_parent_id")
    private String regionParentId;

    @ApiModelProperty("地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县")
    @TableField("region_level")
    private Integer regionLevel;
}
