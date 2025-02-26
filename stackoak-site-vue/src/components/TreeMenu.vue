<template>
  <div class="tree-menu">
    <!-- 遍历当前层级的菜单项 -->
    <div
        v-for="item in menuData"
        :key="item.id"
        class="tree-node"
        :class="{ 'is-open': item.isOpen }"
        @click="toggle(item)"
    >
      <!-- 切换图标 -->
      <span v-if="item.children && item.children.length > 0" class="toggle-icon">
        <span v-if="item.isOpen">▼</span>
        <span v-else>▶</span>
      </span>
      <span v-else class="toggle-icon">•</span>
      <span class="node-label">{{ item.label }}</span>
    </div>

    <!-- 递归渲染子菜单 -->
    <TreeMenu
        v-for="item in menuData"
        :key="`sub-${item.id}`"
        v-if="item.isOpen&&item.children && item.children.length > 0"
        :menu-data="item.children"
        class="sub-menu"
    />
  </div>
</template>

<script>
import {defineComponent, reactive} from 'vue';

export default defineComponent({
  name: 'TreeMenu',
  props: {
    menuData: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    // 使用 reactive 包装菜单数据，使其响应式
    const menuData = reactive(
        props.menuData.map((item) => ({
          ...item,
          isOpen: item.isOpen || false, // 确保每个菜单项都有 isOpen 属性
          children: Array.isArray(item.children) ? item.children : [], // 确保 children 是数组
        }))
    );

    // 切换菜单项的展开状态
    const toggle = (item) => {
      item.isOpen = !item.isOpen; // 切换当前菜单项的展开状态
    };

    return {
      menuData,
      toggle,
    };
  },
});
</script>

<style scoped>
.tree-menu {
  margin-left: 20px;
}

.tree-node {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 5px 0;
}

.toggle-icon {
  margin-right: 8px;
  font-size: 16px;
}

.node-label {
  font-size: 14px;
}

.sub-menu {
  margin-left: 20px;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
