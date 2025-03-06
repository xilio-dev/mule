<script setup lang="ts">
import {ref} from "vue";

const activeTab = ref('1');
const activecolumnStatusTab = ref('1');
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
