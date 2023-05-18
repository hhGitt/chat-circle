package com.hh.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.commons.pojo.entity.user.UserFriend;
import com.hh.commons.pojo.vo.user.UserFriendVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: UserFriendMapper
 * @Author: hh
 * @Date: 2023/4/21 14:26
 * @Version: 1.0
 */
@Mapper
public interface UserFriendMapper extends BaseMapper<UserFriend> {
    /**
    * @Description: t_user_friend表中通过my_user_id字段查找相关数据，并通过my_friend_user_id获取对应用户信息
    * @Param: [userId]
    * @return: java.util.List<com.hh.commons.pojo.vo.user.UserFriendVo>
    * @Author: hh
    * @Date: 2023/4/23
    */
    @Select("select f.user2Id friendId,ui.signature signature,ui.username friendName,(select image from t_head_portrait where id = ui.head_portrait_id) ImgUrl " +
            "from t_user_info ui " +
            "join (select user1_id user2Id from t_user_friend where user2_id = #{userId} union select user2_id user2Id from t_user_friend where user1_id = #{userId}) f " +
            "on ui.user_id = f.user2Id")
    List<UserFriendVo> selectFriends(String userId);

    @Select("select user1_id user1Id,user2_id user2Id from t_user_friend where user1_id = #{user1Id} and user2_id = #{user2Id}" +
            "union " +
            "select user1_id user1Id,user2_id user2Id from t_user_friend where user1_id = #{user2Id} and user2_id = #{user1Id}")
    UserFriend selectFriendByIds(String user1Id, String user2Id);
}
