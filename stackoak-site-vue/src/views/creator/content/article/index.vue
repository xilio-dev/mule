<script setup lang="ts">
import {onMounted, ref} from "vue";
import {articleListByUser} from "@/api/post.ts";
import SoList from '@/components/SoList/index.vue'

import {CommonUtil} from "@/utils/common.ts";
import {ARTICLE} from "@/constants/article.ts";

/*------------------------------------变量定义------------------------------------------*/
const articles = ref([])

const activeTab = ref('1');
const activeArticleStatusTab = ref('-1');
const comments = ref([])/*文章评论列表*/
/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadArticle()
})


/*------------------------------------初始化---------------------------------------------*/
const queryParam = ref({
  current: 1,
  size: 10,
  status: -1
})
const tabs = [
  {key: '-1', label: '全部'},
  {key: '1', label: '已发布'},
  {key: '0', label: '审核中'},
  {key: '2', label: '仅我可见'},
  {key: '4', label: '粉丝可见'},
  {key: '3', label: '密码可见'},
  {key: '7', label: '未通过'},
];
/*------------------------------------事件监听------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
const loadArticle = async () => {
  await articleListByUser(queryParam.value).then(res => {
    articles.value = res.records
  })
}
//加载某一篇文章的评论信息
const loadArticleComment = () => {

}

/*------------------------------------核心业务--------------------------------------------*/
//根据标签切换文章列表
const onTagClick = (key: any) => {
  queryParam.value.status = key
  loadArticle()
}
const onCallEdit = (e: any) => {
  window.open(`/editor?id=${e.item.id}`, '_blank')
}
const onCallComment = (e: any) => {
}
const onCallData = (e: any) => {
}
//删除文章
const onRemoveArticle = () => {

}
/*-------------------------------------其他函数-------------------------------------------*/

</script>

<template>

  <a-card :bordered="false">
    <a-tabs v-model:activeKey="activeTab" @tabClick="onTagClick">
      <a-tab-pane key="1" tab="文章">
        <a-tabs v-model:activeKey="activeArticleStatusTab" @change="onTagClick">
          <a-tab-pane :key="tab.key" :tab="tab.label" v-for="tab in tabs">
            <SoList @on-call-edit="onCallEdit" :list="articles">
              <template #title="{item}">
                <span @click="CommonUtil.openNewPage(`/post/${item.id}`)">{{ item.title }}</span>
              </template>
              <template #tag="{item}">
                <span>阅读 {{ item.viewCount }}</span>
                <span>收藏 {{ item.collectCount }}</span>
                <span>点赞 {{ item.likeCount }}</span>
                <span>评论 {{ item.commentCount }}</span>
              </template>
              <template #content="{item}">
                <a-tag v-if="item.status==ARTICLE.StatusEnum.PUBLISHED" :bordered="false" color="success">已发布</a-tag>
                <a-tag v-if="item.status==ARTICLE.StatusEnum.UNDER_REVIEW" :bordered="false" color="processing">审核中</a-tag>
                <a-tag v-if="item.status==ARTICLE.StatusEnum.PASSWORD_PROTECTED" :bordered="false">密码可见</a-tag>
                <a-tag v-if="item.status==ARTICLE.StatusEnum.PRIVATE" :bordered="false" color="geekblue">仅我可见</a-tag>
                <a-tag v-if="item.status==ARTICLE.StatusEnum.FANS_ONLY" :bordered="false" color="cyan">粉丝可见</a-tag>
                <a-tag v-if="item.status==ARTICLE.StatusEnum.REJECTED" :bordered="false" color="error">未通过</a-tag>
              </template>
              <template #action="{item}">
                <div @click="onCallData(item)">数据</div>
                <div @click="onCallComment(item)">评论</div>
                <div @click="onCallEdit(item)">修改</div>
                <a-dropdown>
                  <a class="ant-dropdown-link" @click.prevent>
                    更多
                    <DownOutlined/>
                  </a>
                  <template #overlay>
                    <a-menu>
                      <a-menu-item>
                        <a href="javascript:;">删除</a>
                      </a-menu-item>
                      <a-menu-item>
                        <a href="javascript:;">分享</a>
                      </a-menu-item>
                      <a-menu-item>
                        <a href="javascript:;">置顶</a>
                      </a-menu-item>
                    </a-menu>
                  </template>
                </a-dropdown>
              </template>
            </SoList>
          </a-tab-pane>
        </a-tabs>
      </a-tab-pane>
      <a-tab-pane key="5" tab="草稿箱">
        <SoList :list="articles"/>
      </a-tab-pane>
      <a-tab-pane key="6" tab="回收站">
        <SoList :list="articles"/>
      </a-tab-pane>
      <template #rightExtra>
        <a-button @click="CommonUtil.openNewPage('/editor')" type="primary" size="small">写文章</a-button>
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
