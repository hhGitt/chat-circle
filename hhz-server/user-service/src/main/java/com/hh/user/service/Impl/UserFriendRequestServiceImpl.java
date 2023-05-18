package com.hh.user.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.snowflake.SnowflakeObj;
import com.hh.commons.pojo.entity.chat.MsgSign;
import com.hh.commons.pojo.entity.user.UserFriend;
import com.hh.commons.pojo.entity.user.UserFriendRequest;
import com.hh.commons.pojo.enums.user.FriendRequestEnum;
import com.hh.commons.pojo.vo.user.UserFriendRequestVo;
import com.hh.feign.clients.IdClient;
import com.hh.user.mapper.MsgSignMapper;
import com.hh.user.mapper.UserFriendMapper;
import com.hh.user.mapper.UserFriendRequestMapper;
import com.hh.user.service.UserFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;

/**
 * @Description: UserFriendRequestService实现
 * @Author: hh
 * @Date: 2023/4/21 14:31
 * @Version: 1.0
 */
@Service
public class UserFriendRequestServiceImpl extends ServiceImpl<UserFriendRequestMapper, UserFriendRequest> implements UserFriendRequestService {

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private IdClient idClient;
    @Autowired
    private UserFriendRequestMapper userFriendRequestMapper;
    @Autowired
    private UserFriendMapper userFriendMapper;
    @Autowired
    private MsgSignMapper roomSignMapper;

    @Transactional
    @Override
    public boolean saveUserFriend(String sendUserId, String acceptUserId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("saveUserFriend");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            ResultData re = idClient.getId();
            SnowflakeObj snowflakeObj = JSON.parseObject(JSON.toJSONString(re.getData()), SnowflakeObj.class);  // 获取唯一id
            String id = snowflakeObj.getId();
            UserFriendRequest user = new UserFriendRequest(id, sendUserId, acceptUserId, FriendRequestEnum.UNPROCESSED.getValue(), new Date());  // 添加两人关系
            // 添加两条起始消息签收
            roomSignMapper.insert(new MsgSign(sendUserId, acceptUserId, new Date()));
            roomSignMapper.insert(new MsgSign(acceptUserId, sendUserId, new Date()));
            return userFriendRequestMapper.insert(user) > 0;
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    @Override
    public UserFriendRequest getByObj(UserFriendRequest userFriendRequest) {
        LambdaQueryWrapper<UserFriendRequest> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(userFriendRequest.getSendId()), UserFriendRequest::getSendId, userFriendRequest.getSendId())
                .eq(StringUtils.isNotBlank(userFriendRequest.getAcceptId()), UserFriendRequest::getAcceptId, userFriendRequest.getAcceptId())
                .eq(true, UserFriendRequest::getFlag, userFriendRequest.getFlag());
        return userFriendRequestMapper.selectOne(wrapper);
    }

    @Override
    public List<UserFriendRequestVo> getRequestsByUserId(String userId) {
        return userFriendRequestMapper.selectUserFriendRequestVoList(userId);
    }

    @Override
    @Transactional
    public boolean updateFlag(UserFriendRequest userFriendRequest) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("updateFlag");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            LambdaQueryWrapper<UserFriendRequest> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(StringUtils.isNotBlank(userFriendRequest.getAcceptId()), UserFriendRequest::getAcceptId, userFriendRequest.getAcceptId())
                    .eq(StringUtils.isNotBlank(userFriendRequest.getSendId()), UserFriendRequest::getSendId, userFriendRequest.getSendId())
                    .eq(true, UserFriendRequest::getFlag, FriendRequestEnum.UNPROCESSED.getValue());

            LambdaQueryWrapper<UserFriendRequest> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(StringUtils.isNotBlank(userFriendRequest.getAcceptId()), UserFriendRequest::getAcceptId, userFriendRequest.getSendId())
                    .eq(StringUtils.isNotBlank(userFriendRequest.getSendId()), UserFriendRequest::getSendId, userFriendRequest.getAcceptId())
                    .eq(true, UserFriendRequest::getFlag, FriendRequestEnum.UNPROCESSED.getValue());
            return userFriendRequestMapper.update(new UserFriendRequest(userFriendRequest.getSendId(), userFriendRequest.getAcceptId(), FriendRequestEnum.ACCEPT.getValue()), wrapper1) + userFriendRequestMapper.update(new UserFriendRequest(userFriendRequest.getAcceptId(), userFriendRequest.getSendId(), FriendRequestEnum.ACCEPT.getValue()), wrapper2) > 0;
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    @Override
    @Transactional
    public boolean acceptRequest(UserFriendRequest userFriendRequest) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("acceptRequest");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            boolean flag = updateFlag(userFriendRequest);  // 判断是否有数据改变
            if (flag) {
                UserFriend userFriend = new UserFriend(userFriendRequest.getAcceptId(), userFriendRequest.getSendId(), new Date());
                flag = userFriendMapper.insert(userFriend) > 0;
            }
            return flag;
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }
}
