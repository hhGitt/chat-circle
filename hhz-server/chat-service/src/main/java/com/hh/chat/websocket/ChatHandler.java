package com.hh.chat.websocket;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hh.chat.service.impl.ChatMsgServiceImpl;
import com.hh.chat.service.impl.RoomSignServiceImpl;
import com.hh.chat.utils.MyBeanUtil;
import com.hh.commons.pojo.dto.chat.ChatMsgDto;
import com.hh.commons.pojo.dto.chat.DataContent;
import com.hh.commons.pojo.entity.chat.MsgSign;
import com.hh.commons.pojo.enums.chat.MsgActionEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description: 用于处理消息的handler 由于传输数据的载体是frame，在netty中是用于为websocket专门处理文本对象的，frame是消息的载体，此类叫TextWebSocketFrame
 * @Author: hh
 * @Date: 2023/1/17 14:53
 * @Version: 1.0
 */
@Component
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChatMsgServiceImpl chatMsgService = MyBeanUtil.getBean(ChatMsgServiceImpl.class);
    private final RoomSignServiceImpl roomSignService = MyBeanUtil.getBean(RoomSignServiceImpl.class);
    private final UserChanelRel userChanelRel = MyBeanUtil.getBean(UserChanelRel.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端所传输的消息
        String content = msg.text();
        // 1.获取客户端发来的消息
        DataContent dataContent = JSON.parseObject(content, DataContent.class);
        Integer action = dataContent.getAction();
        Channel selfChannel = ctx.channel();
        // 2.判断消息类型,根据不同类型来处理不同的业务
        if (action ==  MsgActionEnum.CONNECT.getType()) {
            // 2.1 websocket第一次open的时候,初始化channel,把用的channel和userid关联起来
            String senderId = dataContent.getChatMsg().getSenderId();
            userChanelRel.put(senderId, selfChannel);
        } else if (action == MsgActionEnum.CHAT.getType()) {
            // 2.2 聊天类型的消息,把聊天记录保存到数据库
            ChatMsgDto chatMsg = dataContent.getChatMsg();
            chatMsgService.saveMsg(chatMsg);
            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setAction(MsgActionEnum.CHAT.getType());
            dataContentMsg.setChatMsg(chatMsg);
            // 发送消息
            String receiverId = chatMsg.getReceiverId(); // 接收者Id
            Set<String> userIdSet = RoomsChanelRel.get(receiverId);
            if (userIdSet != null) {
                // 在聊天室内
                List<String> outTimeList = new ArrayList<>();
                for (String userId : userIdSet) {
                    Channel receiverChannel = userChanelRel.get(userId);
                    if (receiverChannel == null) outTimeList.add(userId);// 离线用户
                    else {
                        receiverChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(dataContentMsg)));// 用户在线
                    }
                }
                for (String userId : outTimeList) userIdSet.remove(userId);
            } else {
                // 和好友聊天
                Channel receiverChannel = userChanelRel.get(receiverId);
                if (receiverChannel != null) {
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(dataContentMsg))); // 用户在线
                }
                selfChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(dataContentMsg)));
            }
        } else if (action == MsgActionEnum.SIGNED.getType()) {
            // 更新消息签收
            ChatMsgDto chatMsg = dataContent.getChatMsg();
            roomSignService.saveOrUpdateId(new MsgSign(chatMsg.getReceiverId(), chatMsg.getSenderId(), new Date()));
        } else if (action == MsgActionEnum.KEEPALIVE.getType()) {
            // 检测心跳，更新有效时间
            String senderId = dataContent.getChatMsg().getSenderId();
            userChanelRel.put(senderId, selfChannel);
        } else if (action == MsgActionEnum.Enter_ChatRoom.getType()) {
            // 进入房间后将用户加入该房间列表
            String roomId = dataContent.getChatMsg().getReceiverId();
            String userId = dataContent.getChatMsg().getSenderId();
            RoomsChanelRel.put(roomId, userId);
            Channel receiverChannel = userChanelRel.get(userId);
            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setAction(MsgActionEnum.Enter_ChatRoom.getType());
            receiverChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(dataContentMsg)));  // 返回给客户端房间连接成功
        } else if (action == MsgActionEnum.Leave_ChatRoom.getType()) {
            String roomId = dataContent.getChatMsg().getReceiverId();
            String userId = dataContent.getChatMsg().getSenderId();
            System.out.println("离开房间:" + RoomsChanelRel.get(roomId));
            if (StringUtils.isNotBlank(userId)) RoomsChanelRel.removeByUserId(userId, roomId);  // 房间中移除对应userid
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开,channel对应的长ID为:" + ctx.channel().id().asLongText());
        String userId = userChanelRel.getUserId(ctx.channel());
        userChanelRel.removeById(userId);  // 删除用户id与channel关系
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        String userId = userChanelRel.getUserId(ctx.channel());
        userChanelRel.removeById(userId);  // 删除用户id与channel关系
        ctx.channel().close();
    }

}
