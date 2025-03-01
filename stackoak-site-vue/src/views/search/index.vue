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
        searchArticle({ keyword: newKeyword, page: 1, size: 20 }).then((res) => {
          articles.value = res.records;
        });
      }
    },
    { immediate: true }
);
</script>

<template>
  <ArticleList :article-list="articles"/>
</template>

<style scoped>

</style>
