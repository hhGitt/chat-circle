package com.hh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.commons.pojo.entity.user.HeadPortrait;
import com.hh.commons.pojo.entity.user.UserInfo;
import com.hh.commons.pojo.vo.user.UserInfoVo;

/**
 * @Description: UserInfoService
 * @Author: hh
 * @Date: 2023/1/7 15:59
 * @Version: 1.0
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
    * @Description: 获取用户基础信息
    * @Param: [id]
    * @return: com.hh.commons.pojo.vo.user.UserVo
    * @Author: hh
    * @Date: 2023/1/9
    */
    UserInfoVo getUserInfo(String id);

    /**
    * @Description: 在头像表新增一条头像信息，并改变该用户的头像信息
    * @Param: [userid,headPortrait]
    * @return: void
    * @Author: hh
    * @Date: 2023/1/10
    */
    void updateHeadPortrait(String userid, HeadPortrait headPortrait);

    /**
    * @Description: 获取用户头像URL
    * @Param: [userid]
    * @return: com.hh.commons.pojo.vo.user.HeadPortrait
    * @Author: hh
    * @Date: 2023/1/10
    */
    String getBigHeadPortrait(String userid);
}
