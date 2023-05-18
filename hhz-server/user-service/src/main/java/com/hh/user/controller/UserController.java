package com.hh.user.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hh.commons.annotations.AccessLimit;
import com.hh.commons.common.HttpStatus;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.user.UserLoginDto;
import com.hh.commons.pojo.dto.user.UserRegisterDto;
import com.hh.commons.pojo.entity.user.User;
import com.hh.commons.pojo.vo.user.UserVo;
import com.hh.commons.utils.JwtUtils;
import com.hh.commons.utils.RedisUtils;
import com.hh.user.service.UserService;
import com.hh.user.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 处理用户登陆注册相关接口
 * @Author: hh
 * @Date: 2023/4/21 14:23
 * @Version: 1.0
 */

@RestController
@RequestMapping("/user")
@Api(value = "用户接口", tags = {"用户接口"})
@Slf4j
public class UserController {

    @Value("${jwt.expireTime}")
    private int expireTime;  // jwtToken的默认有效时间 单位分钟

    @Value("${validateCode.time}")
    private int validateCodeTime; // 验证码有效期

    @Value("${validateCode.pre}")
    private String validateCodePre; // 验证码在redis前缀

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SMSUtils smsUtils;


    @ApiOperation("用户登录")
    @PostMapping("/login")
    @AccessLimit(seconds = 20, maxCount = 5)
    private ResultData checkLogin(@RequestBody UserLoginDto userDto) {
        User user = userService.checkAccount(userDto);
        if (user == null) {
            return ResultData.fail(HttpStatus.USERNAME_OR_PASSWORD_ERROR.getCode(), HttpStatus.USERNAME_OR_PASSWORD_ERROR.getMessage());
        } else {
            User jwtUser = jwtUtils.setTime(user);
            String userid = jwtUser.getId();
            redisUtils.putValue(userid, jwtUser, expireTime, TimeUnit.MINUTES); //将用户信息存入redis数据库 第三和第四个参数为有效时间和时间单位
            Map<String, Object> userInfoMap = new HashMap<>();
            userInfoMap.put("id", userid);
            String token = jwtUtils.createJwtToken(userInfoMap); //使用工具类生成token
            UserVo userVo = userService.getUserInfo(userid);
            userVo.setToken(token);
            return ResultData.success(userVo);
        }
    }

    @ApiOperation("用户注册")
    @PostMapping
    private ResultData registerAccount(@RequestBody UserRegisterDto userRegisterDto) {
        if (userService.checkNoRegister(userRegisterDto)) {  // 判断是否已注册
            return ResultData.fail(HttpStatus.USER_ACCOUNT_EXIST.getCode(), HttpStatus.USER_ACCOUNT_EXIST.getMessage());
        }
        String code;
        if (StringUtils.isNotBlank(userRegisterDto.getPhone_number())) {  // 判断手机号还是邮箱注册
            code = (String) redisUtils.getValue(validateCodePre + userRegisterDto.getPhone_number());
        } else if (StringUtils.isNotBlank(userRegisterDto.getEmail())) {
            code = (String) redisUtils.getValue(validateCodePre + userRegisterDto.getEmail());
        } else {
            return ResultData.fail(HttpStatus.NO_LOGIN_INFO.getCode(), HttpStatus.NO_LOGIN_INFO.getMessage());
        }
        if (code == null) {
            return ResultData.fail(HttpStatus.NO_CODE_REQUEST.getCode(), HttpStatus.NO_CODE_REQUEST.getMessage());
        } else if (code.equals(userRegisterDto.getCode())) {
            userService.saveUser(userRegisterDto);
        } else {
            return ResultData.fail(HttpStatus.CODE_ERROR.getCode(), HttpStatus.CODE_ERROR.getMessage());
        }
        return ResultData.success();
    }

    @ApiOperation("生成验证码")
    @PostMapping("/verificationCode")
    private ResultData getVerificationCode(@RequestBody UserRegisterDto userRegisterDto) throws Exception {
        String code = ValidateCodeUtils.generateNCode(6);
        if (StringUtils.isNotBlank(userRegisterDto.getPhone_number())) {  // 判断手机号还是邮箱注册
            if (!CheckUtils.checkPhone(userRegisterDto.getPhone_number())) {
                return ResultData.fail(HttpStatus.PHONE_FORMAT_ERROR.getCode(), HttpStatus.PHONE_FORMAT_ERROR.getMessage());
            }
            if (redisUtils.getValue(validateCodePre + userRegisterDto.getPhone_number()) != null) {
                return ResultData.fail(HttpStatus.REPEAT_CODE_REQUEST.getCode(), HttpStatus.REPEAT_CODE_REQUEST.getMessage());
            }
            boolean codeFlag = smsUtils.sendCodeToPhone(userRegisterDto.getPhone_number(), code);
            if (codeFlag) {
                redisUtils.putValue(validateCodePre + userRegisterDto.getPhone_number(), code, validateCodeTime, TimeUnit.MINUTES);
            } else {
                return ResultData.fail(HttpStatus.SEND_FAIL.getCode(), HttpStatus.SEND_FAIL.getMessage());
            }
        } else if (StringUtils.isNotBlank(userRegisterDto.getEmail())) {
            if (!CheckUtils.checkMail(userRegisterDto.getEmail())) {
                return ResultData.fail(HttpStatus.EMAIL_FORMAT_ERROR.getCode(), HttpStatus.EMAIL_FORMAT_ERROR.getMessage());
            }
            if (redisUtils.getValue(validateCodePre + userRegisterDto.getEmail()) != null) {
                return ResultData.fail(HttpStatus.REPEAT_CODE_REQUEST.getCode(), HttpStatus.REPEAT_CODE_REQUEST.getMessage());
            }
            EmailUtils.senCodeToMail(userRegisterDto.getEmail(), code);
            redisUtils.putValue(validateCodePre + userRegisterDto.getEmail(), code, validateCodeTime, TimeUnit.MINUTES);
        } else {
            return ResultData.fail(HttpStatus.NO_LOGIN_INFO.getCode(), HttpStatus.NO_LOGIN_INFO.getMessage());
        }
        return ResultData.success();
    }


}
