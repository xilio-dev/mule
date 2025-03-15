<script setup lang="ts">
import {onMounted, ref} from "vue";
import {columnListByUser} from "@/api/column.ts";
import SoList from '@/components/SoList/index.vue'

/*------------------------------------变量定义------------------------------------------*/
const activeTab = ref('1');
const activeColumnStatusTab = ref('-1');
const openArticleManageModel=ref(false)
const columns = ref([])
const queryParam = ref({
  current: 1,
  size: 5,
  key: '',
  status: 1
})

/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadColumns()
})


/*------------------------------------初始化---------------------------------------------*/
const tabs = [
  {key: '-1', label: '全部'},
  {key: '2', label: '已发布'},
  {key: '3', label: '审核中'},
  {key: '4', label: '未通过'},

];


/*------------------------------------数据加载--------------------------------------------*/
const loadColumns = async () => {
  await columnListByUser(queryParam.value).then(res => {
    columns.value = res.records
  })
}


/*------------------------------------核心业务--------------------------------------------*/




/*-------------------------------------其他函数-------------------------------------------*/
</script>

<template>
  <a-card :bordered="false">
    <a-tabs v-model:activeKey="activeTab">
      <a-tab-pane key="1" tab="专栏管理">
        <a-tabs v-model:activeKey="activeColumnStatusTab">
          <a-tab-pane :key="tab.key" :tab="tab.label" v-for="tab in tabs">
            <SoList :list="columns">
              <template #title="{item}">
                <span>{{ item.name }}</span>
              </template>
              <template #tag="{item}">
                <span>文章数 20</span>
                <span>订阅人数 20</span>
              </template>
              <template #action="{item}">
                <div>编辑</div>
                <div>删除</div>
                <div @click="openArticleManageModel=true">管理</div>
              </template>
            </SoList>
          </a-tab-pane>
        </a-tabs>
      </a-tab-pane>
      <a-tab-pane key="2" tab="专栏订阅">
        <a-tabs v-model:activeKey="activeColumnStatusTab">
          <a-tab-pane key="1" tab="订阅我的">

          </a-tab-pane>
          <a-tab-pane key="2" tab="我的订阅">
            <SoList :list="columns" :load-more="false">
              <template #title="{item}">
                <span>{{ item.name }}</span>
              </template>
              <template #content="{item}">
                <span>{{ item.intro }}</span>
              </template>
              <template #tag="{item}">
                <span>订阅时间 2023-03-15</span>
                <span>文章数 20</span>
                <span>订阅人数 20</span>
              </template>
              <template #right="{item}">
                <a-button type="primary" size="small">已订阅</a-button>
              </template>
            </SoList>
          </a-tab-pane>
        </a-tabs>
      </a-tab-pane>
      <template #rightExtra>
        <a-button type="primary" size="small">新建专栏</a-button>
      </template>
    </a-tabs>

  </a-card>
  <a-modal :bordered="false" :footer="null" v-model:open="openArticleManageModel" title="管理文章" width="60%" >
    <SoList :load-more="true" :list="columns">
      <template #loadMore>

      </template>
      <template #title="{item}">
        <span>{{ item.name }}</span>
      </template>
      <template #tag="{item}">
        <span>文章数 20</span>
        <span>订阅人数 20</span>
      </template>
      <template #action="{item}">
        <div>编辑</div>
        <div>删除</div>
      </template>
    </SoList>
  </a-modal>
</template>

<style scoped>
/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}


</style>
