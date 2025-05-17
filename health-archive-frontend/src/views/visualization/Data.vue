<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <div class="header-title">
                    <span class="title-text">体检数据</span>
                    <span class="subtitle-text">最近</span>
                </div>
                <el-radio-group v-model="selectedPeriod" @change="handlePeriodChange()">
                    <el-radio-button v-for="period in periods" :key="period" :value="period">
                        {{ period }}
                    </el-radio-button>
                </el-radio-group>
            </div>
        </template>

        <!-- 级联选择器 -->
        <div class="cascader-selector">
            <!-- 项目选择 -->
            <el-select v-model="selectedItem" placeholder="请选择体检项目" @change="handleItemChange(selectedItem)"
                style="width: 300px; margin-right: 20px; margin-bottom: 30px;">
                <el-option v-for="item in checkupItem" :key="item.id" :label="item.mainCategory" :value="item.id" />
            </el-select>

            <!-- 指标选择（依赖项目选择） -->
            <el-select v-model="selectedIndicator" placeholder="请选择指标" :disabled="!selectedItem"
                @change="handleIndicatorChange()" style="width: 300px;margin-bottom: 30px;">
                <el-option v-for="indicator in checkupIndicator" :key="indicator.id" :label="indicator.name"
                    :value="indicator.id" />
            </el-select>
        </div>

        <div class="router-view-wrapper">
            <router-view :key="viewKey" :data="currentData" />
        </div>
    </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { onMounted } from 'vue'

// 获取路由实例 
const router = useRouter()

const selectedPeriod = ref('所有')
const periods = ['所有', '十次', '五次', '三次', '两次', '一次']

const checkupItem = ref([
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
const checkupIndicator = ref([])
const selectedItem = ref(null)
const selectedIndicator = ref(null)
const Data = ref(null)

import { checkupItemService, checkupIndicatorService, checkupVisualService } from '@/api/checkupReport'
const fetchItem = async () => {
    const res = await checkupItemService()
    checkupItem.value = res.data
}

fetchItem();

const handleItemChange = (id) => {
    selectedIndicator.value = null
    fetchIndicator(id)
}

const handlePeriodChange = () => {
    if (selectedIndicator.value) {
        fetchData() // 重新获取图表数据
    }
}

const fetchIndicator = async (id) => {
    const res = await checkupIndicatorService(id)
    checkupIndicator.value = res.data
}

const handleIndicatorChange = () => {
    fetchData()
}

const fetchData = async () => {
    if (!selectedIndicator.value) return
    let params = {
        selectedIndicator: selectedIndicator.value,
        selectedPeriod: selectedPeriod.value
    }
    let result = await checkupVisualService(params);
    Data.value = result.data

    const isValidNumberString = (str) => {
        return /^[0-9]+(\.[0-9]+)?$/.test(str); // 例如: "123" 或 "123.45"
    }
    // 判断数据类型 
    const firstResult = Data.value.checkupResultList?.[0]
    const isChartData = firstResult && isValidNumberString(firstResult)

    // 根据数据类型导航到对应路由
    router.replace({
        path: isChartData ? '/visualization/chart' : '/visualization/table',
        query: {
            id: selectedIndicator.value,
            period: selectedPeriod.value
        }
    })
}
onMounted(() => {
    // 检测页面是否刷新（现代浏览器推荐方式）
    const navigationEntries = performance.getEntriesByType('navigation')
    const isPageRefresh = navigationEntries.some(entry => entry.type === 'reload')

    if (isPageRefresh) {
        // 重置路由到基础路径 
        router.replace('/visualization')
        resetAllStates()
    }
})

const resetAllStates = () => {
    selectedPeriod.value = '所有'
    selectedItem.value = null
    selectedIndicator.value = null
}
</script>


<style lang="scss" scoped>
.echarts-container {
    width: 100%;
    height: 400px;
}

.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: flex-start;
        gap: 18px;

        &-title {
            display: flex;
            align-items: baseline;
            gap: 8px;

            .title-text {
                font-size: 18px;
                font-weight: 500;
                color: #333;
            }

            .subtitle-text {
                font-size: 14px;
                color: #666;
            }
        }
    }

    .item-selector {
        margin-bottom: 20px;
    }
}

.router-view-wrapper {
    width: 103%; // 确保宽度填满父容器
    margin: 0; // 移除外边距 
    padding: 0; // 移除内边距 
    border: none !important;
    /* 移除边框 */
    padding: 0 !important;
    /* 移除内边距 */

    /* 如果需要穿透子组件样式 */
    :deep(.el-card) {
        border: none;
        box-shadow: none;
    }
}
</style>