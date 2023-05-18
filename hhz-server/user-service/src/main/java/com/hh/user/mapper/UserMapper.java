package com.hh.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.commons.pojo.entity.user.User;
import com.hh.commons.pojo.vo.user.UserVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

/**
 * @Description: UserMapper
 * @Author hh
 * @Date 2023/1/7 15:56
 * @Version 1.0
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
    * @Description: 通过userid查找用户姓名，头像，个性签名
    * @Param: [userid]
    * @return: com.hh.commons.pojo.vo.user.UserVo
    * @Author: hh
    * @Date: 2023/1/7
    */
    @Select("select u.user_id id, u.username username, h.image image, u.signature signature from t_user_info u join t_head_portrait h on u.head_portrait_id = h.id and u.user_id = #{id}")
    @Results({
            @Result(property = "userId", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "signature", column = "signature"),
            @Result(property = "faceImg", column = "image")
    })
    UserVo getUserInfo(@Param("id") String userid);
}
