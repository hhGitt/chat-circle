package com.hh.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.commons.pojo.entity.user.UserInfo;
import com.hh.commons.pojo.vo.user.UserInfoVo;
import org.apache.ibatis.annotations.*;

/**
 * @Description: 用户信息持久化操作
 * @Author hh
 * @Date 2023/1/7 15:56
 * @Version 1.0
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("select u.user_id uId,u.username,h.image,u.sex,u.signature,u.birthday,u.hobbies,u.region_id from t_user_info u join t_head_portrait h on user_id = #{id} and u.head_portrait_id = h.id")
    @Results({
            @Result(property = "userId",column = "uId"),
            @Result(property = "username",column = "username"),
            @Result(property = "imgUrl",column = "image"),
            @Result(property = "sex",column = "sex"),
            @Result(property = "signature",column = "signature"),
            @Result(property = "birthday",column = "birthday"),
            @Result(property = "hobbies",column = "hobbies"),
            @Result(property = "regionId",column = "region_id"),
    })
    UserInfoVo getUserInfo(@Param("id") String id);

    @Update("update t_user_info set head_portrait_id = #{head_portrait_id} where user_id = #{user_id}")
    void updateHeadPortraitId(@Param("user_id")String userid,@Param("head_portrait_id") String headPortraitId);

    @Select("select image_big from t_user_info u join t_head_portrait h on u.user_id = #{userid} and u.head_portrait_id = h.id")
    @Results({
            @Result(property = "imageBig",column = "image_big"),
    })
    String getBigHeadPortrait(@Param("userid")String userid);
}
