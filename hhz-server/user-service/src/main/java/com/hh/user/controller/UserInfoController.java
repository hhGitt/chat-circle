package com.hh.user.controller;

import com.hh.commons.common.HttpStatus;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.user.UserFaceDto;
import com.hh.commons.pojo.entity.user.HeadPortrait;
import com.hh.commons.pojo.entity.user.User;
import com.hh.commons.pojo.entity.user.UserFriend;
import com.hh.commons.pojo.entity.user.UserInfo;
import com.hh.commons.pojo.vo.user.RegionVo;
import com.hh.commons.pojo.vo.user.UserInfoVo;
import com.hh.commons.utils.FastDFSUtils;
import com.hh.user.service.RegionService;
import com.hh.user.service.UserFriendService;
import com.hh.user.service.UserInfoService;
import com.hh.commons.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 用户信息接口
 * @Author: hh
 * @Date: 2023/1/9 19:53
 * @Version: 1.0
 */
@RestController
@RequestMapping("/userinfo")
@Api(value = "用户信息接口", tags = {"用户信息接口"})
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private FastDFSUtils fastDFSUtils;

    @GetMapping
    @ApiOperation("获取用户个人信息")
    public ResultData getUserInfo(@RequestHeader(name = "token") String tokenHeader) throws Exception {
        User u = jwtUtils.verifyJwtToken(tokenHeader);
        UserInfoVo userInfo = userInfoService.getUserInfo(u.getId());
        return ResultData.success(userInfo);
    }

    @ApiOperation("更新用户信息")
    @PostMapping
    public ResultData updateUserInfo(@RequestBody UserInfo userInfo, @RequestHeader(name = "token") String tokenHeader) throws Exception {
        User u = jwtUtils.verifyJwtToken(tokenHeader);  // 通过token获取userid
        String userid = u.getId();
        userInfo.setUserId(userid);
        boolean flag = userInfoService.updateById(userInfo);
        return flag ? ResultData.success() : ResultData.fail(HttpStatus.UPDATE_INFO_ERROR.getCode(), HttpStatus.UPDATE_INFO_ERROR.getMessage());
    }

    @ApiOperation("用户头像上传")
    @PostMapping("/uploadFaceBase64")
    public ResultData uploadFaceBase64(@RequestBody UserFaceDto userFaceDto, @RequestHeader(name = "token") String tokenHeader) throws Exception {
        User u = jwtUtils.verifyJwtToken(tokenHeader);  // 通过token获取userid
        String userid = u.getId();
        // 将base64字符串转化为文件对象并上传头像
        String base64Data = userFaceDto.getFaceData();
        String[] ImgUrlList = fastDFSUtils.uploadImg(base64Data, userid);
        // 更新用户头像
        HeadPortrait headPortrait = new HeadPortrait();
        headPortrait.setImage(ImgUrlList[0]);
        headPortrait.setImageBig(ImgUrlList[1]);
        userInfoService.updateHeadPortrait(userid, headPortrait);
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setImgUrl(ImgUrlList[1]);
        return ResultData.success(userInfoVo);
    }

    @ApiOperation("获取用户头像")
    @GetMapping("/bigHeadPortrait")
    public ResultData getBigHeadPortrait(@RequestHeader(name = "token") String tokenHeader) throws Exception {
        User u = jwtUtils.verifyJwtToken(tokenHeader);  // 通过token获取userid
        String userid = u.getId();
        String imgUrl = userInfoService.getBigHeadPortrait(userid);
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setImgBigUrl(imgUrl);
        return ResultData.success(userInfoVo);
    }

    @ApiOperation("获取地区信息")
    @GetMapping("/region")
    public ResultData getRegionList() {
        List<RegionVo> areaList = regionService.getRegionList();
        return ResultData.success(areaList);
    }

    @ApiOperation("获取其它用户基本信息")
    @GetMapping("/ou/{userId}")
    public ResultData getOtherUserInfo(@RequestHeader(name = "token") String tokenHeader, @PathVariable String userId) throws Exception {
        User u = jwtUtils.verifyJwtToken(tokenHeader);  // 通过token获取userid
        String myId = u.getId();
        UserInfoVo userInfo = userInfoService.getUserInfo(userId);
        UserFriend userFriend = new UserFriend(myId,userId);
        userInfo.setFriendIs(userFriendService.judgeFriend(userFriend));  // 判断用户是否为好友
        return ResultData.success(userInfo);
    }
}
