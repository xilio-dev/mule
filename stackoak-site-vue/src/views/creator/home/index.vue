<template>

  <a-card :bordered="false" title="数据面板" style="height: 305px;box-shadow: none">
    <template #extra>
      <RouterLink to="/editor">
        <a-button type="primary">去创作</a-button>
      </RouterLink>
    </template>

    <div class="analyze-container">
      <div class="analyze-item">
        <a-flex vertical gap="4" align="center">
          <span>阅读数量</span>
          <span>{{userinfo.gotViewCount}}</span>
        </a-flex>
      </div>
      <div class="analyze-item">
        <a-flex vertical gap="4" align="center">
          <span>粉丝总数</span>
          <span>{{userinfo.fansCount}}</span>
        </a-flex>
      </div>
      <div class="analyze-item">
        <a-flex vertical gap="4" align="center">
          <span>收藏数量</span>
          <span>{{userinfo.gotCollectCount}}</span>
        </a-flex>
      </div>
      <div class="analyze-item">
        <a-flex vertical gap="4" align="center">
          <span>文章数量</span>
          <span>{{userinfo.articleCount}}</span>
        </a-flex>
      </div>
      <div class="analyze-item">
        <a-flex vertical gap="4" align="center">
          <span>点赞数量</span>
          <span>{{userinfo.likeArticleCount}}</span>
        </a-flex>
      </div>
      <div class="analyze-item">
        <a-flex vertical gap="4" align="center">
          <span>关注数量</span>
          <span>{{userinfo.followCount}}</span>
        </a-flex>
      </div>
    </div>
  </a-card>
  <a-card :bordered="false" title="近期文章" style="margin-top: 20px;box-shadow: none">
    <a-list item-layout="vertical" size="large" :data-source="articles">
      <template #loadMore>
        <div
            :style="{ textAlign: 'center', marginTop: '12px',marginBottom:'30px', height: '32px', lineHeight: '32px' }">
          <a-button @click="onLoadMore">加载更多</a-button>
        </div>
      </template>
      <template #renderItem="{ item }">
        <a-list-item key="item.title">
          <template #actions>
            <span><component :is="StarOutlined" style="margin-right: 8px"/>{{ item.collectCount }}</span>
            <span><component :is="LikeOutlined" style="margin-right: 8px"/>{{ item.likeCount }}</span>
            <span><component :is="MessageOutlined" style="margin-right: 8px"/>{{ item.commentCount }}</span>
          </template>
          <template #extra>
            <a-dropdown>
              <a class="ant-dropdown-link" @click.prevent>
                操作
                <DownOutlined/>
              </a>
              <template #overlay>
                <a-menu>
                  <a-menu-item>
                    <router-link :to="{ path: '/editor', query: { id: item.id } }" target="_blank">编辑
                    </router-link>
                  </a-menu-item>
                  <a-menu-item>
                    <span @click="onRemoveRecentArticle(item.id)">删除</span>
                  </a-menu-item>
                  <a-menu-item>
                    <router-link :to="`/post/${item.id}`" target="_blank">浏览</router-link>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </template>
          <a-list-item-meta class="title-container">
            <template #title>
              <router-link :to="`/post/${item.id}`" target="_blank">
                <span class="ellipsis-text">{{ item.title }}</span>
              </router-link>
            </template>
          </a-list-item-meta>
        </a-list-item>
      </template>

    </a-list>
  </a-card>
</template>
<script lang="ts" setup>
import {nextTick, ref} from 'vue';
import {message, Modal} from 'ant-design-vue';
import {StarOutlined, LikeOutlined, MessageOutlined} from '@ant-design/icons-vue';
import {DownOutlined} from '@ant-design/icons-vue';
import {onMounted} from 'vue';
import {articleList, deleteArticle} from "@/api/post.ts";
import {getUserInfo} from "@/api/user.ts";

/*------------------------------------变量定义------------------------------------------*/
const articles = ref([])
const data = ref([]);
const userinfo = ref({})
/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadUserInfo()
  //加载近期文章
  loadRecentArticle()
});
/*------------------------------------初始化---------------------------------------------*/
const queryParam = ref({
  current: 1,
  size: 10,
  categoryId: 0,
  showType: 3,
})
/*------------------------------------数据加载--------------------------------------------*/
//加载近期文章-初始化
const loadRecentArticle = async () => {
  await articleList(queryParam.value).then(res => {
    articles.value = res.records || []
    data.value = res.records || []
  })
}
//加载更多近期文章
const onLoadMore = async () => {
  try {
    queryParam.value.current += 1;
    articles.value = data.value.concat(); // 更新列表为当前数据
    const res = await articleList(queryParam.value); // 异步请求文章列表
    const newData = data.value.concat(res.records); // 合并新数据
    data.value = newData; // 更新数据
    articles.value = newData; // 更新列表
    // 在 DOM 更新后触发 resize 事件
    nextTick(() => {
      window.dispatchEvent(new Event('resize'));
    });
  } catch (error) {
    console.error('加载更多数据失败:', error);
  }
};
const loadUserInfo = async () => {
  const res = await getUserInfo()
  res ? userinfo.value = res : {}
}
/*------------------------------------核心业务--------------------------------------------*/
//删除近期文章
const onRemoveRecentArticle = (id: string) => {
  Modal.confirm({
    title: '您确定删除该篇文章?',
    content: '删除后会放到回收站',
    okText: '确认',
    okType: 'danger',
    cancelText: '取消',
    onOk() {
      deleteArticle({aid: id}).then(res => {
        //过滤掉已经删除的文章
        articles.value = articles.value.filter(article => article.id !== aid);
        message.success("已删除")
      })
    },
  });
}
/*-------------------------------------其他函数-------------------------------------------*/
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

.ant-dropdown-link {
  color: #292828;
}

:deep(.ant-list-lg .ant-list-item ) {
  padding: 10px 10px;
}

:deep(.ant-list-vertical .ant-list-item .ant-list-item-action ) {
  margin-block-start: 0;
}

:deep(.ant-list-vertical .ant-list-item .ant-list-item-meta) {
  margin-block-end: 0;
}

/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}

:deep(.ant-card .ant-card-body ) {
  padding: 10px;
}
</style>
