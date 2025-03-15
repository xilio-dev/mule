<script setup lang="ts">
import {onMounted, ref} from "vue";
import {articleListByUser} from "@/api/post.ts";
import ArticleCenterList from '@/components/ArticleCenterList/index.vue'
import router from "@/router";

/*------------------------------------变量定义------------------------------------------*/
const articles = ref([])

const activeTab = ref('1');
const activeArticleStatusTab = ref('-1');
/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadArticle()
})


/*------------------------------------初始化---------------------------------------------*/
const queryParam = ref({
  current: 1,
  size: 10,
})
/*------------------------------------事件监听------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
const loadArticle = async () => {
  await articleListByUser(queryParam.value).then(res => {
    articles.value = res.records
  })
}


/*------------------------------------核心业务--------------------------------------------*/
//根据标签切换文章列表
const onTagClick = () => {
 // queryParam.value.status = activeArticleStatusTab.value
  loadArticle()
}
const onCallEdit=(e:any)=>{
  window.open(`/editor?id=${e.item.id}`,'_blank')
}

/*-------------------------------------其他函数-------------------------------------------*/

</script>

<template>

  <a-card :bordered="false">
    <a-tabs  v-model:activeKey="activeTab" @tabClick="onTagClick">
      <a-tab-pane key="1" tab="文章">
        <a-tabs v-model:activeKey="activeArticleStatusTab" @tabClick="onTagClick">
          <a-tab-pane key="-1" tab="全部">
            <ArticleCenterList @on-call-edit="onCallEdit" :article-list="articles"/>
          </a-tab-pane>
          <a-tab-pane key="2" tab="已发布">
            <ArticleCenterList :article-list="articles"/>
          </a-tab-pane>
          <a-tab-pane key="3" tab="仅我可见">
            <ArticleCenterList :article-list="articles"/>
          </a-tab-pane>
          <a-tab-pane key="4" tab="密码可见">
            <ArticleCenterList :article-list="articles"/>
          </a-tab-pane>
          <a-tab-pane key="5" tab="审核中">
            <ArticleCenterList :article-list="articles"/>
          </a-tab-pane>
          <a-tab-pane key="6" tab="未通过">
            <ArticleCenterList :article-list="articles"/>
          </a-tab-pane>
        </a-tabs>
      </a-tab-pane>
      <a-tab-pane key="7" tab="草稿箱">
        <ArticleCenterList :article-list="articles"/>
      </a-tab-pane>
      <a-tab-pane key="8" tab="回收站">
        <ArticleCenterList :article-list="articles"/>
      </a-tab-pane>
      <template #rightExtra>
        <a-button type="primary" size="small">写文章</a-button>
      </template>
    </a-tabs>
  </a-card>

</template>

<style scoped>
/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}

.no-wrap {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  font-weight: 400;
}

</style>
