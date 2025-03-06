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
                    <a-popconfirm
                        title="你确定要删除该篇文章?"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="onRemoveRecentArticle(item.id)">
                      <span>删除</span>
                    </a-popconfirm>
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
import {ref} from 'vue';
import {message} from 'ant-design-vue';
//删除近期文章
const onRemoveRecentArticle = (id: string) => {
  message.success("删除成功！" + id)
}
//加载近期文章

const queryParam = ref({
  current: 1,
  size: 10,
  categoryId: 0,
  showType: 3
})
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
import {articleList} from "@/api/post.ts";

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
