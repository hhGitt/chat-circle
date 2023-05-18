package com.hh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.commons.pojo.entity.user.UserFriendRequest;
import com.hh.commons.pojo.vo.user.UserFriendRequestVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: UserFriendRequestService
 * @Author: hh
 * @Date: 2023/4/21 14:31
 * @Version: 1.0
 */
public interface UserFriendRequestService extends IService<UserFriendRequest> {

    /**
    * @Description: 保存好友请求
    * @Param: [sendUserId, acceptUserId]
    * @return: boolean
    * @Author: hh
    * @Date: 2023/4/21
    */
    boolean saveUserFriend(String sendUserId, String acceptUserId);

    /**
    * @Description: 通过对象查找
    * @Param: [userFriend]
    * @return: com.hh.commons.pojo.entity.user.UserFriend
    * @Author: hh
    * @Date: 2023/4/21
    */
    UserFriendRequest getByObj(UserFriendRequest userFriendRequest);

    /***
    * @Description: 通过userid获取该其它用户对自己的好友请求
    * @Param: [userId]
    * @return: java.util.List<com.hh.commons.pojo.entity.user.UserFriendRequest>
    * @Author: hh
    * @Date: 2023/4/21
    */
    List<UserFriendRequestVo> getRequestsByUserId(String userId);

    /**
    * @Description: 改变该条请求的处理情况
    * @Param: [userFriendRequest]
    * @return: boolean
    * @Author: hh
    * @Date: 2023/4/23
    */
    boolean updateFlag(UserFriendRequest userFriendRequest);

    /**
    * @Description: 同意好友请求，并将请求人id与接收请求人id关联起来
    * @Param: [userFriendRequest]
    * @return: boolean
    * @Author: hh
    * @Date: 2023/4/23
    */
    boolean acceptRequest(UserFriendRequest userFriendRequest);
}
