<script setup lang="ts">

import {UserOutlined} from "@ant-design/icons-vue";
</script>

<template>
  <a-menu-item key="1">
    <desktop-outlined />
    <span>Option 1</span>
  </a-menu-item>
  <a-sub-menu key="sub1">
    <template #title>
            <span>
              <user-outlined />
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
