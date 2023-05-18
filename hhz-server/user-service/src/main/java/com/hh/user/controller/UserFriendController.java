package com.hh.user.controller;

import com.hh.commons.common.HttpStatus;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.entity.user.User;
import com.hh.commons.pojo.entity.user.UserFriend;
import com.hh.commons.pojo.entity.user.UserFriendRequest;
import com.hh.commons.pojo.enums.user.FriendRequestEnum;
import com.hh.commons.pojo.vo.user.UserFriendRequestVo;
import com.hh.commons.pojo.vo.user.UserFriendVo;
import com.hh.commons.utils.JwtUtils;
import com.hh.user.service.Impl.UserServiceImpl;
import com.hh.user.service.UserFriendRequestService;
import com.hh.user.service.UserFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 用户好友相关服务接口
 * @Author: hh
 * @Date: 2023/4/21 14:23
 * @Version: 1.0
 */
@RestController
@Slf4j
@Api(value = "用户好友相关服务接口", tags = {"用户好友相关服务接口"})
@RequestMapping("/friend")
public class UserFriendController {

    @Autowired
    private UserFriendRequestService userFriendRequestService;
    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtils jwtUtils;

    @ApiOperation("发送添加好友信息")
    @PostMapping("/{acceptUserId}")
    private ResultData addFriendRequest(@RequestHeader(name = "token") String tokenHeader, @PathVariable String acceptUserId) throws Exception {
        User u = jwtUtils.verifyJwtToken(tokenHeader);
        String sendUserId = u.getId();
        // 判断想添加用户是否存在
        if (userService.getById(acceptUserId) == null) {
            return ResultData.fail(HttpStatus.USER_ACCOUNT_NOT_EXIST.getCode(), HttpStatus.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        // 判断该用户是否已经为好友
        UserFriend userFriend = new UserFriend(sendUserId, acceptUserId);
        if (userFriendService.judgeFriend(userFriend)) {
            return ResultData.fail(HttpStatus.USER_ALREADY_FRIEND.getCode(), HttpStatus.USER_ALREADY_FRIEND.getMessage());
        }
        // 判断请求是否重复
        UserFriendRequest userFriendRequest = new UserFriendRequest(sendUserId, acceptUserId, FriendRequestEnum.UNPROCESSED.getValue());
        if (userFriendRequestService.getByObj(userFriendRequest) == null) {
            userFriendRequestService.saveUserFriend(sendUserId, acceptUserId);
            return ResultData.success();
        } else {
            return ResultData.fail(HttpStatus.USER_REQUEST_REPEAT.getCode(), HttpStatus.USER_REQUEST_REPEAT.getMessage());
        }
    }

    @ApiOperation("查看好友请求信息")
    @GetMapping()
    private ResultData getFriendRequests(@RequestHeader(name = "token") String tokenHeader) throws Exception {
        User u = jwtUtils.verifyJwtToken(tokenHeader);
        String userId = u.getId();
        List<UserFriendRequestVo> requestsList = userFriendRequestService.getRequestsByUserId(userId);
        requestsList.sort(((o1, o2) -> {
            if (o1.getFlag() == FriendRequestEnum.UNPROCESSED.getValue() && o2.getFlag() != FriendRequestEnum.UNPROCESSED.getValue())
                return -1;
            else if (o1.getFlag() != FriendRequestEnum.UNPROCESSED.getValue() && o2.getFlag() == FriendRequestEnum.UNPROCESSED.getValue())
                return 1;
            else return o1.getRequestDate().compareTo(o2.getRequestDate());
        }));
        return ResultData.success(requestsList);
    }

    @ApiOperation("对好友请求做出行动")
    @PostMapping("/{requestId}/{sendId}/{action}")
    private ResultData actionOnRequest(@RequestHeader(name = "token") String tokenHeader, @PathVariable String sendId, @PathVariable String requestId, @PathVariable Integer action) throws Exception {
        User u = jwtUtils.verifyJwtToken(tokenHeader);
        String userId = u.getId();
        FriendRequestEnum friendRequestEnum = FriendRequestEnum.friendRequestOf(action); // 获取操作类型
        UserFriendRequest userFriendRequest = new UserFriendRequest();
        userFriendRequest.setId(requestId);
        userFriendRequest.setAcceptId(userId);
        userFriendRequest.setSendId(sendId);
        userFriendRequest.setFlag(friendRequestEnum.getValue());
        // 判断操作类型
        if (friendRequestEnum == FriendRequestEnum.REFUSE) {
            // 拒绝，修改请求状态
            userFriendRequestService.updateFlag(userFriendRequest);
        } else if (friendRequestEnum == FriendRequestEnum.ACCEPT) {
            // 同意，修改请求状态，并将两人关联起来
            userFriendRequestService.acceptRequest(userFriendRequest);
        }
        return ResultData.success();
    }

    @ApiOperation("查看好友列表")
    @GetMapping("/lis")
    private ResultData getFriends(@RequestHeader(name = "token") String tokenHeader) throws Exception {
        User u = jwtUtils.verifyJwtToken(tokenHeader);
        String userId = u.getId();
        List<UserFriendVo> friends = userFriendService.getFriendsByMyId(userId);
        return ResultData.success(friends);
    }
}
