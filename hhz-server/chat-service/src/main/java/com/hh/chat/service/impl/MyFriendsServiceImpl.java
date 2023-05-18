package com.hh.chat.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.chat.mapper.MyFriendsMapper;
import com.hh.chat.service.MyFriendsService;
import com.hh.commons.pojo.entity.chat.MyFriends;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户朋友表 服务实现类
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
@Service
public class MyFriendsServiceImpl extends ServiceImpl<MyFriendsMapper, MyFriends> implements MyFriendsService {

}
