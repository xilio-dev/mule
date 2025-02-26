<script setup lang="ts">
import { useRoute } from "vue-router";
import {ref, computed, watch} from "vue";
import Navbar from "@/components/Navbar.vue";

// 获取当前路由对象
const route = useRoute();

// 使用 ref 包装 currentPath，确保它是响应式的
const currentPath = ref(route.path);

// 监听路由变化，更新 currentPath
watch(route, (newRoute) => {
  currentPath.value = newRoute.path;
});

// 计算属性 e，判断是否为 /editor 路径
const pathName = computed(() =>{
  if (currentPath.value === "/editor"){
    return 'editor';
  }else if (currentPath.value==='/opensource/document/detail'){
    return 'doc'
  }else {
    return 'base'
  }
})

</script>

<template>
  <header v-if="pathName==='base'">
    <Navbar style="position: fixed;z-index: 90"/>
  </header>
  <main style="padding: 60px 10% 0;" v-if="pathName==='base'">
    <RouterView/>
  </main>

  <RouterView v-if="pathName!=='base'"/>

</template>

<style scoped>

</style>
