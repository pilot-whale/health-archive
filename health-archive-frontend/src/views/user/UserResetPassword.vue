<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router';
import { useTokenStore } from '@/stores/token.js'
import useUserInfoStore from '@/stores/userInfo.js'

const tokenStore = useTokenStore();
const router = useRouter()
const userInfoStore = useUserInfoStore();
const userInfo = ref({ ...userInfoStore.info })

const changeData = ref({
    originalPassword: '',
    newPassword: '',
    reNewPassword: '',
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


import { userInfoUpdateService, userInfoCheckService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
const updateUserInfo = async () => {
    let whether = await userInfoCheckService(changeData.value.originalPassword)
    if (!whether.data) {
        ElMessage.error("原密码错误");
        return;
    }
    //调用接口
    userInfo.value.password = changeData.value.newPassword;
    let result = await userInfoUpdateService(userInfo.value);

    ElMessage.success(result.msg ? result.msg : '密码重置成功，请重新登录');
    tokenStore.removeToken()
    userInfoStore.removeInfo()
    router.replace('/login')
}
</script>




<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>重置密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="changeData" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="原密码" prop="originalPassword">
                        <el-input v-model="changeData.originalPassword" placeholder="请输入原密码"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPassword">
                        <el-input v-model="changeData.newPassword" placeholder="请输入新密码"></el-input>
                    </el-form-item>
                    <el-form-item label="确认新密码" prop="reNewPassword">
                        <el-input v-model="changeData.reNewPassword" placeholder="请再次输入确认新密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserInfo">重置密码</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>