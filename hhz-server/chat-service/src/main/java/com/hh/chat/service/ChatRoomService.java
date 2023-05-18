package com.hh.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.commons.pojo.dto.chat.ChatRoomDto;
import com.hh.commons.pojo.entity.chat.ChatRoom;
import com.hh.commons.pojo.vo.chat.ChatRoomVo;
import com.hh.commons.pojo.entity.user.HeadPortrait;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: ChatRoomService
 * @Author: hh
 * @Date: 2023/3/16 11:43
 * @Version: 1.0
 */
public interface ChatRoomService extends IService<ChatRoom> {
    /**
     * @Description: 查询对应条件的chatRoom列表
     * @Param: [chatRoom]
     * @return: java.util.List<com.hh.commons.pojo.entity.chat.ChatRoom>
     * @Author: hh
     * @Date: 2023/3/16
     */
    List<ChatRoom> getChatRooms(ChatRoom chatRoom);

    /***
    * @Description: 查询对应条件的chatRoomVo列表，带分页功能
    * @Param: [page, state, chatRoom]
    * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.hh.commons.pojo.entity.chat.ChatRoom>
    * @Author: hh
    * @Date: 2023/3/30
    */
    IPage<ChatRoomVo> getChatRooms(int currentPage, int pageSize, ChatRoom chatRoom, String userid);

    /**
     * @Description: 添加聊天室，并返回聊天室相关信息
     * @Param: [chatRoomDto]
     * @return: com.hh.commons.pojo.entity.chat.ChatRoom
     * @Author: hh
     * @Date: 2023/3/16
     */
    ChatRoom createChatRooms(ChatRoomDto chatRoomDto,HeadPortrait headPortrait);

    /***
    * @Description: 获取单个房间信息
    * @Param: [chatRoom]
    * @return: com.hh.commons.pojo.entity.chat.ChatRoom
    * @Author: hh
    * @Date: 2023/3/29
    */
    ChatRoom getOneRoom(ChatRoom chatRoom);

    /***
    * @Description: 通过图片id查找url
    * @Param: [id]
    * @return: com.hh.commons.pojo.vo.user.HeadPortrait
    * @Author: hh
    * @Date: 2023/3/29
    */
    HeadPortrait getHeadPortrait(String id);

    /****
    * @Description: 打开房间
    * @Param: [chatRoom]
    * @return: boolean
    * @Author: hh
    * @Date: 2023/4/6
     * @param roomId
    */
    boolean openRoom(String roomId);

    /**
    * @Description: 获取单个房间的信息并保存在chatRoomVo中
    * @Param: [chatRoom, userId]
    * @return: com.hh.commons.pojo.vo.chat.ChatRoomVo
    * @Author: hh
    * @Date: 2023/4/16
    */
    ChatRoomVo getOneRoomAndMNum(ChatRoom chatRoom, String userId);
}
