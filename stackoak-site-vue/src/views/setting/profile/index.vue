<script setup lang="ts">
import {reactive, ref, toRaw} from 'vue';
import type {UnwrapRef} from 'vue';
import type {Rule} from 'ant-design-vue/es/form';
import type { Dayjs } from 'dayjs';
type RangeValue = [Dayjs, Dayjs];

interface UserForm {
  nickname: string,
  name: string,
  gender: number,
  intro: string,
  personBlog: string,
  github: string,
  gitee: string,
  csdn: string,
  bilibli: string,
  bokeyuan: string,
  authorQr: string,
  region: string | undefined;
  date1: RangeValue,
  delivery: boolean;
  type: string[];
  resource: string;
  desc: string;
}

const formRef = ref();
const labelCol = {span: 4};
const wrapperCol = {span: 20};
const userForm: UnwrapRef<UserForm> = reactive({
  nickname: 'string',
  gender: 1,
  intro: '',
  personBlog: '',
  github: '',
  gitee: '',
  csdn: '',
  bilibli: '',
  bokeyuan: '',
  authorQr: '',
  name: '天才程序员',
  region: undefined,
  date1: undefined,
  delivery: false,
  type: [],
  resource: '',
  desc: '',
});
const rules: Record<string, Rule[]> = {
  name: [
    {required: true, message: 'Please input Activity name', trigger: 'change'},
    {min: 0, max: 100, message: 'Length should be 3 to 5', trigger: 'blur'},
  ],

  region: [{required: true, message: 'Please select Activity zone', trigger: 'change'}],
  date1: [{required: true, message: 'Please pick a date', trigger: 'change', type: 'object'}],
  type: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one activity type',
      trigger: 'change',
    },
  ],
  resource: [{required: true, message: 'Please select activity resource', trigger: 'change'}],
  desc: [{required: true, message: 'Please input activity form', trigger: 'blur'}],
};
const onSubmit = () => {
  formRef.value
      .validate()
      .then(() => {
        console.log('values', userForm, toRaw(userForm));
      })
      .catch(error => {
        console.log('error', error);
      });
};
const resetForm = () => {
  formRef.value.resetFields();
};

const value1 = ref("1");
const options = [...Array(25)].map((_, i) => ({value: (i + 10).toString(36) + (i + 1)}));
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
          <a-avatar :size="{ xs: 24, sm: 32, md: 40, lg: 64, xl: 100, xxl: 120 }">
            <template #icon>
              <a-image src="/avatar.jpeg"/>
            </template>
          </a-avatar>
        </a-form-item>
        <a-form-item ref="name" label="显示昵称" name="name">
          <a-input class="app-input" v-model:value="userForm.name"/>
        </a-form-item>
        <a-form-item label="性别" name="gender">
          <a-radio-group v-model:value="userForm.gender">
            <a-radio :value=1>男</a-radio>
            <a-radio :value=0>女</a-radio>
            <a-radio :value=2>保密</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="个人简介" name="intro">
          <a-textarea placeholder="个人介绍，最多100字" :maxlength="100" class="app-input"
                      v-model:value="userForm.intro"/>
        </a-form-item>
        <a-form-item label="个人博客" name="desc">
          <a-input class="app-input" v-model:value="userForm.desc"/>
        </a-form-item>
        <a-form-item label="Github" name="desc">
          <a-input class="app-input" v-model:value="userForm.desc"/>
        </a-form-item>
        <a-form-item label="Gitee" name="desc">
          <a-input class="app-input" v-model:value="userForm.desc"/>
        </a-form-item>
        <a-form-item label="CSDN" name="desc">
          <a-input class="app-input" v-model:value="userForm.desc"/>
        </a-form-item>
        <a-form-item label="博客园" name="desc">
          <a-input class="app-input" v-model:value="userForm.desc"/>
        </a-form-item>
        <a-form-item label="哔哩哔哩" name="desc">
          <a-input class="app-input" v-model:value="userForm.desc"/>
        </a-form-item>
        <a-form-item label="作者二维码" name="desc">
          <a-input class="app-input" v-model:value="userForm.desc"/>
        </a-form-item>
        <a-form-item label="兴趣标签" name="desc">
          <a-tag v-for="i in 50">java{{ i }}</a-tag>
        </a-form-item>
      </a-form>
    </a-card>
    <a-card title="教育信息" class="base-info-card">
      <a-form
          ref="formRef"
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          :model="userForm">
        <a-form-item ref="name" label="学校" name="name">
          <a-input class="app-input" v-model:value="userForm.name"/>
        </a-form-item>
        <a-form-item ref="name" label="专业" name="name">
          <a-input class="app-input" v-model:value="userForm.name"/>
        </a-form-item>
        <a-form-item ref="name" label="学历" name="name">
          <a-input class="app-input" v-model:value="userForm.name"/>
        </a-form-item>
        <a-form-item label="入学时间" name="date1">
          <a-range-picker
              v-model:value="userForm.date1"
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
          ref="formRef"
          :model="userForm">
        <a-form-item ref="name" label="职业方向" name="name">
          <a-input class="app-input" v-model:value="userForm.name"/>
        </a-form-item>
        <a-form-item ref="name" label="任职公司" name="name">
          <a-input class="app-input" v-model:value="userForm.name"/>
        </a-form-item>
        <a-form-item ref="name" label="岗位" name="name">
          <a-input class="app-input" v-model:value="userForm.name"/>
        </a-form-item>
        <a-form-item label="开始工作" name="date1">
          <a-date-picker
              class="app-input"
              v-model:value="userForm.date1"
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
</style>
