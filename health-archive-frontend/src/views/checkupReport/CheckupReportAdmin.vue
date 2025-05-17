<script setup>
import {
    Delete,
    Search
} from '@element-plus/icons-vue'

import { ref } from 'vue'

//文章列表数据模型
const checkupReports = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数
const residentIdentityCardNumber = ref('')

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    checkupReportList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    checkupReportList()
}


import { checkupAdminService } from '@/api/checkupReport'

//获取体检报告列表数据
const checkupReportList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        residentIdentityCardNumber: residentIdentityCardNumber.value
    }

    let result = await checkupAdminService(params);

    //渲染视图
    total.value = result.data.total;
    checkupReports.value = result.data.items;
}

import '@vueup/vue-quill/dist/vue-quill.snow.css'
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

import { useRouter } from 'vue-router'
const router = useRouter()
const viewCheckupReport = (row) => {
    // 通过路由跳转并传递参数 
    router.push({
        path: '/checkupReport/view',  // 使用路径参数 
        query: {
            // 可以同时传递其他需要的数据 
            id: row.id,
            hospitalName: row.hospitalName,
            createDate: row.createDate
        }
    })
}

import { ElMessageBox } from 'element-plus'
import { checkupReportDeleteService } from '../../api/checkupReport'
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
const articleModel = ref({
    title: '',
    categoryId: '',
    coverImg: '',
    content: '',
    state: '',
    type: [],
})
const uploadDialogVisible = ref(false)
</script>




<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>体检记录管理</span>
                <el-button type="primary" @click="uploadDialogVisible = true">上传体检记录</el-button>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="用户身份证号：" style="width: 470px">
                <el-input placeholder="输入身份证号查询用户" v-model="residentIdentityCardNumber"></el-input>
            </el-form-item>
            <el-form-item class="el-form-item--right">
                <el-button type="primary" @click="checkupReportList">搜索</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="checkupReports" style="width: 100%">
            <el-table-column label="体检机构名称" width="400" prop="hospitalName"></el-table-column>
            <el-table-column label="体检日期" prop="createDate"></el-table-column>
            <el-table-column label="查看报告" width="100">
                <template #default="{ row }">
                    <el-button :icon="Search" circle plain type="primary" @click="viewCheckupReport(row)"></el-button>
                </template>
            </el-table-column>
            <el-table-column label="删除" width="100">
                <template #default="{ row }">
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCheckupReport(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        <!-- 上传文件的弹窗 -->
        <el-dialog v-model="uploadDialogVisible" title="上传体检记录" width="30%">
            <el-upload class="upload-demo" drag action="/api/upload"
                multiple
                :on-success="handleUploadSuccess"
                :on-error="handleUploadError"
                :before-upload="beforeUpload"
                >
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                    拖拽文件到此处或<em>点击上传</em>
                </div>
                <template #tip>
                    <div class="el-upload__tip">
                        只能上传.JSON格式文件，且不超过100MB
                    </div>
                </template>
            </el-upload>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="uploadDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submitUpload">确认上传</el-button>
                </span>
            </template>
        </el-dialog>
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

.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>