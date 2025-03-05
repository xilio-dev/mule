<script setup lang="ts">
import {h, reactive, ref, VueElement, watch} from "vue";
import type {ItemType, MenuProps} from "ant-design-vue";
import {SettingOutlined, ProfileOutlined, UserOutlined, DisconnectOutlined, BellOutlined} from "@ant-design/icons-vue";
import router from "@/router";

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
watch(openKeys, val => {
  console.log('openKeys', val);
});

</script>
<template>
  <a-row :gutter="15">
    <a-col :span="4" class="setting-menu">
      <a-menu
          id="setting_left_menu"
          mode="inline"
          :items="items"
          v-model:openKeys="openKeys"
          v-model:selected-keys="selectedKeys"
          @click="handleClick"
      ></a-menu>
    </a-col>
    <a-col :span="14" style="padding-left: 15px">
      <router-view/>
    </a-col>
    <a-col :span="6">
      <a-card title="常见问题" style="height: 150px;background-color: white">

      </a-card>

    </a-col>
  </a-row>
</template>

<style scoped>
.setting-menu {
  background-color: white;
  border: none;
  border-radius: 4px;
}

:deep(.ant-menu-light.ant-menu-root.ant-menu-inline) {
  border-inline-end: none;
}
</style>
