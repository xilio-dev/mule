<template>
  <a-form
      layout="vertical"
      ref="formRef"
      :model="formState"
      :rules="rules"
  >
    <a-form-item ref="reasonType" label="举报类型" name="reasonType">
      <a-flex class="reason-container" :gap="6" wrap="wrap" align="center"
              justify="space-around">
        <a-button class="reason-item" v-for="item in reasonTypeList" type="dashed" size="large">{{ item.label }}
        </a-button>
      </a-flex>
    </a-form-item>

    <a-form-item label="详细说明" name="reasonText">
      <a-textarea showCount :rows="4" placeholder="请详细描述违规内容，方便审核人员审核！" :maxlength="200"
                  v-model:value="formState.reasonText"/>
    </a-form-item>
    <a-form-item>
      <a-flex justify="flex-end">
        <a-button type="primary" @click="onSubmit">立刻举报</a-button>
        <a-button style="margin-left: 10px" @click="resetForm">取消</a-button>
      </a-flex>
    </a-form-item>
  </a-form>
</template>
<script lang="ts" setup>
import {reactive, ref, toRaw} from 'vue';
import type {UnwrapRef} from 'vue';
import type {Rule} from 'ant-design-vue/es/form';
//举报原因类型(1:内容色情 2:过于暴力 3:侵犯名誉 4:内容抄袭 5:谈论政治 6:肖像侵权 7:著作侵权 8:其他侵权)
const reasonTypeList = reactive([
  {label: '内容色情', value: '1'},
  {label: '过于暴力', value: '2'},
  {label: '侵犯名誉', value: '3'},
  {label: '内容抄袭', value: '4'},
  {label: '谈论政治', value: '5'},
  {label: '肖像侵权', value: '6'},
  {label: '著作侵权', value: '7'},
  {label: '其他侵权', value: '8'},
])

interface FormState {
  reasonType: string;
  reasonText: string;
}

const formRef = ref();
const labelCol = {span: 5};
const wrapperCol = {span: 19};
const formState: UnwrapRef<FormState> = reactive({
  reasonType: '',
  reasonText: '',
});
const rules: Record<string, Rule[]> = {
  reasonType: [
    {required: true, message: '必须选择举报的原因！', trigger: 'change'},
  ],
};
const onSubmit = () => {
  formRef.value
      .validate()
      .then(() => {
        console.log('values', formState, toRaw(formState));
      })
      .catch(error => {
        console.log('error', error);
      });
};
const resetForm = () => {
  formRef.value.resetFields();
};
</script>
<style scoped>
.reason-container {
  width: 100%;

}

.reason-item {

}
</style>
