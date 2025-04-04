<script setup lang="ts">
import {
  UserOutlined,
  BarChartOutlined,
} from '@ant-design/icons-vue';
import SidebarItem from "@/layout/Sidebar/SidebarItem.vue";

const props=defineProps({
  item: {type: Object, default: () => ({})},

})
</script>

<template>
  <div v-if="!item.hidden">
    <a-menu-item :key="item.path">
<!--      <BarChartOutlined/>-->
      <span>{{ item.name }}</span>
    </a-menu-item>
    <a-sub-menu key="sub1" popper-append-to-body>
      <template #title>
            <span>
<!--              <user-outlined/>-->
              <span>{{item.name}}</span>
            </span>
      </template>
      <SidebarItem
          v-for="child in item.children"
          :key="child.path"/>
    </a-sub-menu>
  </div>
</template>

<style scoped>

</style>
