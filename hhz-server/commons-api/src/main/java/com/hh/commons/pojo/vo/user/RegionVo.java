package com.hh.commons.pojo.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 返回的地区格式
 * @Author: hh
 * @Date: 2023/1/14 14:45
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionVo {
    private String value;
    private String label;
    private List<RegionVo> children;
}
