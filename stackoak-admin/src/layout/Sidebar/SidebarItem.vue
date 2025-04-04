<script setup lang="ts">
import {
  UserOutlined,
  BarChartOutlined,
} from '@ant-design/icons-vue';

defineProps({
  item: { type: Object, default: () => ({}) },
})
</script>

<template>
  <a-menu-item key="1">
    <BarChartOutlined/>
    <span>menu</span>
  </a-menu-item>
  <a-sub-menu key="sub1" popper-append-to-body>
    <template #title>
            <span>
              <user-outlined/>
              <span>User</span>
            </span>
    </template>
    <a-menu-item key="3">Tom</a-menu-item>
    <a-menu-item key="4">Bill</a-menu-item>
    <a-menu-item key="5">Alex</a-menu-item>
  </a-sub-menu>
</template>

<style scoped>

</style>
