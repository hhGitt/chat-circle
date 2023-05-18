package com.hh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.commons.pojo.entity.user.Region;
import com.hh.commons.pojo.vo.user.RegionVo;

import java.util.List;

/**
 * @Description: 地区表服务接口
 * @Author: hh
 * @Date: 2023/1/14 14:44
 * @Version: 1.0
 */
public interface RegionService extends IService<Region> {
    /**
    * @Description: 获取地区列表
    * @Param: []
    * @return: java.util.List<com.hh.commons.pojo.vo.user.AreaVo>
    * @Author: hh
    * @Date: 2023/1/14
    */
    List<RegionVo> getRegionList();
}
