<script setup lang="ts">
import {useRoute} from "vue-router";
import {ref, watch} from "vue";
import {searchArticle} from "@/api/search.ts";
import {StarOutlined, LikeOutlined, MessageOutlined} from '@ant-design/icons-vue';
import {ImageUtils} from "@/utils/file.ts";
const route = useRoute()
const articles = ref([])
// 监听查询参数的变化，发生变化重新获取数据
const doSearch = (keyword: any) => {
  searchArticle({keyword: keyword, page: 1, size: 10}).then((res) => {
    articles.value = res.records;
  });
}
watch(
    () => route.query.keyword,
    (newKeyword, oldKeyword) => {
      if (newKeyword) {
        doSearch(newKeyword)
      }
    },
    {immediate: true}
);

const activeKey = ref('1');
const search_key = ref(undefined)
const onSearch = () => {
  doSearch(search_key.value)
}
const pagination = {
  onChange: (page: number) => {
    console.log(page);
  },
  pageSize: 3,
};
const goArticleDetail=(id:string)=>{
  const url = `/post#/post/${id}`;
  window.open(url, '_blank');
}
</script>

<template>
  <a-row style="padding-bottom: 10px;padding-top: 5px ">
    <a-row style="width: 100%;padding-bottom: 15px">
      <a-input-search
          v-model:value="search_key"
          @search="onSearch"
          class="search"
          size="large"
          enter-button
          placeholder="请输入搜索内容"
      />
    </a-row>
    <a-row style="width: 100%;background-color: white">
      <a-card style="width: 100%;box-shadow: none" :bordered="false">
        <a-tabs v-model:activeKey="activeKey">
          <a-tab-pane key="1" tab="文章">
            <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="articles">
              <template #renderItem="{ item }">
                <a-list-item :key="item.id" class="article-item-action" @click="goArticleDetail(item.id)">
                  <template #actions>
                    <span><component :is="StarOutlined" style="margin-right: 8px"/>{{ item.collectCount }}</span>
                    <span><component :is="LikeOutlined" style="margin-right: 8px"/>{{ item.likeCount }}</span>
                    <span><component :is="MessageOutlined" style="margin-right: 8px"/>{{ item.commentCount }}</span>
                  </template>
                  <template #extra>
                    <a-image
                        style="width: 110px;height: 74px;object-fit: cover;"
                        alt="logo"
                        :preview="false"
                        fallback="data:image/png;base64,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"
                        :src="ImageUtils.getImgUrl(item.cover)"
                    />
                  </template>
                  <a-list-item-meta  >
                    <template #description>
                      <span class="article-description" v-html=" item.content "></span>
                    </template>
                    <template #title>
                      <span class="article-title" v-html=" item.title "></span>
                    </template>
                  </a-list-item-meta>

                </a-list-item>

              </template>
            </a-list>
          </a-tab-pane>
          <a-tab-pane key="2" tab="专栏" force-render>

          </a-tab-pane>
          <a-tab-pane key="3" tab="用户">

          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-row>
  </a-row>
</template>

<style scoped>
.search {
  width: 100%;
  margin: 0 8%
}

/* 卡片修改*/
:deep(.ant-card .ant-card-body) {
  padding: 0 20px;
  border-radius: 0 0 8px 8px;
}
/*List列表修改*/
.article-title {
  font-weight: 600;
  font-size: 16px;
  color: #252933;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.article-description{
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  font-weight: 400;
  color: #8a919f;
}
:deep(.ant-list-vertical .ant-list-item .ant-list-item-meta .ant-list-item-meta-title) {
   margin-block-end: 0;

}
:deep(.ant-list-vertical .ant-list-item .ant-list-item-action) {
  margin-block-start: 8px;
}
:deep(.ant-list-vertical .ant-list-item .ant-list-item-meta ){
  margin-block-end: 0;
}
:deep(.ant-list-lg .ant-list-item) {
   padding: 8px 8px;
}

.article-item-action{
  cursor: pointer;
}
</style>
