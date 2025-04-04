<script setup lang="ts">
import {
  UserOutlined,
  BarChartOutlined,
} from '@ant-design/icons-vue';
import SidebarItem from "@/layout/Sidebar/SidebarItem.vue";
import {ref} from "vue";
import {isExternal} from "@/utils/validate.ts";


const props = defineProps(
    {
      item: {
        type: Object,
        required: true
      },
      isNest: {
        type: Boolean,
        default: false
      },
      basePath: {
        type: String,
        default: ''
      }
    }
)
const onlyOneChild = ref({})
const hasOneShowingChild = (children = [], parent: any) => {
  const showingChildren = children.filter(item => {
    //@ts-ignore
    if (item.hidden) {
      return false
    } else {
      // 如果只有一个显示的孩子，则将使用
      onlyOneChild.value = item
      return true
    }
  })
  // 当只有一个子路由器时，默认显示子路由
  if (showingChildren.length === 1) {
    return true
  }

  // 如果没有要显示的子路由器，则显示父路由器
  if (showingChildren.length === 0) {
    onlyOneChild.value = {...parent, path: '', noShowingChildren: true}
    return true
  }

  return false
}
const resolvePath = (routePath: string) => {
  if (isExternal(routePath)) {
    return routePath
  }
  if (isExternal(props.basePath)) {
    return props.basePath
  }
  return props.basePath + "/" + routePath
}
</script>

<template>

  <div v-if="!item.hidden">
    <template
        v-if="hasOneShowingChild(item.children, item) && (!onlyOneChild.children || onlyOneChild.noShowingChildren)"
        :key="item.path">
      <router-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
        <a-menu-item>
          <BarChartOutlined/>
          <span v-if="onlyOneChild.meta">{{ onlyOneChild.meta.title }}</span>
        </a-menu-item>
      </router-link>
    </template>
    <a-sub-menu v-else key="sub" popper-append-to-body>
      <template #title>
            <span>
              <user-outlined/>
              <span v-if="item.meta">{{ item.meta.title }}</span>
            </span>
      </template>
      <SidebarItem
          :item="child"
          :is-nest="true"
          :base-path="resolvePath(child.path)"
          v-for="child in item.children"
          :key="child.path"/>
    </a-sub-menu>
  </div>
</template>

<style scoped>

</style>
