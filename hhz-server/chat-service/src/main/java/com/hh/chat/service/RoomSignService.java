package com.hh.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.commons.pojo.entity.chat.MsgSign;

/**
 * @Description: RoomSignService
 * @Author: hh
 * @Date: 2023/4/16 21:28
 * @Version: 1.0
 */
public interface RoomSignService extends IService<MsgSign> {
    /**
    * @Description: 更新数据库中t_msg_sign表，如果含有数据更新，没有添加
    * @Param: [roomSign]
    * @return: void
    * @Author: hh
    * @Date: 2023/4/16
    */
    boolean saveOrUpdateId(MsgSign roomSign);
}
