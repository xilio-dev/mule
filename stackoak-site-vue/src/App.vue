<script setup lang="ts">
import {useRoute} from "vue-router";
import {ref, computed, watch} from "vue";
import Navbar from "@/components/Navbar.vue";
import {useThemeStore} from "@/store";

// 获取当前路由对象
const route = useRoute();
const useTheme = useThemeStore()
// 使用 ref 包装 currentPath，确保它是响应式的
const currentPath = ref(route.path);

// 监听路由变化，更新 currentPath
watch(route, (newRoute) => {
  currentPath.value = newRoute.path;
});

// 计算属性 e，判断是否为 /editor 路径
const pathName = computed(() => {
  if (currentPath.value === "/editor") {
    return 'editor';
  } else if (currentPath.value === '/author') {
    return 'author'
  } else if (currentPath.value === '/post') {
    return 'post'
  } else {
    return 'base'
  }
})
// 计算背景样式
const background = computed(() => {
  if (currentPath.value.startsWith('/author')) {
    const bg = useTheme.getAuthorBackground(); // 调用 useTheme 获取背景图
    return bg ? {
      backgroundImage:`url(${bg})`,
      backgroundSize: 'contain',
      minHeight:'1000px'
    } : {};
  }
  return ''; // 始终返回对象，避免类型不一致
});

</script>

<template>
  <header v-if="pathName==='base'">
    <Navbar style="position: fixed;z-index: 90"/>
  </header>

  <main :style="background" style="padding: 60px 10% 0" v-if="pathName==='base'">
    <RouterView/>
  </main>

  <RouterView :style="background" v-if="pathName!=='base'"/>
</template>

<style scoped>

</style>
