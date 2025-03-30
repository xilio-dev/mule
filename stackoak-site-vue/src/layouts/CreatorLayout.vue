<template>

  <a-row :gutter="12">
    <a-col :span="4">
      <div style="text-align: center;background-color: white;padding-top: 30px;padding-bottom: 12px">
        <img style="width: 100px;height: 100px;border-radius: 4px;margin-bottom: 5px"
             :src="ImageUtils.getImgUrl(userStore.userinfo.avatar||'')"/>
        <div>{{ userStore.userinfo.nickname }}</div>
      </div>
      <a-affix offset-bottom="bottom" :offset-top="60">
        <a-menu
            style="margin-bottom: 80px"
            id="creator_left_menu"
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
        <a-card class="announcement-card" :bordered="false" title="公告栏" style="min-height: 150px;">
          <a-flex vertical :gap="10">
            <div class="announcement-item" v-for="(item, index) in announcementList" :key="item.id">
              {{ index + 1 }}. {{ item.title }}
            </div>
          </a-flex>
        </a-card>
      </a-affix>
    </a-col>
  </a-row>

</template>
<script lang="ts" setup>
import router from "@/router";
import {useUserStore} from '@/store';
import {reactive, ref, watch, VueElement, h, onMounted} from 'vue';
import {MailOutlined, AppstoreOutlined, SettingOutlined} from '@ant-design/icons-vue';
import {type MenuProps, type ItemType} from 'ant-design-vue';
import {ImageUtils} from "@/utils/file.ts";
import {Https} from "@/utils/request/https.ts";
import {API} from "@/api/ApiConfig.ts";

const userStore = useUserStore()

/*------------------------------------变量定义------------------------------------------*/
const selectedKeys = ref<string[]>(['index']);
const openKeys = ref<string[]>(['index', 'content', 'analysis']);

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
    key: 'index',
    icon: () => h(MailOutlined),
  },
  getItem('内容管理', 'content', () => h(MailOutlined), [
    getItem('文章管理', 'article', null),
    getItem('合集管理', 'column', null),
    getItem('评论管理', 'comment', null),
  ]),

  getItem('数据中心', 'analysis', () => h(AppstoreOutlined), [
    getItem('文章数据', 'articles'),
    getItem('粉丝数据', 'fans'),
  ]),
  getItem('创作工具', 'tool', () => h(AppstoreOutlined), [
    getItem('图片素材', 'images'),
  ]),
  getItem('个性设置', 'setting', () => h(SettingOutlined), [
    getItem('博客设置', 'config-blog'),
  ]),
]);
const announcementQuery = reactive({
  current: 1,
  size: 5
})
const announcementList = ref([])
/*------------------------------------生命周期-------------------------------------------*/
watch(openKeys, val => {
  console.log('openKeys', val);
});
onMounted(() => {
  loadAnnouncement()
})
/*------------------------------------数据加载--------------------------------------------*/
const loadAnnouncement = async () => {
  const res =await Https.action(API.ANNOUNCEMENT.list, announcementQuery)
  announcementList.value=res.records
}

/*------------------------------------核心业务--------------------------------------------*/
const handleClick: MenuProps['onClick'] = e => {
  console.log(e)
  if (e.keyPath) {
    let path = e.keyPath?.join("/")
    console.log(path)
    router.push({path: `/creator/${path}`})
  }
};


</script>

<style scoped>
.setting-menu {
  min-height: 500px;
  background-color: white;
  border: none;
  border-radius: 4px;
}

:deep(.ant-menu-light.ant-menu-root.ant-menu-inline) {
  border-inline-end: none;
}

/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}

:deep(.ant-card .ant-card-body ) {
  padding: 8px 20px;
}
.announcement-card{
  box-shadow: none;
}
.announcement-item{
  cursor: pointer;
  color: #8491a5;
  font-weight: 400;
  line-height: 1.5;
}
.announcement-item:first-child{
  color: #303133;
}
</style>
