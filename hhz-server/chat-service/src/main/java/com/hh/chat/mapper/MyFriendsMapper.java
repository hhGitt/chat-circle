package com.hh.chat.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.commons.pojo.entity.chat.MyFriends;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户朋友表 Mapper 接口
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
@Mapper
public interface MyFriendsMapper extends BaseMapper<MyFriends> {

}
