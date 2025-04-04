<script setup lang="ts">

import SidebarItem from "@/layout/Sidebar/SidebarItem.vue";
import {ref} from "vue";
import {usePermissionStore} from "@/store";

const selectedKeys = ref<string[]>(['1']);
const perm = usePermissionStore()
</script>

<template>
  <div class="logo"/>
  <a-menu class="sidebar-container"
          v-model:selectedKeys="selectedKeys"
          theme="dark" mode="inline">
    <SidebarItem   v-for="item in perm.permission.routes" :item="item" :base-path="item.path" :key="item.path"/>
  </a-menu>
</template>

<style scoped>
.sidebar-container {

}

.logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>
