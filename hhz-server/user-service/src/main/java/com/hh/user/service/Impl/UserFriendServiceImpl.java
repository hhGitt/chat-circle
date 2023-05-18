package com.hh.user.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.commons.pojo.entity.user.UserFriend;
import com.hh.commons.pojo.vo.user.UserFriendVo;
import com.hh.user.mapper.UserFriendMapper;
import com.hh.user.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: UserFriendService实现
 * @Author: hh
 * @Date: 2023/4/21 14:29
 * @Version: 1.0
 */
@Service
public class UserFriendServiceImpl extends ServiceImpl<UserFriendMapper, UserFriend> implements UserFriendService {

    @Autowired
    private UserFriendMapper userFriendMapper;

    @Override
    public boolean judgeFriend(UserFriend userFriend) {
        return userFriendMapper.selectFriendByIds(userFriend.getUser1Id(), userFriend.getUser2Id()) != null;
    }

    @Override
    public List<UserFriendVo> getFriendsByMyId(String userId) {
        return userFriendMapper.selectFriends(userId);
    }
}
