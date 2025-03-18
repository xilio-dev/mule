<script setup lang="ts">
import {onMounted, ref, watch} from "vue";
import router from "@/router";
import {useRoute} from "vue-router";
import {getUnreadCount} from "@/api/notify.ts";
/*------------------------------------变量定义------------------------------------------*/
const unread = ref({})


/*------------------------------------生命周期-------------------------------------------*/

onMounted(()=>{
 // loadUnReadMessageCount()
})

/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
const loadUnReadMessageCount = async () => {
  const res = await getUnreadCount();
  if (res) {
    unread.value = res || {}
  }
}



/*------------------------------------核心业务--------------------------------------------*/




/*-------------------------------------其他函数-------------------------------------------*/
const route = useRoute();

const tabClick = (key: string) => {
  router.push({path: `/msg/${key}`})
}
const activeKey = ref('comment');
watch(() => route.path, (path) => {
  const segments = path.split('/').filter(Boolean);
  activeKey.value = segments.pop();

}, {immediate: true}); // 立即执行一次，确保初始路径也能正确设置选中项
</script>

<template>
  <a-card title="消息管理" :bordered="false" :body-style="{boxShadow:'none',padding:'5px 24px' }">
    <template #extra>
      <RouterLink to="/setting/notification" target="_blank">
        <a-button type="link" size="small">消息设置</a-button>
      </RouterLink>
    </template>
    <a-tabs v-model:activeKey="activeKey" @tabClick="tabClick">
      <a-tab-pane key="comment" tab="评论">
        <router-view v-if="activeKey==='comment'"/>
      </a-tab-pane>
      <a-tab-pane key="like" tab="赞和收藏">
        <router-view v-if="activeKey==='like'"/>
      </a-tab-pane>
      <a-tab-pane key="chat" tab="私信">
        <router-view v-if="activeKey==='chat'"/>
      </a-tab-pane>
      <a-tab-pane key="attention" tab="新增粉丝">
        <router-view v-if="activeKey==='attention'"/>
      </a-tab-pane>
      <a-tab-pane key="system" tab="系统通知">
        <router-view v-if="activeKey==='system'"/>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<style scoped>
:deep(.ant-card .ant-card-head) {
  border-bottom: none;
}
</style>
