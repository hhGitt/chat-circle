<template>
    <div class="tool_lis">
        <el-icon style="cursor: pointer;" @click="getFriendRequest">
            <Plus />
        </el-icon>
    </div>
    <div class="friend_lis" v-for="friend, index in friends">
        <div class="name_initial" v-if="friend.length != 0">
            <p class="initial">{{ index == 26 ? "#" : String.fromCharCode(65 + index) }}</p>
            <div class="u_infos" v-for="f in friend" @click="showUserInfo(f.friendId)">
                <div class="head_img">
                    <el-avatar :size="50" :src="f.imgUrl">
                        <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
                    </el-avatar>
                </div>
                <div class="u_n">
                    <p :title="f.friendName">{{ f.friendName }}</p>
                    <p :title="f.signature">{{ f.signature }}</p>
                </div>
            </div>
        </div>
    </div>
    <peopleCard ref="peopleC" :myProp="choose_info" :turnV="turnV" :sideChoose="sideChoose"></peopleCard>
    <el-dialog v-model="showRequests" width="20%" align-center :modal="false" style="margin-top:15%" title="好友请求"
        class="myDialog">
        <div class="req_body">
            <div class="reqs" v-for="request, index in requestList">
                <div class="head_img">
                    <el-avatar :size="50" :src="request.imgUrl">
                        <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
                    </el-avatar>
                </div>
                <div class="u_n">
                    <p :title="request.sendName">{{ request.sendName }}</p>
                    <p>想添加你为好友</p>
                </div>
                <div class="a_c">
                    <el-button type="success" plain v-if="request.flag == 0" @click="acRequest(index, 1)">同意</el-button>
                    <el-button type="danger" plain v-if="request.flag == 0" @click="acRequest(index, 2)">拒绝</el-button>
                    <el-button type="success" disabled v-if="request.flag == 1">已同意</el-button>
                    <el-button type="danger" disabled v-else-if="request.flag == 2">已拒绝</el-button>
                </div>
            </div>
        </div>

    </el-dialog>
</template>
    
<script lang='ts' setup>
import { onMounted, reactive, ref } from 'vue';
import { ElMessage, ElMessageBox } from "element-plus";
import { getFriendList, actionFriendRequest, getFriendRequests } from '@/request/api/friend'
import pinyin from 'pinyin';
import peopleCard from './peopleCard.vue';
const showRequests = ref(false)
const peopleC = ref<{ getOtherUserInfo(str: string): undefined; }>();
const requestList = ref([{
    id: "",
    sendId: "",
    sendName: "",
    flag: -1,
    requestDate: "",
    imgUrl: ""
}
])

const props = defineProps({
  myProp: { type: Object, required: true },
  turnV: { type: Object, required: true },
  sideChoose: { type: Object, required: true },
})
const choose_info = props.myProp;
const turnV = props.turnV;
const sideChoose = props.sideChoose;

// 查看用户信息
const showUserInfo = (id: string) => {
    if (!peopleC.value) return
    peopleC.value.getOtherUserInfo(id)
}

let friends = ref([
    [{
        friendId: "",
        friendName: "",
        imgUrl: "",
        signature: ""
    },]
])

const getPinyinFirstLetter = (str: string) => {
    let firstLetter = '';
    // 判断字符串是否为中文字符
    if (/^[\u4e00-\u9fa5]/.test(str)) {
        // 将中文字符串转换为拼音字符串
        let pinyinArr = pinyin(str, {
            style: pinyin.STYLE_FIRST_LETTER,
        });
        // 获取拼音字符串的首字母
        firstLetter = pinyinArr[0][0].toUpperCase();
    } else {
        // 如果不是中文字符，直接获取字符串的首字母
        firstLetter = str[0].toUpperCase();
    }
    return firstLetter;
}

const getFriends = async () => {
    await getFriendList().then((res: any) => {
        if (res.success) {
            let arr = res.data;
            arr.sort(function (a: any, b: any) {
                return a.friendName.localeCompare(b.friendName, 'zh-Hans-CN', { sensitivity: 'accent' });
            });
            console.log(arr);
            arr.forEach(function (obj: any) {
                // 获取名字的首字母
                let fl = getPinyinFirstLetter(obj.friendName);
                if (fl >= 'A' && fl <= 'Z') {
                    friends.value[fl.charCodeAt(0) - 65].push(obj);
                } else {
                    // 如果首字母不为字母
                    friends.value[26].push(obj);
                }
            });
            console.log(friends);
        } else {
            ElMessage.error("获取失败，请稍后重试")
        }
    })
}

const getFriendRequest = () => {
    showRequests.value = true;
    getFriendRequestList();
}

const getFriendRequestList = async () => {
    await getFriendRequests().then((res: any) => {
        if (res.success) {
            requestList.value = res.data;
        } else {
            ElMessage.error("获取失败，请稍后重试")
        }
    })
}

const acRequest = async (index: number, action: number) => {
    await actionFriendRequest(requestList.value[index].id, requestList.value[index].sendId, action).then((res: any) => {
        if (res.success) {
            console.log(res)
            if (action == 1) {
                ElMessage.success("已同意")
                requestList.value[index].flag = 1;
            } else if (action == 2) {
                ElMessage.success("已拒绝");
                requestList.value[index].flag = 2;
            }
        } else {
            ElMessage.error("获取失败，请稍后重试")
        }
    })
}

onMounted(() => {
    friends.value = Array.from({ length: 27 }, () => []);
    requestList.value = []
    getFriends();
});
</script>
    
<style lang='less' scoped>
.tool_lis {
    display: flex;
    justify-content: flex-end;
    padding: 0 8px;
}

.name_initial {
    overflow: auto;
    padding-right: 8px;

    .initial {
        padding: 0 10px;
        color: #7F7F7F;
        font-size: 14px;
    }

    .u_infos {
        height: 70px;
        display: flex;
        align-items: center;

        .head_img {
            width: 50px;
            height: 50px;
            margin: 0 10px;
            display: flex;
            align-items: center;
            justify-content: flex-start;
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
            min-height: 19px;
            font-size: 14px;
        }

        .u_n {
            width: 150px;
        }

        :hover {
            background-color: transparent;
        }
    }

    .u_infos:hover {
        background-color: rgba(237, 238, 240, 0.5);
        cursor: pointer;
    }

    // 滚动条整体部分
    &::-webkit-scrollbar {
        width: 6px; //对垂直方向滚动条
        height: 6px; //对水平方向滚动条
    }

    //滚动的滑块
    &::-webkit-scrollbar-thumb {
        border-radius: 3px;
        background-color: #ccc //滚动条的颜色
    }

    //内层滚动槽
    &::-webkit-scrollbar-track-piece {
        background-color: #cccccc75;
    }
}

.req_body {
    height: 300px;
    overflow: auto;
    padding-right: 8px;

    .reqs {
        display: flex;
        align-items: center;
        justify-content: space-around;
        padding: 5px 0;

        .head_img {
            width: 50px;
            height: 50px;
            display: flex;
            align-items: center;
            justify-content: flex-start;
        }


        .u_n {
            width: 40%;
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
            min-height: 19px;
            font-size: 14px;
        }

        }

        .a_c{
            width: 35%;
            display: flex;
            justify-content: center;
        }
    }


    &::-webkit-scrollbar {
        width: 6px; //对垂直方向滚动条
        height: 6px; //对水平方向滚动条
    }

    //滚动的滑块
    &::-webkit-scrollbar-thumb {
        border-radius: 3px;
        background-color: #ccc //滚动条的颜色
    }

    //内层滚动槽
    &::-webkit-scrollbar-track-piece {
        background-color: #cccccc75;
    }

}
</style>
