<template>

  <a-row :gutter="12">
    <a-col :span="4">

      <div style="text-align: center;background-color: white;padding-top: 30px;padding-bottom: 15px">
        <img style="width: 100px;height: 100px;border-radius: 4px" src="@/assets/avatar.jpeg"/>
        <div>xilio1024</div>
      </div>
      <a-affix offset-bottom="bottom"  :offset-top="60">
      <a-menu
          id="creator_left_menu"
          v-model:openKeys="openKeys"
          mode="inline"
          :items="items"
          @click="handleClick"
      ></a-menu>
      </a-affix>
    </a-col>
    <a-col :span="14">
      <a-card :bordered="false" title="数据面板" style="height: 305px;box-shadow: none">
        <template #extra>
          <RouterLink to="/editor">
            <a-button type="primary">去创作</a-button>
          </RouterLink>

        </template>

        <div class="analyze-container">
          <div class="analyze-item">点赞数量</div>
          <div class="analyze-item">今日访问量</div>
          <div class="analyze-item">粉丝新增</div>
          <div class="analyze-item">收藏数量</div>
          <div class="analyze-item">评论数量</div>
          <div class="analyze-item">点赞量</div>
        </div>
      </a-card>
      <a-card :bordered="false" title="近期文章" style="margin-top: 20px;box-shadow: none">
        <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="listData">
          <template #renderItem="{ item }">
            <a-list-item key="item.title">
              <template #actions>
          <span v-for="{ icon, text } in actions" :key="icon">
            <component :is="icon" style="margin-right: 8px"/>
            {{ text }}
          </span>
              </template>
              <template #extra>
                操作
              </template>
              <a-list-item-meta class="title-container">
                <template #title>
                  <a :href="item.href" class="ellipsis-text">{{ item.title }}</a>
                </template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-affix offset-bottom="bottom"  :offset-top="60">
      <a-card title="公告栏" style="height: 150px">

      </a-card>
      </a-affix>
    </a-col>
  </a-row>

</template>
<script lang="ts" setup>
import {StarOutlined, LikeOutlined, MessageOutlined} from '@ant-design/icons-vue';

const listData: Record<string, string>[] = [];

for (let i = 0; i < 23; i++) {
  listData.push({
    href: 'https://www.antdv.com/',
    title: `我的第一篇文章我的第一篇文章我的第一篇文章我的第一篇文章我的第一篇文章我的第一篇文章 ${i}`,
  });
}

const pagination = {
  onChange: (page: number) => {
    console.log(page);
  },
  pageSize: 30,
};
const actions: Record<string, any>[] = [
  {icon: StarOutlined, text: '156'},
  {icon: LikeOutlined, text: '156'},
  {icon: MessageOutlined, text: '2'},
];

import {reactive, ref, watch, VueElement, h} from 'vue';
import {MailOutlined, AppstoreOutlined, SettingOutlined} from '@ant-design/icons-vue';
import type {MenuProps, ItemType} from 'ant-design-vue';

const selectedKeys = ref<string[]>(['1']);
const openKeys = ref<string[]>(['sub1', 'sub2']);

function getItem(
    label: VueElement | string,
    key: string,
    icon?: any,
    children?: ItemType[],
    type?: 'group',
): ItemType {
  return {
    key,
    icon,
    children,
    label,
    type,
  } as ItemType;
}

const items: ItemType[] = reactive([
  {
    label: '首页',
    key: 'home',
    icon: () => h(MailOutlined),
  },
  getItem('内容管理', 'sub1', () => h(MailOutlined), [
    getItem('文章管理', 'g1', null),
    getItem('合集管理', 'g2', null),
    getItem('评论管理', 'g3', null),
  ]),

  getItem('数据中心', 'sub2', () => h(AppstoreOutlined), [
    getItem('文章数据', '5'),
    getItem('粉丝数据', '6'),
  ]),
  getItem('创作工具', 'sub3', () => h(AppstoreOutlined), [
    getItem('图片素材', '7'),
    getItem('数据导出', '8'),
  ]),
  getItem('个性设置', 'sub4', () => h(SettingOutlined), [
    getItem('博客设置', '9'),
  ]),
]);

const handleClick: MenuProps['onClick'] = e => {
  console.log('click', e);
};

watch(openKeys, val => {
  console.log('openKeys', val);
});
//近期文章
import {onMounted, nextTick} from 'vue';

const count = 3;
const fakeDataUrl = `https://randomuser.me/api/?results=${count}&inc=name,gender,email,nat,picture&noinfo`;

const initLoading = ref(true);
const loading = ref(false);
const data = ref([]);
const list = ref([]);
onMounted(() => {
  fetch(fakeDataUrl)
      .then(res => res.json())
      .then(res => {
        initLoading.value = false;
        data.value = res.results;
        list.value = res.results;
      });
});
</script>

<style scoped>
.analyze-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3列，每列等宽 */
  grid-template-rows: auto auto; /* 2行，高度自适应 */
  gap: 20px; /* 设置行和列的间隔 */
  margin: 0 auto;
}

.analyze-item {
  background-color: #f7f8fa;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  border-radius: 5px;
  height: 50px;
}

/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}

/* 父容器的宽度限制 */
.title-container {
  width: 100%; /* 确保父容器宽度有限制 */
  overflow: hidden; /* 防止子元素超出 */
}

/* 子元素的省略号样式 */
.ellipsis-text {
  white-space: nowrap; /* 防止文本换行 */
  overflow: hidden; /* 隐藏超出部分 */
  text-overflow: ellipsis; /* 显示省略号 */
  max-width: 100%; /* 确保宽度不超过父容器 */
  display: block; /* 确保样式生效 */
}
</style>
