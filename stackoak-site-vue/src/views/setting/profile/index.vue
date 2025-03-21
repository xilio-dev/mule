<script setup lang="ts">
import { onMounted, reactive, ref, toRaw } from 'vue';
import type { UnwrapRef } from 'vue';
import type { Rule } from 'ant-design-vue/es/form';
import dayjs, { Dayjs } from 'dayjs';
import { getUserProfile, updateProfile } from '@/api/user.ts';
import { tagList } from '@/api/tag.ts';
import { message } from 'ant-design-vue';
import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue';
import type { UploadChangeParam, UploadProps } from 'ant-design-vue';

// 类型定义
interface Tag {
  id: number;
  name: string;
  checked: boolean;
}

type RangeValue = [Dayjs, Dayjs];

interface UserForm {
  id?: string;
  avatar: string;
  nickname: string;
  gender: number;
  introduce: string;
  personBlogAddress: string;
  github: string;
  gitee: string;
  csdn: string;
  bokeyuan: string;
  bilibli: string;
  authorQr: string;
  eduLevel: string;
  universityName: string;
  majorName: string;
  eduStartTime?: string;
  eduEndTime?: string;
  eduTime?: RangeValue | null;
  careerField: string;
  company: string;
  jobTitle: string;
  jobTime?: Dayjs | null;
  tagIds?: number[];
}

// 响应式表单数据
const userForm: UnwrapRef<UserForm> = reactive({
  id: undefined,
  avatar: '',
  nickname: '',
  gender: 2,
  introduce: '',
  personBlogAddress: '',
  github: '',
  gitee: '',
  csdn: '',
  bokeyuan: '',
  bilibli: '',
  authorQr: '',
  eduLevel: '',
  universityName: '',
  majorName: '',
  eduTime: null,
  careerField: '',
  company: '',
  jobTitle: '',
  jobTime: null,
  tagIds: [],
});

// 表单相关
const formRef = ref();
const labelCol = { span: 4 };
const wrapperCol = { span: 20 };
const rules: Record<string, Rule[]> = {
  nickname: [
    { required: true, message: '请填写您的账号昵称', trigger: 'change' },
    { min: 2, max: 15, message: '字数限制 2-15', trigger: 'change' },
  ],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
};

// 日期格式
const dateFormat = 'YYYY/MM/DD';

// 加载用户数据
const loadUserProfile = () => {
  getUserProfile()
      .then((res) => {
        const data = res || {};
        Object.assign(userForm, data);

        // 处理工作时间
        userForm.jobTime = data.jobTime ? dayjs(data.jobTime, dateFormat) : null;

        // 处理教育时间
        if (data.eduStartTime && data.eduEndTime) {
          userForm.eduTime = [
            dayjs(data.eduStartTime, dateFormat),
            dayjs(data.eduEndTime, dateFormat),
          ];
        } else {
          userForm.eduTime = null;
        }

        // 初始化标签
        if (tags.value && data.tagIds) {
          initSelectedTags();
        }
      })
      .catch((err) => {
        console.error('加载用户数据失败:', err);
        message.error('加载用户信息失败');
      });
};

// 标签相关
const tags = ref<Tag[]>([]);
const selectedTags = ref<Tag[]>([]);

const loadTagList = () => {
  tagList({ current: 1, size: 100 })
      .then((res) => {
        tags.value = (res.records || []).map((tag: any) => ({
          id: tag.id,
          name: tag.name,
          checked: false,
        }));
        if (userForm.tagIds) {
          initSelectedTags();
        }
      })
      .catch((err) => {
        console.error('加载标签失败:', err);
        message.error('加载标签失败');
      });
};

const initSelectedTags = () => {
  if (!tags.value || !userForm.tagIds) return;
  userForm.tagIds.forEach((tagId) => {
    const tag = tags.value.find((t) => t.id === tagId);
    if (tag) {
      tag.checked = true;
      selectedTags.value.push({ ...tag });
    }
  });
};

const handleChanges = (tag: Tag) => {
  if (tag.checked) {
    selectedTags.value.push({ ...tag });
  } else {
    const index = selectedTags.value.findIndex((t) => t.id === tag.id);
    if (index !== -1) selectedTags.value.splice(index, 1);
  }
};

const removeTag = (tagId: number) => {
  const index = selectedTags.value.findIndex((t) => t.id === tagId);
  if (index !== -1) {
    selectedTags.value.splice(index, 1);
    const tag = tags.value.find((t) => t.id === tagId);
    if (tag) tag.checked = false;
  }
};

// 提交表单
const onSubmit = () => {
  formRef.value
      .validate()
      .then(() => {
        const formData = { ...toRaw(userForm) };
        // 处理日期字段
        formData.jobTime = formData.jobTime ? formData.jobTime.format(dateFormat) : '';
        if (formData.eduTime && formData.eduTime.length === 2) {
          formData.eduStartTime = formData.eduTime[0].format(dateFormat);
          formData.eduEndTime = formData.eduTime[1].format(dateFormat);
        } else {
          formData.eduStartTime = '';
          formData.eduEndTime = '';
        }
        delete formData.eduTime; // 移除临时字段

        // 处理标签
        formData.tagIds = selectedTags.value.map((tag) => tag.id);

        updateProfile(formData)
            .then(() => {
              message.success('更新成功');
            })
            .catch((err) => {
              console.error('更新失败:', err);
              message.error('更新失败');
            });
      })
      .catch((err) => {
        console.error('表单验证失败:', err);
        message.error('请检查表单内容');
      });
};

// 选项数据
const eduLevelOptions = [
  { value: '1', label: '大专' },
  { value: '2', label: '本科' },
  { value: '3', label: '硕士' },
  { value: '4', label: '博士' },
];
const careerFieldOptions = [
  { value: '1', label: '后端' },
  { value: '2', label: '前端' },
  { value: '3', label: '人工智能' },
  { value: '4', label: '大数据' },
  { value: '5', label: '网络安全' },
  { value: '6', label: '测试' },
];

// 头像上传
const fileList = ref([]);
const loading = ref<boolean>(false);

const handleChange = (info: UploadChangeParam) => {
  if (info.file.status === 'uploading') {
    loading.value = true;
    return;
  }
  if (info.file.status === 'done') {
    getBase64(info.file.originFileObj as Blob, (base64Url: string) => {
      userForm.avatar = base64Url;
      loading.value = false;
    });
  }
  if (info.file.status === 'error') {
    loading.value = false;
    message.error('上传失败');
  }
};

const beforeUpload = (file: UploadProps['fileList'][number]) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  if (!isJpgOrPng) {
    message.error('只能上传 JPG 或 PNG 文件！');
    return false;
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error('图片大小不能超过 2MB！');
    return false;
  }
  return true;
};

function getBase64(img: Blob, callback: (base64Url: string) => void) {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result as string));
  reader.readAsDataURL(img);
}

// 初始化
onMounted(() => {
  loadTagList();
  loadUserProfile();
});
</script>

<template>
  <a-row style="width: 100%;">
    <a-card title="基本信息" class="base-info-card">
      <a-form :label-col="labelCol" :wrapper-col="wrapperCol" ref="formRef" :model="userForm" :rules="rules">
        <a-form-item label="头像">
          <a-upload
              v-model:file-list="fileList"
              name="avatar"
              list-type="picture-card"
              class="avatar-uploader"
              :show-upload-list="false"
              action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
              :before-upload="beforeUpload"
              @change="handleChange"
          >
            <a-avatar v-if="userForm.avatar" :size="{ xs: 24, sm: 32, md: 40, lg: 64, xl: 100, xxl: 120 }">
              <template #icon>
                <a-image :preview="false" :src="userForm.avatar" alt="avatar" />
              </template>
            </a-avatar>
            <div v-else>
              <loading-outlined v-if="loading" />
              <plus-outlined v-else />
              <div class="ant-upload-text">上传</div>
            </div>
          </a-upload>
        </a-form-item>
        <a-form-item label="显示昵称" name="nickname">
          <a-input class="app-input" v-model:value="userForm.nickname" />
        </a-form-item>
        <a-form-item label="性别" name="gender">
          <a-radio-group v-model:value="userForm.gender">
            <a-radio :value="1">男</a-radio>
            <a-radio :value="0">女</a-radio>
            <a-radio :value="2">保密</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="个人简介" name="introduce">
          <a-textarea
              placeholder="个人介绍，最多100字"
              :maxlength="100"
              class="app-input"
              v-model:value="userForm.introduce"
          />
        </a-form-item>
        <a-form-item label="个人博客" name="personBlogAddress">
          <a-input class="app-input" v-model:value="userForm.personBlogAddress" />
        </a-form-item>
        <a-form-item label="Github" name="github">
          <a-input class="app-input" v-model:value="userForm.github" />
        </a-form-item>
        <a-form-item label="Gitee" name="gitee">
          <a-input class="app-input" v-model:value="userForm.gitee" />
        </a-form-item>
        <a-form-item label="CSDN" name="csdn">
          <a-input class="app-input" v-model:value="userForm.csdn" />
        </a-form-item>
        <a-form-item label="博客园" name="bokeyuan">
          <a-input class="app-input" v-model:value="userForm.bokeyuan" />
        </a-form-item>
        <a-form-item label="哔哩哔哩" name="bilibli">
          <a-input class="app-input" v-model:value="userForm.bilibli" />
        </a-form-item>
        <a-form-item label="兴趣标签" name="tag">
          <div class="selected-tags" style="margin-top: 5px">
            <a-tag
                v-for="tag in selectedTags"
                :key="tag.id"
                closable
                @close="() => removeTag(tag.id)"
                class="tag-item"
                :class="{ 'last-tag': tag === selectedTags[selectedTags.length - 1] }"
            >
              {{ tag.name }}
            </a-tag>
          </div>
          <div class="tag-list dashed-border" style="margin-top: 15px; padding: 15px">
            <a-checkable-tag
                v-for="tag in tags"
                :key="tag.id"
                v-model:checked="tag.checked"
                @change="() => handleChanges(tag)"
                class="tag-item"
                :class="{ 'last-tag': tag === tags[tags.length - 1] }"
            >
              {{ tag.name }}
            </a-checkable-tag>
          </div>
        </a-form-item>
        <a-form-item label="作者名片" name="authorQr">
          <a-qrcode
              error-level="H"
              :value="userForm.authorQr || '暂无'"
              icon="https://www.antdv.com/assets/logo.1ef800a8.svg"
          />
        </a-form-item>
      </a-form>
    </a-card>
    <a-card title="教育信息" class="base-info-card">
      <a-form :label-col="labelCol" :wrapper-col="wrapperCol" :model="userForm">
        <a-form-item label="学校" name="universityName">
          <a-input class="app-input" v-model:value="userForm.universityName" />
        </a-form-item>
        <a-form-item label="专业" name="majorName">
          <a-input class="app-input" v-model:value="userForm.majorName" />
        </a-form-item>
        <a-form-item label="学历" name="eduLevel">
          <a-select
              v-model:value="userForm.eduLevel"
              :options="eduLevelOptions"
              size="small"
              class="app-input"
              placeholder="选择学历"
              style="width: 100%"
          />
        </a-form-item>
        <a-form-item label="入学时间" name="eduTime">
          <a-range-picker
              v-model:value="userForm.eduTime"
              :format="dateFormat"
              class="app-input"
              placeholder="选择入学时间"
              style="width: 100%"
          />
        </a-form-item>
      </a-form>
    </a-card>
    <a-card title="工作信息" class="base-info-card">
      <a-form :label-col="labelCol" :wrapper-col="wrapperCol" :model="userForm">
        <a-form-item label="职业方向" name="careerField">
          <a-select
              v-model:value="userForm.careerField"
              :options="careerFieldOptions"
              size="small"
              class="app-input"
              placeholder="职业领域"
              style="width: 100%"
          />
        </a-form-item>
        <a-form-item label="任职公司" name="company">
          <a-input class="app-input" v-model:value="userForm.company" />
        </a-form-item>
        <a-form-item label="岗位" name="jobTitle">
          <a-input class="app-input" v-model:value="userForm.jobTitle" />
        </a-form-item>
        <a-form-item label="开始工作" name="jobTime">
          <a-date-picker
              v-model:value="userForm.jobTime"
              :format="dateFormat"
              class="app-input"
              placeholder="选择工作时间"
              style="width: 100%"
          />
        </a-form-item>
        <a-form-item :wrapper-col="{ offset: 1 }">
          <a-button type="primary" @click="onSubmit">保存修改</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </a-row>
</template>

<style scoped>
.base-info-card {
  width: 100%;
  margin-bottom: 15px;
}

.app-input {
  border-radius: 4px;
  background: #f2f3f5;
  color: #252933;
  border: none;
}

.app-input:focus {
  outline: none !important;
  box-shadow: none !important;
}

.avatar-uploader > .ant-upload {
  width: 128px;
  height: 128px;
}

.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}

.dashed-border {
  border: 2px dashed #999;
  padding: 15px;
}

.tag-item {
  margin: 0 8px 8px 0;
}

.last-tag {
  margin-right: 0;
}
</style>
