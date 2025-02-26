<script setup lang="ts">
import Markdown from "@/components/Markdown.vue";
import RightDrawer from "@/views/editor/components/right-drawer/index.vue"
import {computed, onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import {addArticle, postDetail, updateArticle} from "@/api/post.ts";
import {useUserStore} from "@/stores/user.ts";
import {categoryList} from "@/api/category.ts";
import {columnList} from "@/api/column.ts";
import {reactive} from 'vue';
import type {Rule} from 'ant-design-vue/es/form';
import {PlusOutlined} from '@ant-design/icons-vue';
import {nextTick} from 'vue';
import {ImageUtils} from "@/utils/file.ts";
import {validateFieldAndLength} from "@/utils/validate/article-validate.ts";
import router from "@/router";

const useUser = useUserStore()
const route = useRoute();
const isLoading = ref(true);
const isAuthorized = ref(false);
//可见范围
const visibleStatusList = ref([
  {id: 1, label: '全部'},
  {id: 2, label: '仅自己'},
  {id: 3, label: '粉丝'},
  {id: 4, label: '密码访问'}
])
//创作类型
const creativeTypeList = ref([
  {id: 1, label: '原创'},
  {id: 2, label: '转载'}])
// 判断是否是新增模式
const isAdd = computed(() => route.query.id == undefined || route.query.id == '');
const articleDetailVo = reactive({
  articleInfo: {
    title: undefined,
    content: undefined
  },
  tags: [],
  category: {
    categoryId: undefined
  }
})
//获取文章详情数据
const loadArticleDetail = async () => {
  try {
    const data = await postDetail({id: route.query.id});
    if (data) {
      Object.assign(articleDetailVo, {
        articleInfo: data.articleInfo || {},
        tags: data.tags || [],
        category: data.category || {}
      });

      isAuthorized.value = articleDetailVo.articleInfo.creativeType === 2 || false
      tagState.tags = articleDetailVo.tags.map(tag => tag.name) || [];
      isLoading.value = false
    }
  } catch (err) {
    isLoading.value = false
  }
};
//获取分类领域
const categorys = ref([]);
const loadCategorys = async () => {
  try {
    // 获取数据
    const data = await categoryList();
    // 过滤掉 item.id === 0 的记录
    categorys.value = (data || []).filter(item => item.id !== '0');
  } catch (err) {
  }
};
//获取用户的专栏列表
const columns = ref([]);
const loadColumnList = async () => {
  columns.value = await columnList() || []
}
const initForm = () => {
  articleDetailVo.articleInfo.visibleStatus = 1
  articleDetailVo.articleInfo.creativeType = 1
}
onMounted(async () => {
  if (!isAdd.value) {
    await loadArticleDetail()
  } else {
    initForm()
  }
  isLoading.value = false
  await loadCategorys()
  await loadColumnList()
})

const rules: Record<string, Rule[]> = {
  categoryId: [{required: true, message: '选择分类', trigger: 'change'}],
  visibleStatus: [{required: true, message: '可见状态', trigger: 'change'}],
  creativeType: [{required: true, message: '文章类型', trigger: 'change'}],
};

const inputRef = ref();
const tagState = reactive({
  tags: [],
  inputVisible: false,
  inputValue: '',
});

const handleClose = (removedTag: string) => {
  const tags = tagState.tags.filter(tag => tag !== removedTag);
  tagState.tags = tags;
};

const showInput = () => {
  if (tagState.tags.length > 4) {
    return
  }
  tagState.inputVisible = true;
  nextTick(() => {
    inputRef.value.focus();
  });
};

const handleInputConfirm = () => {
  const inputValue = tagState.inputValue;
  let tags = tagState.tags;
  if (inputValue && tags.indexOf(inputValue) === -1) {
    tags = [...tags, inputValue];
  }
  Object.assign(tagState, {
    tags,
    inputVisible: false,
    inputValue: '',
  });
};

//图片选择抽屉
const open = ref<boolean>(false);
const showDrawer = () => {
  open.value = true;
};
//发布对话框
const openPublish = ref<boolean>(false);
const showModal = () => {
  if (!validateFieldAndLength(articleDetailVo.articleInfo.title, 5, '文章标题')) return;
  if (!validateFieldAndLength(articleDetailVo.articleInfo.content, 20, '文章内容')) return;
  openPublish.value = true;
};
//发布文章
const onPublishArticle = () => {
  let body = {
    ...articleDetailVo.articleInfo,
    tagNames: tagState.tags,
    columnNames: tagState.tags,
    categoryId: articleDetailVo.category.categoryId
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
};

//分类领域选择
const selectDomainItem = (id: any) => {
  // 更新选中的按钮ID
  articleDetailVo.category.categoryId = id
}

const onMarkdownChange = (e: any) => {
  articleDetailVo.articleInfo.content = e.content
}
const onConfirmSelectImg = (imgUrl: string) => {
  articleDetailVo.articleInfo.cover = ImageUtils.removeSuffixPath(imgUrl)
}
</script>

<template>
  <div style="position: fixed;width: 100%">

    <a-row style="width: 100%;background-color: white;">
      <a-col :span="18">
        <a-input v-model:value="articleDetailVo.articleInfo.title" :bordered="false" show-count :maxlength="100"
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
              :value="articleDetailVo.articleInfo.content"/>
  </div>
  <a-modal style="top: 20px" width="45%" ok-text="立即发布" cancel-text="取消" v-model:open="openPublish"
           title="文章发布" @ok="onPublishArticle">
    <a-form ref="formRef" :model="articleDetailVo" :rules="rules">
      <a-form-item label="分类领域" name="categoryId">
        <div class="domain-container">
          <a-button :type="articleDetailVo.category.categoryId == item.id ? 'primary' : 'dashed'"
                    v-for="item in categorys"
                    @click="selectDomainItem(item.id)" size="small" class="domain-item">{{ item.name }}
          </a-button>
        </div>
      </a-form-item>
      <a-form-item label="文章封面" name="cover">
        <div class="upload-container" @click="showDrawer">
          <!-- 图片显示区域 -->
          <img
              v-if="articleDetailVo.articleInfo.cover"
              :src="ImageUtils.getImgUrl(articleDetailVo.articleInfo.cover)"
              style="width: 150px; height: 100px; object-fit: cover;"
              alt="Uploaded Image"
          />
          <!-- 上传按钮和加号图标 -->
          <div v-else class="upload-button">
            <PlusOutlined/>
          </div>
        </div>
      </a-form-item>

      <a-form-item label="保存合集" name="columnId">
        <template v-for="(tag, index) in  tagState.tags" :key="tag">
          <a-tooltip v-if="tag.length > 5" :title="tag">
            <a-tag :closable="true" @close="handleClose(tag)">
              {{ `${tag.slice(0, 5)}...` }}
            </a-tag>
          </a-tooltip>
          <a-tag v-else :closable="true" @close="handleClose(tag)">
            {{ tag }}
          </a-tag>
        </template>
        <a-input
            v-if=" tagState.inputVisible"
            ref="inputRef"
            v-model:value=" tagState.inputValue"
            type="text"
            size="small"
            :style="{ width: '78px' }"
            @blur="handleInputConfirm"
            @keyup.enter="handleInputConfirm"
        />
        <a-tag v-else-if=" tagState.tags.length<=4" style="background: #fff; border-style: dashed" @click="showInput">
          <plus-outlined/>
          <a-popover>
            <template #content>
              开发中...
            </template>
            添加合集
          </a-popover>
        </a-tag>
      </a-form-item>
      <a-form-item label="文章标签" name="tag">
        <template v-for="(tag, index) in  tagState.tags" :key="tag">
          <a-tooltip v-if="tag.length > 5" :title="tag">
            <a-tag :closable="true" @close="handleClose(tag)">
              {{ `${tag.slice(0, 5)}...` }}
            </a-tag>
          </a-tooltip>
          <a-tag v-else :closable="true" @close="handleClose(tag)">
            {{ tag }}
          </a-tag>
        </template>
        <a-input
            v-if=" tagState.inputVisible"
            ref="inputRef"
            v-model:value=" tagState.inputValue"
            type="text"
            size="small"
            :style="{ width: '78px' }"
            @blur="handleInputConfirm"
            @keyup.enter="handleInputConfirm"
        />
        <a-tag v-else-if=" tagState.tags.length<=4" style="background: #fff; border-style: dashed" @click="showInput">
          <plus-outlined/>
          <a-popover placement="bottom">
            <template #content>
              开发中...
            </template>
            添加标签
          </a-popover>
        </a-tag>
      </a-form-item>
      <a-form-item label="文章类型" name="creativeType">
        <a-radio-group v-model:value="articleDetailVo.articleInfo.creativeType">
          <a-radio v-for="item in creativeTypeList" :value="item.id">{{ item.label }}</a-radio>
        </a-radio-group>
        <a-row v-if="articleDetailVo.articleInfo.creativeType===2">
          <a-input v-model:value="articleDetailVo.articleInfo.originalUrl" placeholder="请输入原文作者链接"
                   style="margin-top: 10px"/>
          <a-checkbox style="margin-top: 10px" v-model:checked="isAuthorized">已获得原文作者授权许可</a-checkbox>
        </a-row>
      </a-form-item>
      <a-form-item label="可见范围" name="visibleStatus">
        <a-radio-group v-model:value="articleDetailVo.articleInfo.visibleStatus">
          <a-radio v-for="item in visibleStatusList" :value="item.id">{{ item.label }}</a-radio>
        </a-radio-group>
        <a-input v-if="articleDetailVo.articleInfo.visibleStatus===4"
                 v-model:value="articleDetailVo.articleInfo.visitPassword"
                 placeholder="请输入文章访问密码" style="margin-top: 15px"/>
      </a-form-item>
      <a-form-item label="文章摘要" name="description">
        <a-textarea placeholder="文章摘要：用于展示在网站各种推荐列表，以便读者快速了解文章内容，建议填写，增加曝光度。"
                    :auto-size="{ minRows: 2, maxRows: 5 }" v-model:value="articleDetailVo.articleInfo.description"/>
      </a-form-item>
    </a-form>

  </a-modal>
  <RightDrawer @confirm-select="onConfirmSelectImg" :open-drawer="open" @close-drawer="open=false"/>

</template>
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
