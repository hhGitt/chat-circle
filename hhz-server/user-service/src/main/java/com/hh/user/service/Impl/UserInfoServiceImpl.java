package com.hh.user.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.snowflake.SnowflakeObj;
import com.hh.commons.pojo.entity.user.HeadPortrait;
import com.hh.commons.pojo.entity.user.UserInfo;
import com.hh.commons.pojo.vo.user.UserInfoVo;
import com.hh.feign.clients.IdClient;
import com.hh.user.mapper.HeadPortraitMapper;
import com.hh.user.mapper.UserInfoMapper;
import com.hh.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @Description: UserInfoServiceImpl
 * @Author: hh
 * @Date: 2023/1/7 16:00
 * @Version: 1.0
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private HeadPortraitMapper headPortraitMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private IdClient idClient;

    @Override
    public UserInfoVo getUserInfo(String id) {
        return userInfoMapper.getUserInfo(id);
    }

    @Override
    @Transactional
    public void updateHeadPortrait(String userid, HeadPortrait headPortrait) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("UpdateHeadPortrait");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            ResultData re = idClient.getId();
            SnowflakeObj snowflakeObj = JSON.parseObject(JSON.toJSONString(re.getData()), SnowflakeObj.class);  // 获取唯一id
            String id = snowflakeObj.getId();
            headPortrait.setId(id);
            headPortraitMapper.insert(headPortrait);
            userInfoMapper.updateHeadPortraitId(userid, id);
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    @Override
    public String getBigHeadPortrait(String userid) {
        return userInfoMapper.getBigHeadPortrait(userid);
    }
}
