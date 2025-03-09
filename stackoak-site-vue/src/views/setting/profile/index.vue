<script setup lang="ts">
import {onMounted, reactive, ref, toRaw} from 'vue';
import type {UnwrapRef} from 'vue';
import type {Rule} from 'ant-design-vue/es/form';
import type {Dayjs} from 'dayjs';
import {getUserProfile, updateProfile} from "@/api/user.ts";
import {message} from "ant-design-vue";

type RangeValue = [Dayjs, Dayjs];
const loadUserProfile=()=>{
  getUserProfile().then(res=>{
    Object.assign(userForm, res);
  })
}
onMounted(()=>{
  loadUserProfile()
})
interface UserForm {
  id:undefined,
  avatar: string,
  nickname: string,
  gender: string,
  introduce: string,
  personBlogAddress: string,
  github: string,
  gitee: string,
  csdn: string,
  bokeyuan: string,
  bilibli: string,

  authorQr: string,

  eduLevel: string,
  universityName: string,
  majorName: string,
  eduStartTime: string,
  eduEndTime: string,
  eduTime: RangeValue,

  careerField: string,
  company: string,
  jobTitle: string,
  jobTime: string,
}

const formRef = ref();
const labelCol = {span: 4};
const wrapperCol = {span: 20};
const userForm: UnwrapRef<UserForm> = reactive({
  id:undefined,
  avatar: '',
  nickname: undefined,
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
  eduStartTime: '',
  eduEndTime: '',
  eduTime: undefined,
  careerField: '',
  company: '',
  jobTitle: '',
  jobTime: '',
});
const rules: Record<string, Rule[]> = {
  nickname: [
    {required: true, message: '请填写您的账号昵称', trigger: 'change'},
    {min: 2, max: 15, message: '字数限制 2-15', trigger: 'change'},
  ],
  gender: [
    {required: true, message: '请选择性别', trigger: 'change'}
  ],
};
const onSubmit = () => {
  console.log(formRef.value.validate());
  formRef.value
      .validate()
      .then(() => {
        if (userForm.eduTime) {
          userForm.eduStartTime = userForm.eduTime[0]
          userForm.eduEndTime = userForm.eduTime[1]
        }
        updateProfile(userForm).then(res => {
          message.info("更新成功")
        })
      })
};


const options = [...Array(25)].map((_, i) => ({value: (i + 10).toString(36) + (i + 1)}));


import {ref} from 'vue';
import {PlusOutlined, LoadingOutlined} from '@ant-design/icons-vue';
import {message} from 'ant-design-vue';
import type {UploadChangeParam, UploadProps} from 'ant-design-vue';

function getBase64(img: Blob, callback: (base64Url: string) => void) {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result as string));
  reader.readAsDataURL(img);
}

const fileList = ref([]);
const loading = ref<boolean>(false);
const imageUrl = ref<string>('');

const handleChange = (info: UploadChangeParam) => {
  if (info.file.status === 'uploading') {
    loading.value = true;
    return;
  }
  if (info.file.status === 'done') {
    // Get this url from response in real world.
    getBase64(info.file.originFileObj, (base64Url: string) => {
      imageUrl.value = base64Url;
      loading.value = false;
    });
  }
  if (info.file.status === 'error') {
    loading.value = false;
    message.error('upload error');
  }
};

const beforeUpload = (file: UploadProps['fileList'][number]) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  if (!isJpgOrPng) {
    message.error('You can only upload JPG file!');
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error('Image must smaller than 2MB!');
  }
  return isJpgOrPng && isLt2M;
};
</script>

<template>
  <a-row style="width: 100%;">
    <a-card title="基本信息" class="base-info-card">
      <a-form
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          ref="formRef"
          :model="userForm"
          :rules="rules">
        <a-form-item label="头像" name="delivery">
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
            <a-avatar v-if="userForm.avatar"  :size="{ xs: 24, sm: 32, md: 40, lg: 64, xl: 100, xxl: 120 }">
              <template #icon>
                <a-image :preview="false" :src="userForm.avatar" alt="avatar"/>
              </template>
            </a-avatar>

            <div v-else>
              <loading-outlined v-if="loading"></loading-outlined>
              <plus-outlined v-else></plus-outlined>
              <div class="ant-upload-text">上传</div>
            </div>
          </a-upload>


        </a-form-item>
        <a-form-item label="显示昵称" name="nickname">

          <a-input class="app-input" v-model:value="userForm.nickname"/>
        </a-form-item>
        <a-form-item label="性别" name="gender">
          <a-radio-group v-model:value="userForm.gender">
            <a-radio :value=1>男</a-radio>
            <a-radio :value=0>女</a-radio>
            <a-radio :value=2>保密</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="个人简介" name="introduce">
          <a-textarea placeholder="个人介绍，最多100字" :maxlength="100" class="app-input"
                      v-model:value="userForm.introduce"/>
        </a-form-item>
        <a-form-item label="个人博客" name="personBlogAddress">
          <a-input class="app-input" v-model:value="userForm.personBlogAddress"/>
        </a-form-item>
        <a-form-item label="Github" name="github">
          <a-input class="app-input" v-model:value="userForm.github"/>
        </a-form-item>
        <a-form-item label="Gitee" name="gitee">
          <a-input class="app-input" v-model:value="userForm.gitee"/>
        </a-form-item>
        <a-form-item label="CSDN" name="csdn">
          <a-input class="app-input" v-model:value="userForm.csdn"/>
        </a-form-item>
        <a-form-item label="博客园" name="bokeyuan">
          <a-input class="app-input" v-model:value="userForm.bokeyuan"/>
        </a-form-item>
        <a-form-item label="哔哩哔哩" name="bilibli">
          <a-input class="app-input" v-model:value="userForm.bilibli"/>
        </a-form-item>
        <a-form-item label="作者二维码" name="authorQr">
          <a-input class="app-input" v-model:value="userForm.authorQr"/>
        </a-form-item>
        <a-form-item label="兴趣标签" name="tag">
          <a-tag v-for="i in 50">java{{ i }}</a-tag>
        </a-form-item>
      </a-form>
    </a-card>
    <a-card title="教育信息" class="base-info-card">
      <a-form
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          :model="userForm">
        <a-form-item ref="name" label="学校" name="universityName">
          <a-input class="app-input" v-model:value="userForm.universityName"/>
        </a-form-item>
        <a-form-item ref="name" label="专业" name="majorName">
          <a-input class="app-input" v-model:value="userForm.majorName"/>
        </a-form-item>
        <a-form-item ref="name" label="学历" name="eduLevel">
          <a-input class="app-input" v-model:value="userForm.eduLevel"/>
        </a-form-item>
        <a-form-item label="入学时间" name="eduTime">
          <a-range-picker
              v-model:value="userForm.eduTime"
              class="app-input"
              type="date"
              placeholder="选择入学时间"
              style="width: 100%"
          />
        </a-form-item>
      </a-form>
    </a-card>
    <a-card title="工作信息" class="base-info-card">
      <a-form
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          :model="userForm">
        <a-form-item ref="name" label="职业方向" name="careerField">
          <a-input class="app-input" v-model:value="userForm.careerField"/>
        </a-form-item>
        <a-form-item ref="name" label="任职公司" name="company">
          <a-input class="app-input" v-model:value="userForm.company"/>
        </a-form-item>
        <a-form-item ref="name" label="岗位" name="jobTitle">
          <a-input class="app-input" v-model:value="userForm.jobTitle"/>
        </a-form-item>
        <a-form-item label="开始工作" name="jobTime">
          <a-date-picker
              class="app-input"
              v-model:value="userForm.jobTime"
              type="date"
              placeholder="选择工作时间"
              style="width: 100%"
          />
        </a-form-item>
        <a-form-item :wrapper-col="{offset: 1 }">
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
  border: none #f2f3f5;
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
</style>
