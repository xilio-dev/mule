<script setup lang="ts">
import {h, reactive, ref, watch} from "vue";
import type {ItemType, MenuProps} from "ant-design-vue";
import {SettingOutlined, ProfileOutlined, UserOutlined, DisconnectOutlined, BellOutlined} from "@ant-design/icons-vue";
import router from "@/router";
import {useRoute} from "vue-router";

const route = useRoute();
const items: ItemType[] = reactive([
  {
    label: '个人资料',
    key: 'profile',
    icon: () => h(ProfileOutlined),
  }, {
    label: '账号设置',
    key: 'account',
    icon: () => h(UserOutlined),
  },
  {
    label: '消息设置',
    key: 'notification',
    icon: () => h(BellOutlined),
  },
  {
    label: '黑名单',
    key: 'black-list',
    icon: () => h(DisconnectOutlined),
  },
  {
    label: '隐私设置',
    key: 'privacy',
    icon: () => h(SettingOutlined),
  },

]);
const handleClick: MenuProps['onClick'] = e => {
  router.push({path: `/setting/${e.key}`})
};
const selectedKeys = ref(['profile']);
const openKeys = ref(['profile']);

watch(() => route.path, (path) => {
  const segments = path.split('/').filter(Boolean); // 分割路径并过滤空字符串
  const lastSegment = segments.pop(); // 获取最后一个路径段
  if (lastSegment) {
    selectedKeys.value = [lastSegment]; // 设置选中项
    openKeys.value = segments; // 设置展开的父级菜单（如果需要）
  } else {
    selectedKeys.value = []; // 如果路径无效，清除选中项
    openKeys.value = []; // 清除展开的父级菜单
  }
}, {immediate: true}); // 立即执行一次，确保初始路径也能正确设置选中项
</script>
<template>
  <a-row :gutter="15">
    <a-col :span="4" class="setting-menu">
      <a-affix offset-bottom="bottom" :offset-top="59">
        <a-menu
            style="height: 500px;border-radius: 4px"
            id="setting_left_menu"
            mode="inline"
            :items="items"
            v-model:openKeys="openKeys"
            v-model:selected-keys="selectedKeys"
            @click="handleClick"
        ></a-menu>
      </a-affix>
    </a-col>
    <a-col :span="14">
      <router-view/>
    </a-col>
    <a-col :span="6">
      <a-affix offset-bottom="bottom" :offset-top="60">
        <a-card :bordered="false" title="常见问题" style="height: 150px;background-color: white">

        </a-card>
      </a-affix>
    </a-col>
  </a-row>
</template>

<style scoped>
.setting-menu {
  border: none;
  border-radius: 4px;
}

:deep(.ant-menu-light.ant-menu-root.ant-menu-inline) {
  border-inline-end: none;
}
</style>
