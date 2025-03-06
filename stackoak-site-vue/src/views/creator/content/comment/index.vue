<script setup lang="ts">
import {ref} from "vue";

const activeTab = ref('1');
const activeCommentStatusTab = ref('1');
</script>

<template>
  <a-card :bordered="false">
    <a-tabs v-model:activeKey="activeTab">
      <a-tab-pane key="1" tab="文章评论">
        <a-tabs v-model:activeKey="activeCommentStatusTab">
          <a-tab-pane key="1" tab="评论我的">

          </a-tab-pane>
          <a-tab-pane key="2" tab="待我审核的">

          </a-tab-pane>

          <a-tab-pane key="3" tab="我发布的评论">

          </a-tab-pane>
        </a-tabs>
      </a-tab-pane>
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
