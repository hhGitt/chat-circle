package com.hh.chat.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.chat.mapper.ChatRoomMapper;
import com.hh.chat.mapper.HeadPortraitMapper;
import com.hh.chat.service.ChatRoomService;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.chat.ChatRoomDto;
import com.hh.commons.pojo.dto.snowflake.SnowflakeObj;
import com.hh.commons.pojo.entity.chat.ChatRoom;
import com.hh.commons.pojo.vo.chat.ChatRoomVo;
import com.hh.commons.pojo.entity.user.HeadPortrait;
import com.hh.feign.clients.IdClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;

/**
 * @Description: ChatRoomServiceImpl
 * @Author: hh
 * @Date: 2023/3/16 12:08
 * @Version: 1.0
 */
@Service
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomMapper, ChatRoom> implements ChatRoomService {
    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private IdClient idClient;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private HeadPortraitMapper headPortraitMapper;

    @Override
    public List<ChatRoom> getChatRooms(ChatRoom chatRoom) {
        LambdaQueryWrapper<ChatRoom> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(chatRoom.getState() != null, ChatRoom::getState, chatRoom.getState());
        return chatRoomMapper.selectList(wrapper);
    }

    @Override
    public IPage<ChatRoomVo> getChatRooms(int currentPage, int pageSize, ChatRoom chatRoom, String userid) {
        PageDTO<ChatRoomVo> pageDTO = new PageDTO<ChatRoomVo>(currentPage, pageSize);
        if (chatRoom.getName() != null) {
            chatRoomMapper.selectChatRoomVoPageLikeName(pageDTO, chatRoom, userid);
        } else {
            chatRoomMapper.selectChatRoomVoPage(pageDTO, chatRoom, userid);
        }
        return pageDTO;
    }

    @Override
    public ChatRoom getOneRoom(ChatRoom chatRoom) {
        LambdaQueryWrapper<ChatRoom> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(chatRoom.getId() != null, ChatRoom::getId, chatRoom.getId());
        return chatRoomMapper.selectOne(wrapper);
    }

    @Override
    public HeadPortrait getHeadPortrait(String id) {
        return chatRoomMapper.getHeadPortrait(id);
    }

    @Override
    public boolean openRoom(String roomId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setState(1);
        chatRoom.setId(roomId);
        return chatRoomMapper.updateById(chatRoom) > 0;
    }

    @Override
    public ChatRoomVo getOneRoomAndMNum(ChatRoom chatRoom, String userid) {
        return chatRoomMapper.selectOneChatRoom(chatRoom,userid);
    }

    @Override
    @Transactional
    public ChatRoom createChatRooms(ChatRoomDto chatRoomDto, HeadPortrait headPortrait) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("createChatRooms");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        ChatRoom chatRoom;
        try {
            ResultData re = idClient.getId();
            SnowflakeObj snowflakeObj = JSON.parseObject(JSON.toJSONString(re.getData()), SnowflakeObj.class);  // 获取头像id
            String headId = snowflakeObj.getId();
            headPortrait.setId(headId);
            headPortraitMapper.insert(headPortrait);
            ResultData re2 = idClient.getId();
            SnowflakeObj snowflakeObj2 = JSON.parseObject(JSON.toJSONString(re2.getData()), SnowflakeObj.class);  // 获取roomId
            String roomId = snowflakeObj2.getId();
            chatRoom = new ChatRoom(roomId, chatRoomDto.getRoomName(), chatRoomDto.getBrief(), chatRoomDto.getTypeId(), headId, chatRoomDto.getUserId(), 0, new Date());
            chatRoomMapper.insert(chatRoom);
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
        return chatRoom;
    }

}
