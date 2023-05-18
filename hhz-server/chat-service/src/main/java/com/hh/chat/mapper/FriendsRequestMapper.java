package com.hh.chat.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.commons.pojo.entity.chat.FriendsRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 好友申请表 Mapper 接口
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
@Mapper
public interface FriendsRequestMapper extends BaseMapper<FriendsRequest> {

}
