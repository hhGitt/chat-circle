package com.hh.commons.pojo.dto.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
* @Description: 用户注册信息传输对象
* @Author: hh
* @Date: 2023/5/15
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    private String email;
    private String phone_number;
    private String password;
    private String code;
}
