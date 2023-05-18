<template>
    <div class="chat_list">
        <div v-for="item, index in chat_list.data" :key="item.id" class="chat_obj" @click="chatToFriend(item.id)">
            <div class="head_img">
                <el-avatar :size="50" :src="item.imgUrl">
                    <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
                </el-avatar>
                <div class="num_c" v-if="item.msgNumber != 0">{{ item.msgNumber >= 100 ? "99+" : item.msgNumber }}</div>
            </div>
            <div class="name_msg">
                <p>{{ item.name }}</p>
                <p>{{ item.msg }}</p>
            </div>
            <div class="other_i">
                <el-dropdown trigger="click">
                    <el-icon style="height:50%">
                        <arrow-down />
                    </el-icon>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click="rmOne(index)">删除</el-dropdown-item>
                            <!-- <el-dropdown-item>置顶</el-dropdown-item> -->
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
                <span>{{ DateUtils.formateMonthTime(item.time) }}</span>
            </div>
        </div>
    </div>
</template>
    
<script lang='ts' setup>
import { onMounted } from 'vue';
import { DateUtils } from '@/utils/DateUtils';
import { FriendStore } from '@/stores';
const props = defineProps({
    chatList: { type: Object, required: true },
    myProp: { type: Object, required: true },
    turnV: { type: Object, required: true },
})
const choose_info = props.myProp;
const turnV = props.turnV;
const chat_list = props.chatList

const rmOne = (index: number) => {
    chat_list.data.splice(index, 1);
    const idList = chat_list.data.map((obj: { id: any; }) => obj.id);
    FriendStore.setFriendChats(idList)
}

// 清空页面
const clearV = async () => {
  turnV.flag = 0;
}

// 转到好友聊天页面
const changeV = async (id:string) => {
  turnV.flag = 2;
  choose_info.chatId = id;
  choose_info.chatObj = 2;
}

// 与好友聊天
const chatToFriend = async(id: string) => {
  await clearV();
  await changeV(id);  
}

onMounted(() => {

});
</script>
    
<style lang='less' scoped>
.chat_list {
    cursor: pointer;

    .chat_obj {
        height: 80px;
        padding: 0 4px;
        display: flex;
        align-items: center;
        justify-content: center;

        .head_img {
            width: 50px;
            height: 50px;
            margin: 0 10px;
            position: relative;
            display: flex;
            align-items: center;
            justify-content: center;

            .num_c {
                width: 15px !important;
                height: 15px !important;
                background-color: #D7D7D7;
                color: #7F7F7F;
                border-radius: 50px;
                position: absolute;
                right: auto;
                right: 0;
                top: 0;
                text-align: center;
                line-height: 15px;
                font-size: 12px;
            }
        }

        p {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            color: #333333;
        }

        p:nth-child(1) {
            font-size: 17px;
            font-weight: bold;
        }

        p:nth-child(2) {
            margin-top: 5px;
            font-size: 14px;
        }

        .name_msg {
            width: 120px;
        }

        .other_i {
            width: 40px;

            .el-dropdown {
                display: flex;
                align-items: center;
                justify-content: center;
            }

            span {
                display: block;
                width: 40px;
                font-size: 10px;
                text-align: center;
                color: #88898c;
            }
        }


        :hover {
            background-color: transparent;
        }
    }

    :hover {
        background-color: rgba(237, 238, 240, 0.5);
    }
}
</style>
    