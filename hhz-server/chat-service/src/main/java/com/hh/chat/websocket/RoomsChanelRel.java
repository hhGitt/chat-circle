package com.hh.chat.websocket;

import com.hh.chat.service.ChatRoomService;
import com.hh.chat.service.impl.ChatRoomServiceImpl;
import com.hh.commons.pojo.entity.chat.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;
import java.util.*;

/**
 * @Description: 房间id与用户id的集合关联关系处理
 * @Author: hh
 * @Date: 2023/3/14 22:07
 * @Version: 1.0
 */
public class RoomsChanelRel {
    private static HashMap<String, Set<String>> rooms = new HashMap<>();

    public static void init(ChatRoomService chatRoomService) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setState(1);
        List<ChatRoom> chatRooms = chatRoomService.getChatRooms(chatRoom);
        for (ChatRoom room : chatRooms) {
            startOneRoom(room.getId());
        }
        System.out.println("成功开启房间：" + rooms);
    }

    public static boolean startOneRoom(String roomId) {
        if (rooms.containsKey(roomId)) return false;
        rooms.put(roomId, new HashSet<>());
        return true;
    }

    public static boolean put(String roomId, String userId) {
        if (!rooms.containsKey(roomId)) return false;
        rooms.get(roomId).add(userId);
        return true;
    }

    public static Set<String> get(String roomId) {
        return rooms.getOrDefault(roomId, null);
    }

    public static boolean removeByUserId(String userId, String roomId) {
        Set<String> roomSet = rooms.get(roomId);
        System.out.println(roomSet);
        if (roomSet == null) return false;
        if (roomSet.contains(userId)) return rooms.get(roomId).remove(userId);
        return false;
    }

    /**
     * 获取房间人数多的前n个房间
     *
     * @param n 返回个数
     * @return 返回大小大于等于n
     */
    public static Map<String, Integer> getHeightRoom(int n) {
        Map<String, Integer> res = new HashMap<>();
        List<String> roomIds = new ArrayList<>(rooms.keySet());
        roomIds.sort((o1, o2) -> rooms.get(o2).size() - rooms.get(o1).size());
        for (int i = 0; i < Math.min(n, roomIds.size()); i++) {
            String roomId = roomIds.get(i);
            res.put(roomId, rooms.get(roomId).size());

        }
        return res;
    }

    /**
     * 获取指定roomId的聊天室房间人数
     *
     * @param roomId 房间号
     * @return 房间人数
     */
    public static int getRoomPeople(String roomId) {
        return rooms.getOrDefault(roomId, new HashSet<>()).size();
    }
}
