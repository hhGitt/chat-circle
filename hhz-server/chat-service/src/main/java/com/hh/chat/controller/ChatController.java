package com.hh.chat.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hh.chat.service.ChatRoomService;
import com.hh.chat.service.ChatRoomTypeService;
import com.hh.chat.websocket.RoomsChanelRel;
import com.hh.commons.common.HttpStatus;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.entity.chat.ChatRoom;
import com.hh.commons.pojo.vo.chat.ChatRoomVo;
import com.hh.commons.pojo.vo.user.UserInfoVo;
import com.hh.feign.clients.UserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 聊天室基本信息接口
 * @Author: hh
 * @Date: 2023/3/29 17:44
 * @Version: 1.0
 */
@RestController
@RequestMapping("/chat")
@Api(value = "聊天室信息", tags = {"聊天室信息接口"})
@Slf4j
public class ChatController {

    @Autowired
    private UserClient userClient;
    @Autowired
    private ChatRoomTypeService chatRoomTypeService;

    @Autowired
    private ChatRoomService chatRoomService;

    @ApiOperation("查询聊天室类型")
    @GetMapping()
    public ResultData getRoomType() {
        return ResultData.success(chatRoomTypeService.list());
    }

    @ApiOperation("查询热门聊天室")
    @GetMapping("/host")
    public ResultData getHostRoom(@RequestHeader(name = "token") String tokenHeader) {
        ResultData re = userClient.getUserInfo(tokenHeader);  // 通过header获取用户id
        UserInfoVo userInfoVo = JSON.parseObject(JSON.toJSONString(re.getData()), UserInfoVo.class);
        if (userInfoVo == null) {
            return ResultData.fail(HttpStatus.USER_ACCOUNT_NOT_EXIST.getCode(), HttpStatus.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        Map<String, Integer> heightRoom = RoomsChanelRel.getHeightRoom(10);
        List<ChatRoomVo> resData = new ArrayList<>();
        for (String s : heightRoom.keySet()) {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setId(s);
            chatRoom.setState(1);
            ChatRoomVo chatRoomVo = chatRoomService.getOneRoomAndMNum(chatRoom, userInfoVo.getUserId());
            chatRoomVo.setPeople(heightRoom.get(s));
            resData.add(chatRoomVo);
        }
        return ResultData.success(resData);
    }

    @ApiOperation("查询指定类型聊天室")
    @GetMapping("/{type}/{page}/{size}")
    public ResultData getTypeRoom(@RequestHeader(name = "token") String tokenHeader, @PathVariable String type, @PathVariable Integer page, @PathVariable Integer size) {
        ResultData re = userClient.getUserInfo(tokenHeader);  // 通过header获取用户id
        UserInfoVo userInfoVo = JSON.parseObject(JSON.toJSONString(re.getData()), UserInfoVo.class);
        if (userInfoVo == null) {
            return ResultData.fail(HttpStatus.USER_ACCOUNT_NOT_EXIST.getCode(), HttpStatus.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        IPage<ChatRoomVo> ChatRoomVo = chatRoomService.getChatRooms(page, size, new ChatRoom(type, 1), userInfoVo.getUserId());
        for (ChatRoomVo c : ChatRoomVo.getRecords()) {
            c.setPeople(RoomsChanelRel.get(c.getId()).size());
        }
        return ResultData.success(ChatRoomVo);
    }

    @ApiOperation("按名称模糊查询指定类型聊天室")
    @GetMapping("/{type}/{name}/{page}/{size}")
    public ResultData getTypeRoom(@RequestHeader(name = "token") String tokenHeader, @PathVariable String type, @PathVariable String name, @PathVariable Integer page, @PathVariable Integer size) {
        ResultData re = userClient.getUserInfo(tokenHeader);  // 通过header获取用户id
        UserInfoVo userInfoVo = JSON.parseObject(JSON.toJSONString(re.getData()), UserInfoVo.class);
        if (userInfoVo == null) {
            return ResultData.fail(HttpStatus.USER_ACCOUNT_NOT_EXIST.getCode(), HttpStatus.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setState(1);
        chatRoom.setTypeId(type);
        chatRoom.setName(name);
        IPage<ChatRoomVo> ChatRoomVo = chatRoomService.getChatRooms(page, size, chatRoom, userInfoVo.getUserId());
        for (ChatRoomVo c : ChatRoomVo.getRecords()) {
            c.setPeople(RoomsChanelRel.get(c.getId()).size());
        }
        return ResultData.success(ChatRoomVo);
    }
}
