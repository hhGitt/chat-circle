package com.hh.chat.controller;

import com.alibaba.fastjson.JSON;
import com.hh.chat.service.ChatRoomService;
import com.hh.chat.websocket.RoomsChanelRel;
import com.hh.commons.common.HttpStatus;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.chat.ChatRoomDto;
import com.hh.commons.pojo.entity.chat.ChatRoom;
import com.hh.commons.pojo.entity.user.HeadPortrait;
import com.hh.commons.pojo.vo.chat.ChatRoomVo;
import com.hh.commons.pojo.vo.user.UserInfoVo;
import com.hh.commons.utils.FastDFSUtils;
import com.hh.feign.clients.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 房间Controller
 * @Author: hh
 * @Date: 2023/3/16 14:43
 * @Version: 1.0
 */
@RestController
@RequestMapping("/chatroom")
@Api(value = "聊天室房间接口", tags = {"聊天室房间接口"})
@Slf4j
public class ChatRoomController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private FastDFSUtils fastDFSUtils;

    @ApiOperation("添加房间")
    @PostMapping
    private ResultData createRoom(@RequestHeader(name = "token") String tokenHeader, @RequestBody ChatRoomDto chatRoomDto) throws Exception {
        ResultData re = userClient.getUserInfo(tokenHeader);  // 通过header获取用户id
        UserInfoVo userInfoVo = JSON.parseObject(JSON.toJSONString(re.getData()), UserInfoVo.class);
        // 判断用户身份（还未完成）
        if (userInfoVo == null) {
            return ResultData.fail(HttpStatus.USER_ACCOUNT_NOT_EXIST.getCode(), HttpStatus.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        // 将base64字符串转化为文件对象并上传头像
        String base64Data = chatRoomDto.getImgData();
        String[] ImgUrlList = fastDFSUtils.uploadImg(base64Data, chatRoomDto.getUserId());
        HeadPortrait headPortrait = new HeadPortrait();
        headPortrait.setImage(ImgUrlList[0]);
        headPortrait.setImageBig(ImgUrlList[1]);
        ChatRoom chatRooms = chatRoomService.createChatRooms(chatRoomDto, headPortrait);
        if (chatRooms == null) {
            return ResultData.fail(HttpStatus.CREATE_ROOM_FAILED.getCode(), HttpStatus.CREATE_ROOM_FAILED.getMessage());
        }
        chatRoomService.openRoom(chatRooms.getId());
        boolean flag = RoomsChanelRel.startOneRoom(chatRooms.getId());
        if (!flag) {
            return ResultData.fail(HttpStatus.START_ROOM_FAILED.getCode(), HttpStatus.START_ROOM_FAILED.getMessage());
        }
        return ResultData.success(chatRooms);
    }

    @ApiOperation("开启房间")
    @PostMapping("/start/{roomId}")
    private ResultData openRoom(@RequestHeader(name = "token") String tokenHeader, @PathVariable String roomId) throws Exception {
        chatRoomService.openRoom(roomId);
        boolean flag = RoomsChanelRel.startOneRoom(roomId);
        if (!flag) {
            return ResultData.fail(HttpStatus.START_ROOM_FAILED.getCode(), HttpStatus.START_ROOM_FAILED.getMessage());
        }
        return ResultData.success();
    }

    @ApiOperation("查询房间相关信息")
    @GetMapping("/{roomId}")
    private ResultData getRoomInfo(@PathVariable String roomId) {
        ChatRoom oneRoom = chatRoomService.getOneRoom(new ChatRoom(roomId));
        HeadPortrait headPortrait = chatRoomService.getHeadPortrait(oneRoom.getRoomImgId());
        ChatRoomVo chatRoomVo = new ChatRoomVo(oneRoom.getId(), oneRoom.getName(), oneRoom.getBrief(), oneRoom.getTypeId(), headPortrait.getImageBig(), RoomsChanelRel.getRoomPeople(roomId),0);
        return ResultData.success(chatRoomVo);
    }
}
