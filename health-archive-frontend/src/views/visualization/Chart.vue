<template>
    <el-card class="page-container">
        <div>
            <span style="margin-left: 67px;">单位：{{ chartData?.unit ? chartData.unit : "--" }}</span>
            <!-- 详细检查结果表格 -->
            <div ref="chartDom" class="echarts-container" />
        </div>
    </el-card>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
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

// 1. DOM 引用（自动暴露给模板）
const chartDom = ref(null)
let chartInstance = null

const chartData = ref(null)

import { checkupVisualService } from '@/api/checkupReport'

const fetchChartData = async () => {
    if (!selectedIndicator.value) return
    let params = {
        selectedIndicator: selectedIndicator?.value,
        selectedPeriod: selectedPeriod?.value
    }
    let result = await checkupVisualService(params);
    chartData.value = result.data
    chartData.value.createDateList = result.data.createDateList.reverse()
    chartData.value.checkupResultList = result.data.checkupResultList.reverse()
    chartData.value.hospitalNameList = result.data.hospitalNameList.reverse()
    updateChart()
}

watch(
    () => route.query,
    (newQuery) => {
        selectedIndicator.value = newQuery.id
        selectedPeriod.value = newQuery.period
        fetchChartData()
    },
    { immediate: true }
)

const updateChart = () => {
    if (!chartInstance || !chartData.value.checkupResultList?.length) return

    const markLineData = []
    if (chartData.value.referenceHigh != null) {
        markLineData.push({
            name: '参考值上界',
            yAxis: chartData.value.referenceHigh,
            lineStyle: { color: '#ff0000', width: 2, type: 'dashed' },
            label: {
                formatter: '上界: {c}',
                position: 'end',
                color: '#000000'
            }
        })
    }
    if (chartData.value.referenceLow != null) {
        markLineData.push({
            name: '参考值下界',
            yAxis: chartData.value.referenceLow,
            lineStyle: { color: '#ff0000', width: 2, type: 'dashed' },
            label: {
                formatter: '下界: {c}',
                position: 'end',
                color: '#000000'
            }
        })
    }

    // 创建基础配置 
    const baseOption = {
        xAxis: {
            type: 'category',
            data: chartData.value.createDateList
        },
        yAxis: {
            type: 'value',
            max: (value) => Math.max(value.max, chartData.value.referenceHigh ?? -Infinity)
        },
        series: [
            {
                name: '检查结果',
                data: chartData.value.checkupResultList,
                type: 'line',
                smooth: true,
                showAllSymbol: true,
                label: 'show',
                lineStyle: {
                    width: 3,
                    color: '#5470c6'
                },
                itemStyle: {
                    color: '#5470c6'
                }
            }
        ],
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                let result = params[0].axisValue + '<br/>';
                params.forEach(item => {
                    const marker = item.marker;
                    const seriesName = item.seriesName;
                    const value = item.value;
                    result += `${marker} ${seriesName}: ${value}<br/>`;

                    if (chartData.value.referenceHigh !== null) {
                        result += `${marker} 参考值上界: ${chartData.value.referenceHigh}<br/>`;
                    }
                    if (chartData.value.referenceLow !== null) {
                        result += `${marker} 参考值下界: ${chartData.value.referenceLow}<br/>`;
                    }
                });
                return result;
            }
        },
        // 添加完整的图例说明 
        graphic: {
            elements: [
                // 蓝色实线（检查结果）
                {
                    type: 'line',
                    left: 270,  // 从左侧60px开始 
                    top: 10,
                    shape: {
                        x1: 0,
                        y1: 0,
                        x2: 60,
                        y2: 0
                    },
                    style: {
                        stroke: '#5470c6',
                        lineWidth: 3
                    }
                },
                // 蓝色点 
                {
                    type: 'circle',
                    left: 298,
                    top: 8.7,
                    shape: {
                        cx: 30,
                        cy: 0,
                        r: 4
                    },
                    style: {
                        fill: '#5470c6'
                    }
                },
                // "检查结果"文字 
                {
                    type: 'text',
                    left: 344,
                    top: 5,
                    style: {
                        text: '检查结果',
                        fill: '#000000',
                        fontSize: 12
                    },
                    x: 70  // 文字向右偏移70px 
                },
                // 红色虚线（参考值）
                {
                    type: 'line',
                    left: 500,  // 从左侧200px开始 
                    top: 10,
                    shape: {
                        x1: 0,
                        y1: 0,
                        x2: 60,
                        y2: 0
                    },
                    style: {
                        stroke: '#ff0000',
                        lineWidth: 2,
                        lineDash: [5, 5]
                    }
                },
                // "参考值"文字 
                {
                    type: 'text',
                    left: 570,
                    top: 5,
                    style: {
                        text: '参考值',
                        fill: '#000000',
                        fontSize: 12
                    },
                    x: 70  // 文字向右偏移70px 
                }
            ]
        },
        color: ['#5470c6']
    }

    // 添加参考线系列 
    if (markLineData.length > 0) {
        baseOption.series.push({
            name: '参考值',
            type: 'line',
            data: [],
            silent: true,
            symbol: 'none',
            lineStyle: {
                opacity: 0
            },
            markLine: {
                silent: true,
                symbol: 'none',
                data: markLineData,
                lineStyle: {
                    color: '#ff0000',
                    width: 2,
                    type: 'dashed'
                },
                label: {
                    show: true,
                    position: 'end',
                    color: '#000000',
                    formatter: (params) => params.name
                }
            }
        });
    }

    // 应用配置 
    chartInstance.setOption(baseOption, {
        notMerge: true,
        lazyUpdate: false
    });
}
// 3. 初始化图表 
const initChart = () => {
    if (!chartDom.value) return
    chartInstance = echarts.init(chartDom.value)
    chartInstance.setOption({   // 初始化空配置 
        xAxis: { type: 'category', data: [] },
        yAxis: { type: 'value' },
        series: [{ data: [], type: 'line' }],
        legend: { data: [] }
    })
    window.addEventListener('resize', handleResize)
}

// 4. 响应式调整 
const handleResize = () => {
    chartInstance?.resize()
}

// 5. 生命周期 
onMounted(initChart)
onBeforeUnmount(() => {
    window.removeEventListener('resize', handleResize)
    chartInstance?.dispose()
})
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
</style>