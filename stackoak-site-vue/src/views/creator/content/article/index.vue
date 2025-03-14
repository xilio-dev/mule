<script setup lang="ts">
import {onMounted, ref} from "vue";
import {articleListByUser} from "@/api/post.ts";
/*------------------------------------变量定义------------------------------------------*/
const articles = ref([])

const activeTab = ref('1');
const activeArticleStatusTab = ref('1');
/*------------------------------------生命周期-------------------------------------------*/
onMounted(  () => {
  loadArticle()
})


/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
const loadArticle = async () => {
  await articleListByUser({current: 1, size: 10,status:0}).then(res => {
    articles.value = res.records
  })
}


/*------------------------------------核心业务--------------------------------------------*/




/*-------------------------------------其他函数-------------------------------------------*/

</script>

<template>
  <a-card :bordered="false">
    <a-tabs v-model:activeKey="activeTab">
      <a-tab-pane key="1" tab="文章">
        <a-tabs v-model:activeKey="activeArticleStatusTab">
          <a-tab-pane key="1" tab="全部">

          </a-tab-pane>
          <a-tab-pane key="2" tab="已发布">

          </a-tab-pane>

          <a-tab-pane key="3" tab="仅我可见">

          </a-tab-pane>
          <a-tab-pane key="4" tab="密码可见">

          </a-tab-pane>
          <a-tab-pane key="5" tab="审核中">

          </a-tab-pane>
          <a-tab-pane key="6" tab="未通过">

          </a-tab-pane>
        </a-tabs>
      </a-tab-pane>
      <a-tab-pane key="3" tab="草稿箱">

      </a-tab-pane>
      <a-tab-pane key="3" tab="回收站">

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


</style>
