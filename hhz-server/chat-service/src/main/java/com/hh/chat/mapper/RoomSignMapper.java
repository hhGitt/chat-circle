package com.hh.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.commons.pojo.entity.chat.MsgSign;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: RoomSignMapper
 * @Author: hh
 * @Date: 2023/4/16 21:27
 * @Version: 1.0
 */
@Mapper
public interface RoomSignMapper extends BaseMapper<MsgSign> {
}
