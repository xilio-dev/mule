<script setup lang="ts">
import {onMounted, ref} from "vue";
import {columnListByUser} from "@/api/column.ts";


/*------------------------------------变量定义------------------------------------------*/
const activeTab = ref('1');
const activecolumnStatusTab = ref('1');
const columns = ref([])
const queryParam = ref({
  current: 1,
  size: 10,
  key: '',
  status: 1
})

/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadColumns()
})


/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
const loadColumns = async () => {
  await columnListByUser(queryParam.value).then(res => {
    columns.value = res.records
  })
}


/*------------------------------------核心业务--------------------------------------------*/




/*-------------------------------------其他函数-------------------------------------------*/
</script>

<template>
  <a-card :bordered="false">
    <a-tabs v-model:activeKey="activeTab">
      <a-tab-pane key="1" tab="专栏管理">
        <a-tabs v-model:activeKey="activecolumnStatusTab">
          <a-tab-pane key="1" tab="全部">

          </a-tab-pane>
          <a-tab-pane key="2" tab="已发布">

          </a-tab-pane>

          <a-tab-pane key="3" tab="审核中">

          </a-tab-pane>
          <a-tab-pane key="4" tab="未通过">

          </a-tab-pane>

        </a-tabs>
      </a-tab-pane>
      <a-tab-pane key="2" tab="专栏订阅">
        <a-tabs v-model:activeKey="activecolumnStatusTab">
          <a-tab-pane key="1" tab="订阅我的">

          </a-tab-pane>
          <a-tab-pane key="2" tab="我的订阅">

          </a-tab-pane>
        </a-tabs>
      </a-tab-pane>
      <template #rightExtra>
        <a-button type="primary" size="small">新建专栏</a-button>
      </template>
    </a-tabs>

  </a-card>
</template>

<style scoped>
/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}


</style>
