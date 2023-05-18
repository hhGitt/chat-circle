<template>
    <div class="personal_main">
        <div class="p_choose_list">
            <div class="p_head">
                <div>
                    <el-avatar shape="square" :size="80" :src="UserStore.user_info?.face_img_url">
                        <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
                    </el-avatar>
                    <p class="pt10">{{ UserStore.user_info?.username }}</p>
                </div>
            </div>
            <el-divider class="nm" />
            <div class="p_lis">
                <div v-for="item in list" :class="$route.path == item.to ? 'choose' : ''">
                    <RouterLink :to="item.to">
                        <el-icon>
                            <Document />
                        </el-icon>
                        {{ item.label }}
                    </RouterLink>
                </div>
            </div>
        </div>
        <div class="p_info">
            <RouterView />
        </div>
    </div>
</template>

<script  lang='ts' setup>
import { onMounted, reactive } from 'vue';
import { UserStore } from '@/stores/index'

const list = reactive<any>([
    {
        label: "个人资料",
        to: "/info",
    },
    {
        label: "账号信息",
        to: "/account",
    },
]);

onMounted(() => {

});
</script>

<style lang='less' scoped>
.personal_main {
    display: flex;
    margin: auto;
    margin: 20px 10%;
    width: 80%;
    min-height: 800px;
    box-sizing: border-box;
}

.p_choose_list {
    width: 30%;
    margin: 0 20px 0 0;
}

.p_info {
    width: 70%;
}

.p_head {
    height: 20%;
    display: flex;
    justify-content: space-around;
    align-items: center;

    p {
        text-align: center;
    }
}

.p_lis {
    height: 80%;
    display: flex;
    flex-direction: column;

    div {
        height: 5%;
        display: flex;
        border-radius: 8px;

        a {
            display: flex;
            padding: 10px 20px;
            width: 100%;
            text-decoration: none;
            color: rgba(94, 94, 94);
            align-items: center;

            .el-icon {
                margin-right: 6px;
            }
        }
    }

    :hover {
        background-color: rgba(237, 238, 240, 0.5);
    }
}

.choose {
    background-color: rgba(237, 238, 240, 0.5);

    a {
        color: rgb(27, 100, 247) !important;
    }
}
</style>