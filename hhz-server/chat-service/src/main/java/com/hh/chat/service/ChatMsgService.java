package com.hh.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.commons.pojo.dto.chat.ChatMsgDateDto;
import com.hh.commons.pojo.dto.chat.ChatMsgDto;
import com.hh.commons.pojo.entity.chat.ChatMsg;
import com.hh.commons.pojo.vo.chat.ChatListInfoVo;
import com.hh.commons.pojo.vo.user.UserInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 聊天信息表 服务类
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
public interface ChatMsgService extends IService<ChatMsg> {
    /**
    * @Description: 保存聊天信息到数据库
    * @Param: [chatMsg]
    * @return: java.lang.String
    * @Author: hh
    * @Date: 2023/1/28
    */
    String saveMsg(ChatMsgDto chatMsg);

    /**
     * 查询对应的房间id对应时间前的消息
     * @param chatMsgDateDto
     * @return
     */
    List<ChatMsgDto> getRoomMsgsByDate(ChatMsgDateDto chatMsgDateDto);

    /**
     * 根据发送者和接收者id和时间等对象，获取聊天信息
     * @param chatMsgDateDto
     * @return
     */
    List<ChatMsgDto> getPeopleMsgsByDate(ChatMsgDateDto chatMsgDateDto,String sendId);

    /**
     * 查询userInfoVo相关数据,用户未接收的消息
     * @param userId
     * @return
     */
    List<ChatListInfoVo> getChatListInfoVo(String userId);
}
