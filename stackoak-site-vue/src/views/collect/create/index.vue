<template>
  <a-form
      v-bind="{labelCol: { span: 4 },wrapperCol: { span: 20 }}"
      ref="newCollectFormRef"
      labelAlign="right"
      :model="newCollectForm"
      :rules="newCollectRules"
  >
    <a-form-item ref="name" label="名字" name="name">
      <a-input v-model:value="newCollectForm.name"/>
    </a-form-item>
    <a-form-item label="描述" name="description">
      <a-textarea v-model:value="newCollectForm.description"/>
    </a-form-item>
    <a-form-item label="状态" name="status" required>
      <a-radio-group v-model:value="newCollectForm.status">
        <a-radio :value="1">公开</a-radio>
        <a-radio :value="2">私有</a-radio>
      </a-radio-group>
    </a-form-item>
    <a-form-item>
      <a-flex justify="flex-end" :gap="15">
        <a-button @click="resetForm">取消</a-button>
        <a-button type="primary" @click="onNewCollect">保存</a-button>
      </a-flex>
    </a-form-item>
  </a-form>
</template>
<script lang="ts" setup>
import {reactive, ref} from 'vue';
import type {Rule} from "ant-design-vue/es/form";
import {Https} from "@/utils/request/https.ts";
import {API} from "@/api/ApiConfig.ts";

const props = defineProps<{
  collect?: object
}>();
const emit = defineEmits(['createOk', 'cancelCreate'])
const newCollectFormRef = ref();
const newCollectForm = reactive({
  name: '',
  description: '',
  status: 1
});
const newCollectRules: Record<string, Rule[]> = {
  name: [
    {required: true, message: '请输入收藏夹名字', trigger: 'change'},
    {min: 0, max: 8, message: '不能超过8个字', trigger: 'change'},
  ]
}

//新建收藏夹
const onNewCollect = () => {
  newCollectFormRef.value
      .validate()
      .then(() => {
        Https.action(API.COLLECT.save, newCollectForm).then(res => {
          emit('createOk')
        })
      })
}
const resetForm = () => {
  newCollectFormRef.value.resetFields();
  emit('cancelCreate')
};
</script>

