<script setup lang="ts">

</script>

<template>
  <a-card style="width: 100%;" title="黑名单作者">
    <a-card style="width: 160px" v-for="i in 1">
      <a-flex vertical align="center" :gap="4">
        <a-avatar :size="64" src="/avatar.jpeg"/>
        <div>大小姐驾到</div>
        <div style="font-size: 12px;color:rgb(138, 145, 159)">拉黑时间：2023-02-25</div>
        <a-button type="dashed" style="width: 100%;">取消拉黑</a-button>
      </a-flex>
    </a-card>
  </a-card>
</template>

<style scoped>

:deep(.ant-card .ant-card-body) {
  padding: 12px;
  border-radius: 0 0 8px 8px;
}

</style>
