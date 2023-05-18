package com.hh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.commons.pojo.dto.user.UserLoginDto;
import com.hh.commons.pojo.dto.user.UserRegisterDto;
import com.hh.commons.pojo.entity.user.User;
import com.hh.commons.pojo.vo.user.UserVo;

/**
 * @Description: UserService
 * @Author: hh
 * @Date: 2023/1/7 16:00
 */

public interface UserService extends IService<User> {
    /**
     * 通过邮箱和密码查找用户
     *
     * @param userDto
     * @return
     */
    User checkAccount(UserLoginDto userDto);

    /**
     * 通过用户id查找用户信息
     *
     * @param id 用户id
     * @return
     */
    UserVo getUserInfo(String id);

    /**
     * 检查是否注册过
     *
     * @param userRegisterDto
     * @return
     */
    boolean checkNoRegister(UserRegisterDto userRegisterDto);

    /**
     * 保存用户信息到数据库
     *
     * @param userRegisterDto
     */
    void saveUser(UserRegisterDto userRegisterDto);
}
