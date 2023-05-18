<template>
    <div class="info_main">
        <div class="face_img">
            <div>
                <el-avatar :size="120" :src="userInfo.imgUrl" @click="handlePictureCardPreview" />
                <el-button type="primary" circle size="large">
                    <el-icon>
                        <Edit />
                    </el-icon>
                    <input type="file" @change="getFile($event)" style="opacity:0" />
                </el-button>
            </div>
            <el-dialog v-model="dialogVisible">
                <img :src="dialogImageUrl" alt="Preview Image" style="width: 70%;height: 70%;object-fit: contain;" />
            </el-dialog>
        </div>
        <div class="info_from">
            <el-form label-position="top" label-width="100px" :model="userInfo" :inline="true">
                <el-form-item label="昵称">
                    <el-input v-model="userInfo.username" />
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="userInfo.sex">
                        <el-radio :label="0" border>保密</el-radio>
                        <el-radio :label="1" border>男</el-radio>
                        <el-radio :label="2" border>女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="生日">
                    <el-date-picker v-model="userInfo.birthday" type="date" placeholder="请选择" />
                </el-form-item>
                <el-form-item label="地区">
                    <el-cascader v-model="region" :options="regions" :props="props" :key="key" placeholder="请选择" />
                </el-form-item>
                <el-form-item label="个性签名" style="width:100%">
                    <el-input v-model="userInfo.signature" maxlength="30" show-word-limit type="textarea"
                        placeholder="你的个性签名" :rows="4" />
                </el-form-item>
                <el-form-item label="个人爱好" style="width:100%">
                    <el-input v-model="userInfo.hobbies" maxlength="30" show-word-limit type="textarea" placeholder="展示你的爱好"
                        :rows="2" />
                </el-form-item>
                <el-form-item style="width:100%;">
                    <el-button type="success" style="margin-left: auto;" @click="updateInfo">保存</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script  lang='ts' setup>
import { UserStore } from '@/stores';
import { onMounted, reactive, ref, defineAsyncComponent } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Edit, Hide } from '@element-plus/icons-vue'
import { getBigHeadPortrait, getRegionList, getUserInfo, putFaceImage, updateUserInfo } from '@/request/api/userInfo';
import type { userInfoParam } from '@/request/interface/userInfo';
import { DateUtils } from '@/utils/DateUtils';
let userInfo = ref({
    username: "",
    imgUrl: "",
    sex: 0,
    birthday: "",
    signature: "",
    hobbies: "",
    regionId: "",
})

const props = {
    expandTrigger: 'hover' as const,
}

let regions = ref([]);
let region: Array<string> = reactive([]);
let key = ref(0)

const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const handlePictureCardPreview = async () => {
    await getBigHeadPortrait().then((res: any) => {
        if (res.success) {
            dialogImageUrl.value = res.data.imgBigUrl
            dialogVisible.value = true
        }
        else {
            ElMessage.error("获取失败，请稍后重试")
        }
    })

}

const getUInfo = async () => {
    await getUserInfo().then((res: any) => {
        if (res.success) {
            userInfo.value = res.data
            region = showRegion(userInfo.value.regionId);
            key.value++;
        }
    })
}

interface regionInter {
    value: string,
    label: string,
    children: Array<regionInter>
}

const showRegion = (regionId: string): Array<string> => {
    var lis: Array<string> = Array();
    for (var i in regions.value) {
        var r: regionInter = regions.value[i];
        if (r.value == regionId) return [regionId];
        lis.push(r.value);
        var flag = dfs(r.children, regionId, lis);
        if (flag) break;
        lis.pop();
    }
    return reactive(lis);
}

const dfs = (region: any, target: string, lis: Array<string>): boolean => {
    for (var i in region) {
        if (region[i].value == target) {
            lis.push(region[i].value)
            return true;
        }
        lis.push(region[i].value);
        var flag = dfs(region[i].children, target, lis)
        if (flag) return true;
        lis.pop();
    }
    return false;
}

//获取上传图片
const getFile = async (event: any) => {
    let file = event.target.files[0];
    // console.log(file)
    event.preventDefault();

    if (!/image\/\w+/.test(file.type)) {
        ElMessage.error("请确保文件为图像类型");
        return false;
    }

    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = async function () {
        const userImage = {
            faceData: this.result
        }
        await putFaceImage(userImage).then((res: any) => {
            if (res.success) {
                userInfo.value.imgUrl = res.data.imgUrl
                UserStore.user_info.face_img_url = res.data.imgUrl
                UserStore.updateUserInfos(UserStore.user_info)
            } else {
                ElMessage.error(res.message)
            }
        })
    }

}

const getRegions = async () => {
    await getRegionList().then((res: any) => {
        if (res.success) {
            regions.value = res.data;
        }
    })
}

const updateInfo = () => {
    ElMessageBox.confirm("确认保存？", "Warning", {
        type: "warning",
    }).then(
        async () => {
            const userParam: userInfoParam = {
                username: userInfo.value.username,
                sex: userInfo.value.sex,
                signature: userInfo.value.signature,
                birthday: userInfo.value.birthday,
                hobbies: userInfo.value.hobbies,
                regionId: region[region.length-1]
            }
            await updateUserInfo(userParam).then((res: any) => {
                if (res.success) {
                    ElMessage.success("更新成功");
                    UserStore.user_info.username = userInfo.value.username
                    UserStore.user_info.signature = userInfo.value.signature
                } else {
                    ElMessage.error(res.message);
                }
            })
        }
    ).catch(() => { });

}

onMounted(async () => {
    await getRegions();
    await getUInfo();
});
</script>

<style lang='less' scoped>
.info_main {
    background-color: rgb(255, 255, 255);
    border-radius: 15px;
    padding: 20px;
    box-shadow: 0 0 #0000, 0 0 #0000, 0px 1px 3px rgba(0, 0, 0, .04), 0px 2px 8px rgba(0, 0, 0, .08);
}

.face_img {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 100%;

    div {
        position: relative;

        .el-button {
            position: absolute;
            right: 0;
            bottom: 0;

            input {
                position: absolute;
                right: 0;
                width: 100%;
                height: 100%;
            }

        }
    }

    .el-dialog {
        img {
            margin: auto;
            display: flex;
        }
    }

}

.info_from {
    width: 100%;

    .el-form {
        align-items: center;
        justify-content: space-between;
    }

    .el-form-item {
        padding: 15px 0;
        width: 48%;
        margin: 0;

        :deep(.el-cascader) {
            width: 100% !important;
        }
    }

    .el-radio-group {
        justify-content: space-between;
        width: 100%;

        .el-radio {
            width: 30%;
            margin: 0;
        }
    }

    :deep(.el-date-editor) {
        width: 100% !important;
    }



    :deep(.el-input__wrapper) {
        width: 100% !important;
        box-sizing: border-box;
    }
}
</style>