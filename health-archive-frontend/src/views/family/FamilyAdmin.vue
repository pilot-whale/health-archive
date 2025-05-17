<script setup>
import { ref } from 'vue'



//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    familyListUpdate()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    familyListUpdate()
}
const familyList = ref()

//修改个人信息
import { familyListService } from '@/api/family.js'
const familyListUpdate = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value
    }

    let result = await familyListService(params);

    //渲染视图
    total.value = result.data.total;
    familyList.value = result.data.items;
}

familyListUpdate();
</script>




<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>用户组总览</span>
            </div>
        </template>

        <el-table :data="familyList" style="width: 100%">
            <el-table-column label="用户组名称" prop="familyName"></el-table-column>
            <el-table-column label="分享码" prop="shareCode"></el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    </el-card>
</template>

<style scoped>
.el-form-item {
    margin-left: 20px;
    white-space: nowrap;
}
</style>