<template>

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
    <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="recentArticle">
      <template #loadMore>
        <div
            v-if="  !loading"
            :style="{ textAlign: 'center', marginTop: '12px', height: '32px', lineHeight: '32px' }">
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
              <a :href="item.href" class="ellipsis-text">{{ item.title }}</a>
            </template>
          </a-list-item-meta>
        </a-list-item>
      </template>

    </a-list>
  </a-card>
</template>
<script lang="ts" setup>
import {StarOutlined, LikeOutlined, MessageOutlined} from '@ant-design/icons-vue';
import {DownOutlined} from '@ant-design/icons-vue';

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
  pageSize: 5,
};
import {nextTick, ref} from 'vue';
import {message, Modal} from 'ant-design-vue';
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
        removeArticleById(id)
        message.success("已删除")
      })
    },

  });

}
//加载近期文章

const queryParam = ref({
  current: 1,
  size: 10,
  categoryId: 0,
  showType: 3
})
const removeArticleById = (aid: string) => {
  recentArticle.value = recentArticle.value.filter(article => article.id !== aid);
}
const recentArticle = ref([])
const loadRecentArticle = async () => {
  await articleList(queryParam.value).then(res => {
    if (res == null) {
      recentArticle.value = []
      return
    }
    recentArticle.value = res.records
  })
}

//近期文章
import {onMounted} from 'vue';
import {articleList, deleteArticle} from "@/api/post.ts";

const count = 3;
const fakeDataUrl = `https://randomuser.me/api/?results=${count}&inc=name,gender,email,nat,picture&noinfo`;
const initLoading = ref(true);
const data = ref([]);
const list = ref([]);
onMounted(() => {
  //加载近期文章
  loadRecentArticle()
  fetch(fakeDataUrl)
      .then(res => res.json())
      .then(res => {
        initLoading.value = false;
        data.value = res.results;
        list.value = res.results;
      });
});

const loading = ref(false);

const onLoadMore = () => {
  loading.value = true;


  queryParam.value.size+= 10
  list.value = data.value.concat(
      [...new Array(count)].map(() => ({loading: true, name: {}, picture: {}})),
  );
  fetch(fakeDataUrl)
      .then(res => res.json())
      .then(res => {
        const newData = data.value.concat(res.results);
        loading.value = false;
        data.value = newData;
        list.value = newData;
        nextTick(() => {

          window.dispatchEvent(new Event('resize'));
        });
      });
};

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
