<script setup lang="ts">
import {useRoute} from "vue-router";
import {ref, watch} from "vue";
import {searchArticle} from "@/api/search.ts";
import ArticleList from "@/components/ArticleList.vue";

const route = useRoute()
const articles = ref([])
// 监听查询参数的变化，发生变化重新获取数据
watch(
    () => route.query.keyword,
    (newKeyword, oldKeyword) => {
      if (newKeyword) {
        searchArticle({keyword: newKeyword, page: 1, size: 20}).then((res) => {
          articles.value = res.records;
        });
      }
    },
    {immediate: true}
);
const activeKey = ref('1');
</script>

<template>
  <a-row style="padding-bottom: 15px;padding-top: 5px " :gutter="12">
    <a-col :span="17">
      <a-flex style="width: 100%">
        <a-input-search size="large" style="width: 100%;margin: 0 8%"/>
      </a-flex>
    </a-col>

  </a-row>
  <a-row :gutter="12">
    <a-col :span="17">
      <a-card style="width: 100%;background-color: white">
        <a-flex :gap="20">
          <a-button>文章</a-button>
          <a-button>专栏</a-button>
          <a-button>标签</a-button>
          <a-button>用户</a-button>
        </a-flex>
        <a-tabs v-model:activeKey="activeKey">
          <a-tab-pane key="1" tab="综合">
            <ArticleList :article-list="articles"/>
          </a-tab-pane>
          <a-tab-pane key="2" tab="最新" force-render>

          </a-tab-pane>
          <a-tab-pane key="3" tab="最热">

          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-col>
    <a-col :span="7">
      <a-flex :gap="12" vertical>
        <a-card style="height: 260px">
          hello
        </a-card>
        <a-affix offset-bottom="bottom"  :offset-top="60">
        <a-card style="height: 260px">
          hello
        </a-card>
        <a-card style="height: 260px;margin-top: 12px">
          hello
        </a-card>
        </a-affix>
      </a-flex>
    </a-col>
  </a-row>


</template>

<style scoped>

</style>
