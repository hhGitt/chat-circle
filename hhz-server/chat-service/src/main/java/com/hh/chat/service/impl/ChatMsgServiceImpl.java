package com.hh.chat.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.chat.mapper.ChatMsgMapper;
import com.hh.chat.service.ChatMsgService;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.chat.ChatMsgDateDto;
import com.hh.commons.pojo.dto.chat.ChatMsgDto;
import com.hh.commons.pojo.dto.snowflake.SnowflakeObj;
import com.hh.commons.pojo.vo.chat.ChatListInfoVo;
import com.hh.feign.clients.IdClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.commons.pojo.entity.chat.ChatMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 聊天信息表 服务实现类
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
@Service
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper, ChatMsg> implements ChatMsgService {

    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Autowired
    private IdClient idClient;


    @Override
    public String saveMsg(ChatMsgDto chatMsgDto) {
        ResultData re = idClient.getId();
        SnowflakeObj snowflakeObj = JSON.parseObject(JSON.toJSONString(re.getData()), SnowflakeObj.class);  // 获取唯一id
        String id = snowflakeObj.getId();
        ChatMsg chatMsg = new ChatMsg(id, chatMsgDto.getSenderId(), chatMsgDto.getReceiverId(), chatMsgDto.getMsg(), new Date());
        chatMsgMapper.insert(chatMsg);
        return id;
    }

    @Override
    public List<ChatMsgDto> getRoomMsgsByDate(ChatMsgDateDto chatMsgDateDto) {
        if (chatMsgDateDto.getId() != null){
            return chatMsgMapper.selectChatRoomMsgsByDateId(chatMsgDateDto);
        }
        return chatMsgMapper.selectChatRoomMsgsByDate(chatMsgDateDto);
    }

    @Override
    public List<ChatMsgDto> getPeopleMsgsByDate(ChatMsgDateDto chatMsgDateDto,String sendId) {
        if (chatMsgDateDto.getId() != null){
            return chatMsgMapper.selectChatPeopleMsgsByDateId(chatMsgDateDto, sendId);
        }
        return chatMsgMapper.selectChatPeopleMsgsByDate(chatMsgDateDto, sendId);
    }

    @Override
    public List<ChatListInfoVo> getChatListInfoVo(String acceptId) {
        return chatMsgMapper.selectChatListInfoVo(acceptId);
    }
}
