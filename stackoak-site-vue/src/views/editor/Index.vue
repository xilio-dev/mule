<template>
  <div style="position: fixed;width: 100%">
    <a-row style="width: 100%;background-color: white;">
      <a-col :span="18">
        <a-input v-model:value="articleDetailForm.title" :bordered="false" show-count :maxlength="100"
                 class="custom-placeholder"
                 placeholder="请输入一个简短的文章标题"
                 style="font-size:20px;min-height: 50px;"/>
      </a-col>
      <a-col :span="6" style="padding-left: 20px">
        <div class="operation-button">
          <a-button type="default">存草稿</a-button>
          <a-button type="primary" @click="showModal">发布</a-button>
          <div>
            <a-avatar :src="useUser.userinfo.avatar"/>
          </div>
        </div>
      </a-col>
    </a-row>
    <Markdown @markdown-change="onMarkdownChange" v-if="!isLoading" :height="95" md-id="9999" :preview="true"
              :value="articleDetailForm.content"/>
  </div>
  <a-modal style="top: 20px" width="45%" ok-text="立即发布" cancel-text="取消" v-model:open="openPublish"
           title="文章发布" @ok="onPublishArticle">
    <a-form ref="formRef" :model="articleDetailForm" :rules="rules">
      <a-form-item label="分类领域" name="categoryId">
        <div class="domain-container">
          <a-button :type="articleDetailForm.categoryId == item.id ? 'primary' : 'dashed'"
                    v-for="item in categories"
                    @click="selectDomainItem(item.id)" size="small" class="domain-item">{{ item.name }}
          </a-button>
        </div>
      </a-form-item>
      <a-form-item label="文章封面" name="cover">
        <div class="upload-container" @click="showDrawer">
          <!-- 图片显示区域 -->
          <img
              v-if="articleDetailForm.cover"
              :src="ImageUtils.getImgUrl(articleDetailForm.cover)"
              style="width: 150px; height: 100px; object-fit: cover;"
              alt="Uploaded Image"/>
          <!-- 上传按钮和加号图标 -->
          <div v-else class="upload-button">
            <PlusOutlined/>
          </div>
        </div>
      </a-form-item>

      <a-form-item label="保存合集" name="columnId">
        <TagInput name="columns" label="合集" v-model:model-value="selectColumns" max-tags="3" max-length="10"
                  :selectItems="columns"/>
      </a-form-item>
      <a-form-item id="2" label="文章标签" name="tag">
        <TagInput id="1" name="tags" label="标签" v-model:model-value="selectTags" max-tags="4" max-length="10"
                  :selectItems="tags"/>
      </a-form-item>

      <a-form-item label="文章类型" name="creativeType">
        <a-radio-group v-model:value="articleDetailForm.creativeType">
          <a-radio v-for="item in ARTICLE.CREATIVE_TYPE_LIST" :value="item.id">{{ item.label }}</a-radio>
        </a-radio-group>
        <a-row v-if="articleDetailForm.creativeType==2">
          <a-input v-model:value="articleDetailForm.originalUrl" placeholder="请输入原文作者链接"
                   style="margin-top: 10px"/>
          <a-checkbox style="margin-top: 10px" v-model:checked="authorizedStatus">
            已获得原文作者授权许可
          </a-checkbox>
        </a-row>
      </a-form-item>

      <a-form-item label="可见范围" name="visibleStatus">
        <a-radio-group v-model:value="articleDetailForm.visibleStatus">
          <a-radio v-for="item in ARTICLE.VISIBLE_STATUS_LIST" :value="item.id">{{ item.label }}</a-radio>
        </a-radio-group>
        <a-input v-if="articleDetailForm.visibleStatus===4"
                 v-model:value="articleDetailForm.visitPassword"
                 placeholder="请输入文章访问密码" style="margin-top: 15px"/>
      </a-form-item>
      <a-form-item label="文章摘要" name="description">
        <a-textarea
            placeholder="文章摘要：用于展示在网站各种推荐列表，以便读者快速了解文章内容，建议填写，增加曝光度。如果不填，系统将自动生成！"
            :auto-size="{ minRows: 2, maxRows: 5 }" v-model:value="articleDetailForm.description"/>
      </a-form-item>
    </a-form>

  </a-modal>
  <RightDrawer @confirm-select="onConfirmSelectImg" :open-drawer="open" @close-drawer="open=false"/>
</template>
<script setup lang="ts">
import Markdown from "@/components/Markdown.vue";
import RightDrawer from "@/views/editor/components/right-drawer/index.vue"
import {computed, onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import {addArticle, postDetail, updateArticle} from "@/api/post.ts";
import {useUserStore} from "@/stores/user.ts";
import {categoryList} from "@/api/category.ts";
import {columnList} from "@/api/column.ts";
import type {Rule} from 'ant-design-vue/es/form';
import {PlusOutlined} from '@ant-design/icons-vue';
import {ImageUtils} from "@/utils/file.ts";
import router from "@/router";
import {tagList} from "@/api/tag.ts";
import TagInput from '@/components/TagInput/index.vue'
import {ARTICLE} from "@/constants/article.ts";
import {message} from "ant-design-vue";
import {validateFieldAndLength} from "@/utils/validate/article-validate.ts";

/*------------------------------------变量定义--------------------------------------------*/
const useUser = useUserStore()/*用户状态*/
const route = useRoute();/*路由状态*/
const isLoading = ref(true);/*加载中开关*/
const selectColumns = ref()/*用户选择的分类专栏*/
const selectTags = ref([])/*用户选择的标签列表*/
const categories = ref([]);/*分类领域*/
const columns = ref();/*用户分类专栏列表*/
const tags = ref();/*推荐标签列表*/
const authorizedStatus = ref(false)/*转载文章是否被授权*/
const open = ref<boolean>(false);/*图片选择抽屉开关*/
const openPublish = ref<boolean>(false);/*发布文章对话框开关*/
const formRef = ref()

/*-------------------------------------------生命周期---------------------------------------------*/
onMounted(async () => {
  if (!isAdd.value) {
    await loadArticleDetail()
  }
  isLoading.value = false
  await loadCategories()
  await loadColumnList()
  await loadTagList()
})
/*-----------------------------------------初始化-----------------------------------------------*/
const articleDetailForm = ref({
  title: '',
  content: '',
  categoryId: '',
  authorizeStatus: 0,
  visitPassword: '',
  creativeType: ARTICLE.CreativeTypeEnum.ORIGINAL,
  visibleStatus: ARTICLE.VisibilityStatusEnum.ALL
})
// 判断是否是新增模式
const isAdd = computed(() => !route.query || route.query.id == undefined || route.query.id == '');
const rules: Record<string, Rule[]> = {
  categoryId: [{required: true, message: '请选择分类领域', trigger: 'change'}],
  visibleStatus: [{required: true, message: '请选择可见范围', trigger: 'change'}],
  creativeType: [{required: true, message: '文章类型', trigger: 'change'}],
};
/*----------------------------------------数据加载------------------------------------------------*/
//获取文章详情数据
const loadArticleDetail = async () => {
  try {
    const data = await postDetail({id: route.query.id});
    if (data) {
      articleDetailForm.value = {...data.articleInfo, ...data.category}
      authorizedStatus.value = articleDetailForm.value.authorizeStatus == 1
      //初始化标签
      if (data.tags) {
        selectTags.value = data.tags.map(tag => tag.name) || [];
      }
      if (data.columns) {
        selectColumns.value = data.columns.map(c => c.name) || [];
      }
      isLoading.value = false
    }
  } catch (err) {
    isLoading.value = false
  }
};
//加载分类领域
const loadCategories = async () => {
  try {
    const data = await categoryList();
    // 过滤掉 item.id === 0 的记录
    categories.value = (data || []).filter(item => item.id !== '0');
  } catch (err) {
  }
};
//加载用户的专栏列表
const loadColumnList = async () => {
  const res = await columnList({current: 1, size: 10})
  columns.value = res.records || []
}
//加载系统中所有标签
const loadTagList = async () => {
  const res = await tagList({current: 1, size: 10})
  tags.value = res.records || []
}
/*------------------------------------------核心业务----------------------------------------------*/
//发布文章
const onPublishArticle = () => {
  formRef.value
      .validate()
      .then(() => {
        if (articleDetailForm.value.creativeType == ARTICLE.CreativeTypeEnum.REPRINT && !authorizedStatus.value) {
          message.warning("转载类型文章需得到作者的授权,请确认已获得授权!")
          return
        }
        if (articleDetailForm.value.visibleStatus == ARTICLE.VisibilityStatusEnum.PASSWORD && articleDetailForm.value.visitPassword == '') {
          message.warning("请输入访问密码！")
          return
        }
        let body = {
          ...articleDetailForm.value,
          authorizedStatus: authorizedStatus.value,
          tagNames: selectTags.value || [],
          columnNames: selectColumns.value || [],
        }
        //发布新文章
        if (isAdd.value) {
          addArticle(body).then(articleId => {
            router.push({path: `/post/${articleId}`})
          })
        } else {
          //更新文章
          updateArticle(body).then(articleId => {
            router.push({path: `/post/${articleId}`})
          })
        }
        openPublish.value = false;
      })
};
/*-------------------------------------------其他函数---------------------------------------------*/
//发布对话框
const showModal = () => {
  if (!validateFieldAndLength(articleDetailForm.value.title, 5, '文章标题')) return;
  if (!validateFieldAndLength(articleDetailForm.value.content, 20, '文章内容')) return;
  openPublish.value = true;
};
// 更新选中的按钮ID
const selectDomainItem = (id: any) => {
  articleDetailForm.value.categoryId = id
}
const onMarkdownChange = (e: any) => {
  articleDetailForm.value.content = e.content
}
const onConfirmSelectImg = (imgUrl: string) => {
  articleDetailForm.value.cover = ImageUtils.removeSuffixPath(imgUrl)
}
const showDrawer = () => {
  open.value = true;
};
</script>
<style scoped>
.operation-button {
  display: flex; /* 使用 Flexbox 布局 */
  justify-content: flex-start; /* 按钮水平分布 */
  align-items: center; /* 垂直居中 */
  height: 100%; /* 确保按钮容器高度与输入框一致 */
  gap: 15px
}

.custom-placeholder {
  padding-left: 40px; /* 设置左侧间隔为 15px */
}

.custom-placeholder::-webkit-input-placeholder {
  font-size: 20px;
  color: #999;
}

.custom-placeholder:-ms-input-placeholder {
  font-size: 20px;
  color: #999;
}

.custom-placeholder::-ms-input-placeholder {
  font-size: 20px;
  color: #999;
}

.custom-placeholder::placeholder {
  font-size: 20px;
  color: #999;
}

.avatar-uploader > .ant-upload {
  width: 150px;
  height: 120px;
}

.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}

.upload-container {
  position: relative;
  width: 150px;
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px dashed #ccc; /* 添加边框，方便定位 */
  cursor: pointer;
}

.upload-button {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #1890ff;
  font-size: 16px;
}


/*分类领域标签*/
.domain-container {
  display: grid;
  grid-template-columns: repeat(6, 1fr); /* 6列，每列等宽 */
  grid-template-rows: auto auto; /* 2行，高度自适应 */
  gap: 8px; /* 单元格之间的间距 */
  margin: 0 auto; /* 水平居中 */
}

.domain-item {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-right: 10px;
  border-radius: 0;
  width: 80px;
}

.domain-item:nth-child(5n) {
  margin-right: 0;
}


</style>
