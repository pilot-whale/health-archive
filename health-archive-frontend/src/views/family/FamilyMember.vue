<script setup>
import {
    Delete,
    Search
} from '@element-plus/icons-vue'

import { ref } from 'vue'

//文章列表数据模型
const familyMember = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    familyMemberList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    familyMemberList()
}


import { familyMemberService } from '@/api/family'

//获取文章列表数据
const familyMemberList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value
    }

    let result = await familyMemberService(params);

    //渲染视图
    total.value = result.data.total;
    familyMember.value = result.data.items;
}

familyMemberList();

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { Plus } from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const checkupReportModel = ref({
    hospitalName: '',
    createDate: ''
})


//导入token
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();

//上传成功的回调函数
const uploadSuccess = (result) => {
    articleModel.value.coverImg = result.data;
    console.log(result.data);
}

//添加文章
import { ElMessage } from 'element-plus'
const addArticle = async (clickState) => {
    //把发布状态赋值给数据模型
    articleModel.value.state = clickState;

    //调用接口
    let result = await articleAddService(articleModel.value);

    ElMessage.success(result.msg ? result.msg : '添加成功');

    //让抽屉消失
    visibleDrawer.value = false;

    //刷新当前列表
    articleList()
}

import { ElMessageBox } from 'element-plus'
import { checkupReportDeleteService } from '../../api/checkupReport'
import { userInfoService, userLogInfoService, userTempLogin } from '../../api/user'
const deleteCheckupReport = (row) => {
    // 提示用户，弹出确认框
    ElMessageBox.confirm(
        '是否要删除这份体检报告？',
        '提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(async () => {
        // 调用接口
        let result = await checkupReportDeleteService(row.id)
        ElMessage({
            type: 'success',
            message: '删除成功',
        })
        // 刷新列表
        checkupReportList();
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '删除取消',
        })
    })
}

import { useRouter } from 'vue-router'
import useUserInfoStore from '@/stores/userInfo.js'
import useFamilyInfoStore from '@/stores/familyInfo';

const userInfoStore = useUserInfoStore();
const familyInfoStore = useFamilyInfoStore();
const router = useRouter()
const tempLogin = async (row) => {
    const user = await userLogInfoService(row.residentIdentityCardNumber)
    console.log(user.data.id)
    const result1 = await userTempLogin(user.data.id)
    console.log(result1)

    tokenStore.removeToken()
    userInfoStore.removeInfo()
    familyInfoStore.removeInfo()
    ElMessage.success(result1.msg ? result1.msg : '登录成功')
    //把得到的token存储到pinia中
    tokenStore.setToken(result1.data)
    //跳转到首页 路由完成跳转
    getUserInfo();
    router.push('/')

}

const getUserInfo = async () => {
    //调用接口
    let result = await userInfoService();
    //数据存储到pinia中
    userInfoStore.setInfo(result.data);
    if (userInfoStore.info.familyGroup) {
        getFamilyInfo();
    }
}

const getFamilyInfo = async () => {
    //调用接口
    let result = await familyInfoService(userInfoStore.info.familyGroup);
    //数据存储到pinia中
    familyInfoStore.setInfo(result.data);
}


</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>查看用户组成员</span>
            </div>
        </template>
        <!-- 搜索表单 -->
        <!-- <el-form inline>
            <el-form-item label="文章分类：">
                <el-select placeholder="请选择" v-model="categoryId">
                    <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="发布状态：">
                <el-select placeholder="请选择" v-model="state">
                    <el-option label="已发布" value="已发布"></el-option>
                    <el-option label="草稿" value="草稿"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="articleList">搜索</el-button>
                <el-button @click="categoryId = ''; state = ''">重置</el-button>
            </el-form-item>
        </el-form> -->
        <!-- 文章列表 -->
        <el-table :data="familyMember" style="width: 100%">
            <el-table-column label="成员用户名" width="400" prop="userName"></el-table-column>
            <el-table-column label="姓名" prop="name"></el-table-column>
            <el-table-column label="登录账号" width="100">
                <template #default="{ row }">
                    <el-button :icon="Search" circle plain type="primary" @click="tempLogin(row)"></el-button>
                </template>
            </el-table-column>
            <!-- <el-table-column label="删除" width="100">
                <template #default="{ row }">
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCheckupReport(row)"></el-button>
                </template>
            </el-table-column> -->
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" title="上传体检报告" direction="rtl" size="50%">
            <!-- 添加文章表单 -->
            <el-form :model="articleModel" label-width="100px">
                <el-form-item label="体检机构名称">
                    <el-input v-model="articleModel.title" placeholder="请输入名称"></el-input>
                </el-form-item>
                <el-form-item label="体检日期">
                    <el-select placeholder="请选择输入日期" v-model="articleModel.categoryId">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <!-- <el-form-item label="文章封面">

              
                        auto-upload:设置是否自动上传
                        action:设置服务器接口路径
                        name:设置上传的文件字段名
                        headers:设置上传的请求头
                        on-success:设置上传成功的回调函数
      

                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
                        name="file" :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess">
                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item> -->
                <el-form-item label="文章内容">
                    <div class="editor">
                        <quill-editor theme="snow" v-model:content="articleModel.content" contentType="html">
                        </quill-editor>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addArticle('已发布')">发布</el-button>
                    <el-button type="info" @click="addArticle('草稿')">草稿</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>



<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>