package com.hh.commons.pojo.vo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description: 前端查看的用户好友信息
 * @Author: hh
 * @Date: 2023/4/23 13:50
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendVo {
    private String friendId;  // 好友id
    private String friendName; // 好友姓名
    private String signature; // 好友签名
    private String ImgUrl;  // 头像url
}
