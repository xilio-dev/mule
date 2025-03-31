<script setup lang="ts">
import {onActivated, onBeforeUnmount, onMounted, onUpdated, reactive, ref} from "vue";
import * as echarts from 'echarts';

const chartInstance = ref()
//const emit = defineEmits(['toggle-follow', 'on-chat'])
const props = defineProps<{
  dateList: string[],
  chartData: object,

}>()
const init = () => {
  var chartDom = document.getElementById('stacked-line-main');
  chartInstance.value = echarts.init(chartDom);
  var option;

  option = {
    title: {
      text: '数据分析'
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['阅读', '点赞', '收藏', '评论']
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    toolbox: {
      feature: {

      }
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: props.dateList
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '阅读',
        type: 'line',
        stack: 'Total',
        data: props.chartData.view
      },
      {
        name: '点赞',
        type: 'line',
        stack: 'Total',
        data: props.chartData.like
      },
      {
        name: '收藏',
        type: 'line',
        stack: 'Total',
        data: props.chartData.collect
      },
      {
        name: '评论',
        type: 'line',
        stack: 'Total',
        data: props.chartData.comment
      },
    ]
  };

  option && chartInstance.value.setOption(option);
}
//不要在 updated 钩子中更改组件的状态，这可能会导致无限的更新循环！
onUpdated(() => {
  init()
})
onMounted(() => {
  init()
})


onBeforeUnmount(() => {
  if (chartInstance.value){
    chartInstance.value.dispose()
  }
})
</script>

<template>
  <main>
    <div class="chart-container" id="stacked-line-main"></div>
  </main>
</template>

<style scoped>
.chart-container {
  width: 850px;
  height: 400px;
}

</style>
