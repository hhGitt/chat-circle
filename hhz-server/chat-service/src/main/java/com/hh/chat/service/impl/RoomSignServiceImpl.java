package com.hh.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.chat.mapper.RoomSignMapper;
import com.hh.chat.service.RoomSignService;
import com.hh.commons.pojo.entity.chat.MsgSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: RoomSignServiceImpl
 * @Author: hh
 * @Date: 2023/4/16 21:28
 * @Version: 1.0
 */
@Service
public class RoomSignServiceImpl extends ServiceImpl<RoomSignMapper, MsgSign> implements RoomSignService {
    @Autowired
    private RoomSignMapper roomSignMapper;

    @Override
    public boolean saveOrUpdateId(MsgSign roomSign) {
        LambdaQueryWrapper<MsgSign> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(roomSign.getRoomId()), MsgSign::getRoomId, roomSign.getRoomId())
                .eq(StringUtils.isNotBlank(roomSign.getUserId()), MsgSign::getUserId, roomSign.getUserId());
        MsgSign one = roomSignMapper.selectOne(wrapper);
        if (one != null) {
            roomSignMapper.update(roomSign, wrapper);
        } else {
            roomSignMapper.insert(roomSign);
        }
        return true;
    }
}
