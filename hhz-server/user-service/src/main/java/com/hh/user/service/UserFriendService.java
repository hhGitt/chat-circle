package com.hh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.commons.pojo.entity.user.UserFriend;
import com.hh.commons.pojo.vo.user.UserFriendVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: UserFriendService
 * @Author: hh
 * @Date: 2023/4/21 14:27
 * @Version: 1.0
 */
public interface UserFriendService extends IService<UserFriend> {
    /**
    * @Description: 判断两人是否为朋友
    * @Param: [userFriend]
    * @return: com.hh.commons.pojo.entity.user.UserFriend
    * @Author: hh
    * @Date: 2023/4/21
    */
    boolean judgeFriend(UserFriend userFriend);

    /**
    * @Description: 通过userId获取对应好友的相关消息
    * @Param: [userId]
    * @return: java.util.List<com.hh.commons.pojo.vo.user.UserFriendVo>
    * @Author: hh
    * @Date: 2023/4/23
    */
    List<UserFriendVo> getFriendsByMyId(String userId);
}
