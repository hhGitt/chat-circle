package com.hh.user.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.snowflake.SnowflakeObj;
import com.hh.commons.pojo.dto.user.UserLoginDto;
import com.hh.commons.pojo.dto.user.UserRegisterDto;
import com.hh.commons.pojo.entity.user.User;
import com.hh.commons.pojo.entity.user.UserInfo;
import com.hh.commons.pojo.vo.user.UserVo;
import com.hh.feign.clients.IdClient;
import com.hh.user.mapper.UserInfoMapper;
import com.hh.user.mapper.UserMapper;
import com.hh.user.service.UserService;
import com.hh.user.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

/**
 * @Description: UserServiceImpl
 * @Author: hh
 * @Date: 2023/5/15
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private IdClient idClient;

    @Override
    public User checkAccount(UserLoginDto userDto) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(userDto.getAccount()), User::getEmail, userDto.getAccount()).or().eq(StringUtils.isNotBlank(userDto.getAccount()), User::getPhone_number, userDto.getAccount()).eq(StringUtils.isNotBlank(userDto.getPassword()), User::getPassword, userDto.getPassword());
        return userMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public UserVo getUserInfo(String id) {
        return userMapper.getUserInfo(id);
    }

    @Override
    public boolean checkNoRegister(UserRegisterDto userRegisterDto) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(userRegisterDto.getEmail()), User::getEmail, userRegisterDto.getEmail()).or().eq(StringUtils.isNotBlank(userRegisterDto.getPhone_number()), User::getPhone_number, userRegisterDto.getPhone_number());
        return userMapper.selectOne(lambdaQueryWrapper) != null;
    }

    @Override
    @Transactional
    public void saveUser(UserRegisterDto userRegisterDto) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("RegisterUser");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            ResultData re = idClient.getId();
            SnowflakeObj snowflakeObj = JSON.parseObject(JSON.toJSONString(re.getData()), SnowflakeObj.class);  // 获取唯一id
            String id = snowflakeObj.getId();
            User user = new User(id, userRegisterDto.getEmail(), userRegisterDto.getPhone_number(), userRegisterDto.getPassword(), new Date(), new Date());
            userMapper.insert(user);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(id);
            userInfo.setUsername("用户_" + ValidateCodeUtils.generateSNCode(6));
            userInfoMapper.insert(userInfo);
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }

    }
}
