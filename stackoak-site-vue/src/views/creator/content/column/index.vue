<script setup lang="ts">
import {onMounted, reactive, ref, type UnwrapRef} from "vue";
import {columnListByUser, deleteColumn, saveColumn} from "@/api/column.ts";
import SoList from '@/components/SoList/index.vue'
import {deleteArticle, getColumnArticle} from "@/api/post.ts";
import type {Rule} from "ant-design-vue/es/form";
import {ImageUtils} from "@/utils/file.ts";
import {message, Modal} from "ant-design-vue";

/*------------------------------------变量定义------------------------------------------*/
const activeTab = ref('1');
const activeColumnStatusTab = ref('-1');
const openArticleManageModel = ref(false)
const openColumnFormModel = ref(false)/*打开新建专栏对话框*/
const columns = ref([])
const articles = ref([])
const columnFormRef = ref();
const queryParam = ref({
  current: 1,
  size: 10,
  key: '',
  status: -1
})

const articleQueryParam = ref({
  current: 1,
  size: 10,
})

/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadColumns()
})


/*------------------------------------初始化---------------------------------------------*/
const tabs = [
  {key: '-1', label: '全部'},
  {key: '1', label: '已发布'},
  {key: '0', label: '审核中'},
  {key: '2', label: '未通过'},

];
interface ColumnForm {
  id: string;
  name: string;
  cover: string;
  intro: string | undefined;
}

const columnForm: UnwrapRef<ColumnForm> = reactive({
  id: '',
  name: '',
  cover: '',
  intro: '',
});
const clearColumnForm = () => {
  columnForm.id = '';
  columnForm.name = '';
  columnForm.cover = '';
  columnForm.intro = '';
}
const rules: Record<string, Rule[]> = {
  name: [
    {required: true, message: '名称不能为空', trigger: 'change'},
    {min: 1, max: 10, message: '字数范围1-10个字', trigger: 'change'},
  ],
  intro: [
    {min: 1, max: 100, message: '字数范围1-100个字', trigger: 'change'},
  ],
};

/*------------------------------------数据加载--------------------------------------------*/
const loadColumns = async () => {
  await columnListByUser(queryParam.value).then(res => {
    columns.value = res.records
  })
}
const onChangeTab = (key: any) => {
  queryParam.value.status = key
  loadColumns()
}
/*------------------------------------核心业务--------------------------------------------*/
const onManageArticle = (id: string) => {
  getColumnArticle({pageQuery: articleQueryParam.value, cid: id}).then(res => {
    articles.value = res.records
  })
  openArticleManageModel.value = true
}
const onEditColumn = (item: string) => {
  Object.assign(columnForm, item)
  openColumnFormModel.value = true
}
//保存专栏
const onSaveColumn = () => {
  columnFormRef.value
      .validate()
      .then(() => {
        saveColumn(columnForm).then(res => {
          message.info("保存成功")
          openColumnFormModel.value=false
          clearColumnForm()
          loadColumns()
        })
      })
};
const onNewColumn = () => {
  clearColumnForm()
  openColumnFormModel.value = true
}
const removeColumn=(cid:string)=>{
  Modal.confirm({
    title: '您确定删除该专栏?',
    content: '删除后不可恢复！',
    okText: '确认',
    okType: 'danger',
    cancelText: '取消',
    onOk() {
      deleteColumn(cid).then(res=>{
        //过滤掉已经删除的文章
        columns.value = columns.value.filter(column => column.id !== cid);
        message.success("已删除")
      })
    },
  });
}
/*-------------------------------------其他函数-------------------------------------------*/


</script>

<template>
  <a-card :bordered="false">
    <a-tabs v-model:activeKey="activeTab">
      <a-tab-pane key="1" tab="专栏管理">
        <a-tabs v-model:activeKey="activeColumnStatusTab" @change="onChangeTab">
          <a-tab-pane :key="tab.key" :tab="tab.label" v-for="tab in tabs">
            <SoList :list="columns">
              <template #title="{item}">
                <span>{{ item.name }}</span>
              </template>
              <template #content="{item}">
                <a-tag v-if="item.status==0" :bordered="false" color="processing">审核中</a-tag>
                <a-tag v-if="item.status==1" :bordered="false" color="success">已发布</a-tag>
                <a-tag v-if="item.status==2" :bordered="false" color="error">未通过</a-tag>
              </template>
              <template #tag="{item}">
                <span>文章数 20</span>
                <span>订阅人数 20</span>
              </template>
              <template #action="{item}">
                <div @click="onEditColumn(item)">编辑</div>
                <div @click="removeColumn(item.id)">删除</div>
                <div @click="onManageArticle(item.id)">管理</div>
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
        <a-button @click="onNewColumn" type="primary" size="small">新建专栏</a-button>
      </template>
    </a-tabs>

  </a-card>
  <a-modal :bordered="false" :footer="null" v-model:open="openArticleManageModel" title="管理文章" width="60%">
    <SoList :load-more="true" :list="articles" v-if="articles.length>0">
      <template #loadMore>

      </template>
      <template #title="{item}">
        <span>{{ item.title }}</span>
      </template>
      <template #tag="{item}">
        <span>阅读 {{ item.viewCount }}</span>
        <span>收藏 {{ item.collectCount }}</span>
        <span>点赞 {{ item.likeCount }}</span>
        <span>评论 {{ item.commentCount }}</span>
      </template>
      <template #action="{item}">
        <div>编辑</div>
        <div>删除</div>
      </template>
    </SoList>
    <a-empty v-else description="暂无数据"/>
  </a-modal>

  <a-modal v-model:open="openColumnFormModel" :title="columnForm.id==''?'新建专栏':'编辑专栏'" ok-text="保存" cancel-text="取消" @ok="onSaveColumn">
    <a-form
        ref="columnFormRef"
        :model="columnForm"
        :rules="rules">
      <a-form-item ref="name" label="名称" name="name">
        <a-input v-model:value="columnForm.name"/>
      </a-form-item>
      <a-form-item label="封面" name="cover">
        <a-upload
            v-model:file-list="fileList"
            name="avatar"
            list-type="picture-card"
            :show-upload-list="false"
            action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
            :before-upload="beforeUpload"
            @change="handleChange"
        >
          <a-image style="width: 120px;height: 68px" :preview="false" v-if="columnForm.cover" :src="ImageUtils.getImgUrl(columnForm.cover)" alt="avatar"/>
        </a-upload>
      </a-form-item>
      <a-form-item label="描述" name="intro">
        <a-textarea v-model:value="columnForm.intro"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>
/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}


</style>
