<script setup lang="ts">
import {onActivated, onBeforeUnmount, onMounted, reactive, ref} from "vue";
import * as echarts from 'echarts';
const chartInstance=ref()
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
        saveAsImage: {}
      }
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['2022/02/03', '2022/02/03', '2022-02-05', '2022-02-06', '2022-02-07', '2022-02-08', '2022/02/03']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '阅读',
        type: 'line',
        stack: 'Total',
        data: [120, 132, 101, 134, 90, 230, 210]
      },
      {
        name: '点赞',
        type: 'line',
        stack: 'Total',
        data: [220, 182, 191, 234, 290, 330, 310]
      },
      {
        name: '收藏',
        type: 'line',
        stack: 'Total',
        data: [150, 232, 201, 154, 190, 330, 410]
      },
      {
        name: '评论',
        type: 'line',
        stack: 'Total',
        data: [320, 332, 301, 334, 390, 330, 320]
      },
    ]
  };

  option && chartInstance.value.setOption(option);
}
onMounted(() => {
  init()
})
onBeforeUnmount(()=>{

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
