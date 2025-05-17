<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
import useFamilyInfoStore from '@/stores/familyInfo.js'


const userInfoStore = useUserInfoStore();
const familyInfoStore = useFamilyInfoStore();

const formData = ref(JSON.parse(JSON.stringify(userInfoStore.info))); 
const familyInfo = ref(JSON.parse(JSON.stringify(familyInfoStore.info))); 
const rules = {
    name: [
        { required: true, message: '请输入姓名', trigger: 'blur' },
        { min: 1, max: 20, message: '长度为1~20位非空字符', trigger: 'blur' }
    ],
    userName: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 1, max: 20, message: '长度为1~20位非空字符', trigger: 'blur' }
    ],
    residentIdentityCardNumber: [
        { required: true, message: '请输入身份证号', trigger: 'blur' },
        { len: 18, message: '长度为18位非空字符', trigger: 'blur' }
    ],
}

//修改个人信息
import { userInfoUpdateService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
const updateUserInfo = async () => {
    //调用接口
    formData.value.password = null;
    let result = await userInfoUpdateService(formData.value);
    ElMessage.success(result.msg ? result.msg : '修改成功');

    //修改pinia中的个人信息
    userInfoStore.setInfo({ ...formData.value })
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>基本资料</span>
            </div>
        </template>
        <el-row>
            <el-col :span="14">
                <el-form :model="formData" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="身份证号：" prop="residentIdentityCardNumber">
                        <el-input v-model="formData.residentIdentityCardNumber" placeholder="请输入身份证号"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名：" prop="name">
                        <el-input v-model="formData.name" placeholder="请输入姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="用户名：" prop="userName">
                        <el-input v-model="formData.userName" placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item label="所属用户组：">
                        <span>{{ familyInfo.familyName || '尚未加入用户组' }}</span>
                    </el-form-item>
                    <el-form-item label="用户组分享码：">
                        <span>{{ familyInfo.shareCode || '--' }}</span>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserInfo">提交修改</el-button>
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