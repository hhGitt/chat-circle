package com.hh.chat.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.commons.pojo.dto.chat.ChatMsgDateDto;
import com.hh.commons.pojo.dto.chat.ChatMsgDto;
import com.hh.commons.pojo.entity.chat.ChatMsg;
import com.hh.commons.pojo.vo.chat.ChatListInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 聊天信息表 Mapper 接口
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
@Mapper
public interface ChatMsgMapper extends BaseMapper<ChatMsg> {

    /**
     * 查询t_chat_msg中acceptId为聊天室的信息并查询senderImg按date降序
     * @param chatMsgDateDto 相关对象
     * @return
     */
    @Select("select m.id id,m.send_user_id senderId, p.image senderImg, u.username senderName, m.accept_user_id receiverId, m.msg msg, m.create_time sendTime " +
            "from t_chat_msg m " +
            "join t_user_info u on m.send_user_id = u.user_id and m.create_time <= #{date} and m.accept_user_id = #{acceptId} " +
            "join t_head_portrait p on p.id = u.head_portrait_id " +
            "order by m.create_time desc limit #{size}")
    List<ChatMsgDto> selectChatRoomMsgsByDate(ChatMsgDateDto chatMsgDateDto);

    /**
     * 查询t_chat_msg中acceptId为聊天室的信息并查询senderImg按date降序（有id）
     * @param chatMsgDateDto 相关对象
     * @return
     */
    @Select("select m.id id,m.send_user_id senderId, p.image senderImg, u.username senderName, m.accept_user_id receiverId, m.msg msg, m.create_time sendTime " +
            "from t_chat_msg m " +
            "join t_user_info u on m.send_user_id = u.user_id and m.create_time <= #{date} and m.accept_user_id = #{acceptId} and m.id != #{id} " +
            "join t_head_portrait p on p.id = u.head_portrait_id " +
            "order by m.create_time desc limit #{size}")
    List<ChatMsgDto> selectChatRoomMsgsByDateId(ChatMsgDateDto chatMsgDateDto);

    /**
     * 查询t_chat_msg中acceptId为人的信息并查询senderImg按date降序
     * @param chatMsgDateDto
     * @param sendId
     * @return
     */
    @Select("select m.id id,m.send_user_id senderId, p.image senderImg, u.username senderName, m.accept_user_id receiverId, m.msg msg, m.create_time sendTime " +
            "from t_chat_msg m " +
            "join t_user_info u on m.send_user_id = u.user_id and m.create_time <= #{chatMsgDateDto.date} and ((m.accept_user_id = #{chatMsgDateDto.acceptId} and m.send_user_id = #{sendId}) or (m.accept_user_id = #{sendId} and m.send_user_id = #{chatMsgDateDto.acceptId})) " +
            "join t_head_portrait p on p.id = u.head_portrait_id " +
            "order by m.create_time desc limit #{chatMsgDateDto.size}")
    List<ChatMsgDto> selectChatPeopleMsgsByDate(ChatMsgDateDto chatMsgDateDto,String sendId);

    /**
     * 查询t_chat_msg中acceptId为人的信息并查询senderImg按date降序（有id）
     * @param chatMsgDateDto
     * @param sendId
     * @return
     */
    @Select("select m.id id,m.send_user_id senderId, p.image senderImg, u.username senderName, m.accept_user_id receiverId, m.msg msg, m.create_time sendTime " +
            "from t_chat_msg m " +
            "join t_user_info u on m.send_user_id = u.user_id and m.create_time <= #{chatMsgDateDto.date} and ((m.accept_user_id = #{chatMsgDateDto.acceptId} and m.send_user_id = #{sendId}) or (m.accept_user_id = #{sendId} and m.send_user_id = #{chatMsgDateDto.acceptId})) and m.id != #{chatMsgDateDto.id} " +
            "join t_head_portrait p on p.id = u.head_portrait_id " +
            "order by m.create_time desc limit #{chatMsgDateDto.size}")
    List<ChatMsgDto> selectChatPeopleMsgsByDateId(ChatMsgDateDto chatMsgDateDto,String sendId);

    /**
     * 查询chatListInfoVo数据
     * @param acceptId
     * @return
     */
    @Select(
            "SELECT ui.user_id AS id, ui.username AS name, hp.image AS imgUrl, su.msgNumber AS msgNumber, tcmm.create_time AS time, tcmm.msg AS msg " +
                    "FROM t_user_info AS ui " +
                    "JOIN t_head_portrait AS hp ON ui.head_portrait_id = hp.id " +
                    "JOIN (SELECT tcm.send_user_id AS send_user_id, COUNT(*) AS msgNumber, MAX(id) mid  FROM t_chat_msg tcm " +
                    "JOIN t_msg_sign ms ON ms.room_id = tcm.send_user_id AND ms.user_id = tcm.accept_user_id " +
                    "JOIN t_user_friend uf ON (uf.user1_id = tcm.send_user_id AND uf.user2_id = #{acceptId}) OR (uf.user2_id = tcm.send_user_id AND uf.user1_id = #{acceptId}) " +
                    "WHERE tcm.accept_user_id = #{acceptId} AND tcm.create_time > ms.sign_time " +
                    "GROUP BY send_user_id) AS su ON su.send_user_id = ui.user_id " +
                    "JOIN t_chat_msg tcmm ON su.mid = tcmm.id"
    )
    List<ChatListInfoVo> selectChatListInfoVo(String acceptId);
}
