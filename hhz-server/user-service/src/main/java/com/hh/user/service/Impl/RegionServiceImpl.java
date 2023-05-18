package com.hh.user.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.commons.pojo.entity.user.Region;
import com.hh.commons.pojo.vo.user.RegionVo;
import com.hh.user.mapper.RegionMapper;
import com.hh.user.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: 地区表服务接口实现
 * @Author: hh
 * @Date: 2023/1/14 14:47
 * @Version: 1.0
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {
    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<RegionVo> getRegionList() {
        List<Region> regions = regionMapper.selectList(null);
        List<List<Region>> levels = new ArrayList<>();  // 存放每一级地区的列表
        // 地区的最大层级
        int size = regions.stream().max(Comparator.comparingInt(Region::getRegionLevel)).get().getRegionLevel();
        if (size == 0) return null;
        for (int i = 0; i < size; i++) levels.add(new ArrayList<>());
        // 加入地区列表
        for (Region region : regions) {
            levels.get(region.getRegionLevel() - 1).add(region);
        }
        List<RegionVo> regionVoList = new ArrayList<>();  // 返回数据
        Map<String, List<RegionVo>> regionMap = new HashMap<>();  // 父级所有的子地区
        for (int i = size - 1; i >= 1; i--) {
            Map<String, List<RegionVo>> map = new HashMap<>(); // 用于临时存放上级地区
            for (Region region : levels.get(i - 1)) {
                map.put(region.getRegionId(), new ArrayList<>());
            }
            for (Region region : levels.get(i)) {
                map.get(region.getRegionParentId()).add(new RegionVo(region.getRegionId(), region.getRegionName(), regionMap.getOrDefault(region.getRegionId(), new ArrayList<>())));
            }
            regionMap = map;
        }
        for (Region region : levels.get(0)) {
            regionVoList.add(new RegionVo(region.getRegionCode(), region.getRegionName(), regionMap.getOrDefault(region.getRegionId(), new ArrayList<>())));
        }
        return regionVoList;
    }
}
