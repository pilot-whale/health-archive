<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
import useFamilyInfoStore from '@/stores/familyInfo';

const userInfoStore = useUserInfoStore();
const familyInfoStore = useFamilyInfoStore();
const userInfo = ref({ ...userInfoStore.info })

const changeData = ref({
    shareCode: '',
    password: '',
})

const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== changeData.value.newPassword) {
        callback(new Error('请确保两次输入的密码一样'))
    } else {
        callback()
    }
}

//定义表单校验规则
const rules = {
    originalPassword: [
        { required: true, message: '请输入原密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    newPassword: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    reNewPassword: [
        { required: true, validator: checkRePassword, trigger: 'blur' }
    ]
}


import { userInfoUpdateService} from '@/api/user.js'
import { ElMessage } from 'element-plus'
import { familyChangeService, familyInfoService } from '../../api/family';
const updateUserInfo = async () => {
    let familyIdList = await familyChangeService(changeData.value)
    if (!familyIdList.data[0]) {
        ElMessage.error("分享码或密码错误");
        return;
    }
    //调用接口
    userInfo.value.password = null;
    userInfo.value.familyGroup = familyIdList.data[0];
    let result = await userInfoUpdateService(userInfo.value);
    ElMessage.success(result.msg ? result.msg : '加入新用户组成功');
    userInfoStore.setInfo({ ...userInfo.value })

    let result2 = await familyInfoService(familyIdList.data[0])
    familyInfoStore.setInfo({ ...result2.data })
}
</script>



<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>更换用户组</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="changeData" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="用户组分享码：" prop="shareCode">
                        <el-input v-model="changeData.shareCode" placeholder="请输入用户组分享码"></el-input>
                    </el-form-item>
                    <el-form-item label="用户组密码：" prop="password">
                        <el-input v-model="changeData.password" placeholder="请输入用户组密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserInfo">加入用户组</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>

<style scoped>
.el-form-item {
    margin-left: 20px;
    white-space: nowrap;
}
</style>