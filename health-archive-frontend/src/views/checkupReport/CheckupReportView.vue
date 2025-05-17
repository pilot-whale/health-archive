<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const checkupData = ref(null)
const loading = ref(false) // 加载状态 
const selectedItem = ref(null)

// 体检项目大类数据模型 
const checkupItems = ref([
    { "id": 1, "mainCategory": "一般检查" },
    { "id": 2, "mainCategory": "内科" },
    { "id": 3, "mainCategory": "尿常规" },
    { "id": 4, "mainCategory": "生化-血脂" },
    { "id": 5, "mainCategory": "免疫发光" },
    { "id": 6, "mainCategory": "免疫" },
    { "id": 7, "mainCategory": "生化-肝功" },
    { "id": 8, "mainCategory": "血常规五分类" },
    { "id": 9, "mainCategory": "外科" },
    { "id": 10, "mainCategory": "心电图" }
])
import { checkupItemService } from '@/api/checkupReport'
const fetchItem = async () => {
    const res = await checkupItemService()
    checkupItems.value = res.data
}
fetchItem();

// 从路由获取报告基本信息 
const reportData = ref(null)
onMounted(() => {
    reportData.value = {
        id: route.query.id,
        hospitalName: route.query.hospitalName,
        createDate: route.query.createDate
    }
})

// 获取详细检查结果 
import { checkupDataService } from '@/api/checkupReport'

const fetchData = async () => {
    if (!selectedItem.value || !reportData.value?.id) return

    loading.value = true
    try {
        const params = {
            itemId: selectedItem.value,
            reportId: reportData.value.id
        }
        const res = await checkupDataService(params)
        checkupData.value = res.data
    } catch (error) {
        console.error(' 获取详细数据失败:', error)
        checkupData.value = []
    } finally {
        loading.value = false
    }
}

// 当选择变化时获取数据 
const handleItemChange = () => {
    fetchData()
}

</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>机构名称：{{ reportData?.hospitalName || '--' }}</span>
                <span>体检时间：{{ reportData?.createDate || '--' }}</span>
            </div>
        </template>

        <!-- 体检大类选择器 -->
        <div class="item-selector">
            <el-select v-model="selectedItem" clearable placeholder="请选择体检项目大类" @change="handleItemChange"
                style="width: 300px; margin-bottom: 20px;">
                <el-option v-for="item in checkupItems" :key="item.id" :label="item.mainCategory" :value="item.id" />
            </el-select>
        </div>

        <!-- 详细检查结果表格 -->
        <el-table :data="checkupData" v-loading="loading" style="width: 100%" stripe>
            <el-table-column prop="name" label="项目名称" width="230" />
            <el-table-column prop="result" label="检查结果" width="240" />
            <el-table-column prop="reference" label="参考值" width="170" />
            <el-table-column prop="unit" label="单位" width="150" />
            <el-table-column prop="exception" label="是否异常" width="100" />
        </el-table>
    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: flex-start;
        justify-content: flex-start;
        gap: 30px;
    }

    .item-selector {
        margin-bottom: 20px;
    }
}
</style>