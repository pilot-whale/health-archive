<template>
    <el-card class="page-container">
        <!-- 文章列表 -->
        <el-table :data="tableData" style="width: 100%">
            <el-table-column label="体检时间" width="430" prop="createDate"></el-table-column>
            <el-table-column label="检查结果" prop="checkupResult"></el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
    </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { watch } from 'vue'


const route = useRoute()
const selectedPeriod = ref(null)
const selectedIndicator = ref(null)
onMounted(() => {
    selectedIndicator.value = route.query.id
    selectedPeriod.value = route.query.period

    fetchChartData()
})

const chartData = ref(null)

import { checkupVisualService } from '@/api/checkupReport'
import { computed } from 'vue'

const fetchChartData = async () => {
    if (!selectedIndicator.value) return
    let params = {
        selectedIndicator: selectedIndicator?.value,
        selectedPeriod: selectedPeriod?.value
    }
    let result = await checkupVisualService(params);
    chartData.value = result.data
}

const tableData = computed(() => {
    if (!chartData.value)  return []
    
    const { createDateList = [], checkupResultList = [] } = chartData.value  
    
    return createDateList.map((createDate,  index) => ({
        createDate,
        checkupResult: checkupResultList[index] || '无结果'
    }))
})

watch(
    () => route.query,
    (newQuery) => {
        selectedIndicator.value = newQuery.id
        selectedPeriod.value = newQuery.period
        fetchChartData()
    },
    { immediate: true }
)
</script>


<style lang="scss" scoped>
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
}
</style>