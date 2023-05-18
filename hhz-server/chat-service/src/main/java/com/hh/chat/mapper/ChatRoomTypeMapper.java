package com.hh.chat.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.commons.pojo.entity.chat.ChatRoom;
import com.hh.commons.pojo.entity.chat.ChatRoomType;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 聊天房类型Mapper
 * @Author: hh
 * @Date: 2023/3/29 17:18
 * @Version: 1.0
 */
@Mapper
public interface ChatRoomTypeMapper extends BaseMapper<ChatRoomType> {
}
