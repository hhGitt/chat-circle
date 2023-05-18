package com.hh.chat.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.chat.mapper.FriendsRequestMapper;
import com.hh.chat.service.FriendsRequestService;
import com.hh.commons.pojo.entity.chat.FriendsRequest;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 好友申请表 服务实现类
 * </p>
 *
 * @author hh
 * @since 2023-01-03
 */
@Service
public class FriendsRequestServiceImpl extends ServiceImpl<FriendsRequestMapper, FriendsRequest> implements FriendsRequestService {

}
