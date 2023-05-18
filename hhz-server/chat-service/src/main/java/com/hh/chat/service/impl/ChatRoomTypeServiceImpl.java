package com.hh.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.chat.mapper.ChatRoomTypeMapper;
import com.hh.chat.service.ChatRoomTypeService;
import com.hh.commons.pojo.entity.chat.ChatRoomType;
import org.springframework.stereotype.Service;

/**
 * @Description: ChatRoomTypeServiceImpl
 * @Author: hh
 * @Date: 2023/3/29 17:28
 * @Version: 1.0
 */
@Service
public class ChatRoomTypeServiceImpl extends ServiceImpl<ChatRoomTypeMapper, ChatRoomType> implements ChatRoomTypeService {
}
