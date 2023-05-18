package com.hh.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.commons.pojo.entity.user.UserFriendRequest;
import com.hh.commons.pojo.vo.user.UserFriendRequestVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: UserFriendRequestMapper
 * @Author: hh
 * @Date: 2023/4/21 14:31
 * @Version: 1.0
 */

@Mapper
public interface UserFriendRequestMapper extends BaseMapper<UserFriendRequest> {
    /**
    * @Description: t_user_friend_request表中查找acceptId为userId的数据，并查询对应sendId用户的相关信息
    * @Param: [userId]
    * @return: java.util.List<com.hh.commons.pojo.vo.user.UserFriendRequestVo>
    * @Author: hh
    * @Date: 2023/4/23
    */
    @Select("select ufr.id id,ufr.send_user_id sendId,ui.username sendName,(select image from t_head_portrait where ui.head_portrait_id = id) ImgUrl,ufr.flag flag ,ufr.request_date_time requestDate " +
            "from t_user_friend_request ufr " +
            "join t_user_info ui " +
            "on ui.user_id = ufr.send_user_id and ufr.accept_user_id = #{userId}")
    List<UserFriendRequestVo> selectUserFriendRequestVoList(String userId);
}
