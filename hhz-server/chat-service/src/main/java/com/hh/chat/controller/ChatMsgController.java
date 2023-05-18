package com.hh.chat.controller;

import com.alibaba.fastjson.JSON;
import com.hh.chat.service.ChatMsgService;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.chat.ChatMsgDateDto;
import com.hh.commons.pojo.enums.chat.AcceptObjEnum;
import com.hh.commons.pojo.vo.chat.ChatListInfoVo;
import com.hh.commons.pojo.vo.user.UserInfoVo;
import com.hh.feign.clients.UserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 聊天室消息Controller
 * @Author: hh
 * @Date: 2023/4/7 21:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/chatmsg")
@Api(value = "聊天室消息接口", tags = {"聊天室消息接口"})
@Slf4j
public class ChatMsgController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ChatMsgService chatMsgService;

    @ApiOperation("获取该房间对应时间前指定条数消息")
    @PostMapping("/{acceptType}")
    private ResultData getRoomMsg(@RequestHeader(name = "token") String tokenHeader, @RequestBody ChatMsgDateDto chatMsgDateDto, @PathVariable Integer acceptType) {
        AcceptObjEnum acceptObjEnum = AcceptObjEnum.AcceptObjOf(acceptType);
        if (acceptObjEnum == AcceptObjEnum.ROOM) {
            return ResultData.success(chatMsgService.getRoomMsgsByDate(chatMsgDateDto));
        } else if (acceptObjEnum == AcceptObjEnum.PEOPLE) {
            ResultData re = userClient.getUserInfo(tokenHeader);  // 通过header获取用户id
            UserInfoVo userInfoVo = JSON.parseObject(JSON.toJSONString(re.getData()), UserInfoVo.class);
            System.out.println(chatMsgDateDto.getAcceptId());
            System.out.println(userInfoVo.getUserId());
            return ResultData.success(chatMsgService.getPeopleMsgsByDate(chatMsgDateDto, userInfoVo.getUserId()));
        }
        return ResultData.success();
    }

    @ApiOperation("获取聊天列表中的好友信息和消息")
    @GetMapping("/chatList")
    private ResultData getFriendChatList(@RequestHeader(name = "token") String tokenHeader) {
        ResultData re = userClient.getUserInfo(tokenHeader);  // 通过header获取用户id
        UserInfoVo userInfoVo = JSON.parseObject(JSON.toJSONString(re.getData()), UserInfoVo.class);
        return ResultData.success(chatMsgService.getChatListInfoVo(userInfoVo.getUserId()));
    }

}
