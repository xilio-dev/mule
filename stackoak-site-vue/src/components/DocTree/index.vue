<!--<template>-->
<!--  <ul>-->
<!--    <li v-for="node in nodes" :key="node.id">-->
<!--      <div @click="toggle(node)">-->
<!--        <span v-if="node.children && node.children.length">-->
<!--        <div v-if="!node.isOpen" class="arrow-right"/>-->
<!--        <div v-else class="arrow-down"/>-->
<!--        </span>-->

<!--        {{ node.name }}-->

<!--      </div>-->
<!--      <DocumentTree-->
<!--          v-if="node.children && node.children.length && node.isOpen"-->
<!--          :nodes="node.children"-->
<!--      />-->
<!--    </li>-->
<!--  </ul>-->
<!--</template>-->

<!--<script setup lang="ts">-->
<!--import {defineProps} from 'vue';-->
<!--import DocumentTree from "@/components/DocTree/index.vue"-->

<!--const props = defineProps({-->
<!--  nodes: {-->
<!--    type: Array,-->
<!--    required: true-->
<!--  }-->
<!--});-->

<!--const toggle = (node) => {-->
<!--  if (!('isOpen' in node)) {-->
<!--    node.isOpen = false; // 初始化 isOpen 属性-->
<!--  }-->
<!--  node.isOpen = !node.isOpen; // 切换状态-->
<!--};-->
<!--</script>-->

<!--<style scoped>-->
<!--ul {-->
<!--  list-style-type: none;-->
<!--  padding-left: 20px;-->
<!--}-->

<!--li {-->
<!--  cursor: pointer;-->
<!--}-->

<!--.arrow-right {-->
<!--  display: inline-block;-->
<!--  width: 0;-->
<!--  height: 0;-->
<!--  border-top: 5px solid transparent;-->
<!--  border-bottom: 5px solid transparent;-->
<!--  border-left: 10px solid black; /* 箭头颜色 */-->
<!--}-->

<!--.arrow-down {-->
<!--  display: inline-block;-->
<!--  width: 0;-->
<!--  height: 0;-->
<!--  border-left: 5px solid transparent;-->
<!--  border-right: 5px solid transparent;-->
<!--  border-top: 10px solid black; /* 箭头颜色 */-->
<!--}-->
<!--</style>-->
<template>
  <ul>
    <li v-for="node in nodes" :key="node.id">
      <!-- 动态绑定样式 -->
      <div
          @click="toggle(node)"
          :class="['node-label', `level-${level}`]"
      >
        <!-- 箭头图标 -->
        <span v-if="node.children && node.children.length">
          <div v-if="!node.isOpen" class="arrow-right" />
          <div v-else class="arrow-down" />
        </span>
        {{ node.name }}
        <!-- 节点名称 -->
      </div>
      <!-- 递归渲染子节点 -->
      <DocumentTree
          v-if="node.children && node.children.length && node.isOpen"
          :nodes="node.children"
          :level="level + 1"
      />
    </li>
  </ul>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import DocumentTree from "@/components/DocTree/index.vue"
const props = defineProps({
  nodes: {
    type: Array,
    required: true
  },
  level: {
    type: Number,
    default: 1 // 默认层级为 1
  }
});

const toggle = (node) => {
  if (!('isOpen' in node)) {
    node.isOpen = false; // 初始化 isOpen 属性
  }
  node.isOpen = !node.isOpen; // 切换状态
};
</script>

<style scoped>
ul {
  list-style-type: none;
  padding-left: 20px;
}

li {
  cursor: pointer;
}

/* 节点标签样式 */
.node-label {
  display: flex;
  align-items: center;
  gap: 5px; /* 图标和文字之间的间距 */
}

/* 一级节点样式 */
.level-1 {
  font-size: 16px;
  font-weight: bold;
}

/* 二级及以后节点样式 */
.level-2,
.level-3,
.level-4 {
  font-size: 14px;
  font-weight: normal;
}

/* 箭头图标样式 */
.arrow-right {
  display: inline-block;
  width: 0;
  height: 0;
  border-top: 5px solid transparent;
  border-bottom: 5px solid transparent;
  border-left: 10px solid black; /* 箭头颜色 */
}

.arrow-down {
  display: inline-block;
  width: 0;
  height: 0;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-top: 10px solid black; /* 箭头颜色 */
}




</style>
