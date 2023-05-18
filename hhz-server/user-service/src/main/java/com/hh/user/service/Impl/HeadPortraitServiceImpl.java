package com.hh.user.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.commons.pojo.entity.user.HeadPortrait;
import com.hh.user.mapper.HeadPortraitMapper;
import com.hh.user.service.HeadPortraitService;
import org.springframework.stereotype.Service;

/**
 * @Description: 头像表服务接口实现
 * @Author: hh
 * @Date: 2023/1/10 17:02
 * @Version: 1.0
 */
@Service
public class HeadPortraitServiceImpl extends ServiceImpl<HeadPortraitMapper, HeadPortrait> implements HeadPortraitService {
}
