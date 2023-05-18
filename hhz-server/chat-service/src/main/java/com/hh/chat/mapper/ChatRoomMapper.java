package com.hh.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hh.commons.pojo.entity.chat.ChatRoom;
import com.hh.commons.pojo.entity.user.HeadPortrait;
import com.hh.commons.pojo.vo.chat.ChatRoomVo;
import org.apache.ibatis.annotations.*;

/**
 * @Description: 聊天室房间信息 Mapper
 * @Author: hh
 * @Date: 2023/3/16 11:44
 * @Version: 1.0
 */


@Mapper
public interface ChatRoomMapper extends BaseMapper<ChatRoom> {

    @Select("select image_big from t_head_portrait where id = #{id}")
    @Results({
            @Result(property = "imageBig", column = "image_big"),
    })
    HeadPortrait getHeadPortrait(@Param("id") String id);

    /**
     * 查询ChatRoomVo中信息分页
     *
     * @param page
     * @param chatRoom
     * @return
     */
    @Select("SELECT c.id id, c.name name, c.brief brief, c.type_id typeId, h.image roomImgUrl, " +
            "(SELECT count(*) from t_chat_msg where accept_user_id = c.id and create_time > (coalesce((SELECT sign_time from t_msg_sign where room_id = c.id and user_id = #{userid}),'1970-1-1 00:00:00'))) nums " +
            "FROM t_chat_room c JOIN t_head_portrait h ON c.room_img_id = h.id and c.type_id = #{chatRoom.typeId} and c.state = #{chatRoom.state} ")
    Page<ChatRoomVo> selectChatRoomVoPage(Page<ChatRoomVo> page, ChatRoom chatRoom, String userid);

    /**
     * 查询chatRoomVo中信息分页，并且模糊查询name相关
     * @param page
     * @param chatRoom
     * @return
     */
    @Select("SELECT c.id id, c.name name, c.brief brief, c.type_id typeId, h.image roomImgUrl, " +
            "(SELECT count(*) from t_chat_msg where accept_user_id = c.id and create_time > (coalesce((SELECT sign_time from t_msg_sign where room_id = c.id and user_id = #{userid}),'1970-1-1 00:00:00'))) nums " +
            "FROM t_chat_room c JOIN t_head_portrait h ON c.room_img_id = h.id and c.type_id = #{chatRoom.typeId} and c.state = #{chatRoom.state} and c.name like concat('%',#{chatRoom.name},'%')")
    Page<ChatRoomVo> selectChatRoomVoPageLikeName(Page<ChatRoomVo> page, ChatRoom chatRoom, String userid);

    /**
     * 通过房间id查找该房间信息和对应用户未接收信息保存在chatRoomVo中
     * @param chatRoom
     * @param userid
     * @return
     */
    @Select("SELECT c.id id, c.name name, c.brief brief, c.type_id typeId, h.image roomImgUrl, " +
            "(SELECT count(*) from t_chat_msg where accept_user_id = c.id and create_time > (coalesce((SELECT sign_time from t_msg_sign where room_id = c.id and user_id = #{userid}),'1970-1-1 00:00:00'))) nums " +
            "FROM t_chat_room c JOIN t_head_portrait h ON c.room_img_id = h.id and c.id = #{chatRoom.id} and c.state = #{chatRoom.state} ")
    ChatRoomVo selectOneChatRoom(ChatRoom chatRoom, String userid);
}


