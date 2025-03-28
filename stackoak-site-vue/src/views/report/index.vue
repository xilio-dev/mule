<template>
  <a-form
      layout="vertical"
      ref="formRef"
      :model="reportFormState"
      :rules="rules"
  >
    <a-form-item ref="reasonType" label="举报类型" name="reasonType">
      <div class="reason-container">
        <a-button
            class="reason-item"
            v-for="item in reasonTypeList"
            :key="item.value"
            :type="reportFormState.reasonType === item.value ? 'primary' : 'dashed'"
            size="large"
            @click="selectReason(item.value)"
        >
          {{ item.label }}
        </a-button>
      </div>
    </a-form-item>

    <a-form-item label="详细说明" name="reasonText">
      <a-textarea
          showCount
          :rows="4"
          placeholder="请详细描述违规内容，方便审核人员审核！"
          :maxlength="200"
          v-model:value="reportFormState.reasonText"
      />
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
import type {Rule} from 'ant-design-vue/es/form';
import {Https} from "@/utils/request/https.ts";
import {API} from "@/api/ApiConfig.ts";
import {type ReportResponse, type ReportRequest, ReportTargetType} from '@/types/report';
import {message} from "ant-design-vue";

const props = defineProps<{
  targetType: ReportTargetType;
  targetId: string;
}>();
// 举报原因类型
const reasonTypeList = reactive([
  {label: '内容色情', value: '1'},
  {label: '过于暴力', value: '2'},
  {label: '侵犯名誉', value: '3'},
  {label: '内容抄袭', value: '4'},
  {label: '谈论政治', value: '5'},
  {label: '肖像侵权', value: '6'},
  {label: '著作侵权', value: '7'},
  {label: '其他侵权', value: '8'},
]);
const formRef = ref();
const reportFormState = reactive<ReportRequest>({
  targetType: props.targetType,
  targetId: props.targetId,
  reasonType: undefined,
  reasonText: '',
});

const rules: Record<string, Rule[]> = {
  reasonType: [
    {required: true, message: '必须选择举报的原因！', trigger: 'change'},
  ],
};

// 选择举报类型
const selectReason = (value: number) => {
  reportFormState.reasonType = value;
};

const onSubmit = () => {
  formRef.value
      .validate()
      .then(() => {
        return Https.action<any>(API.REPORT.createReport, reportFormState);
      })
      .then((res: any) => {
        message.info('举报已提交，我们会尽快审核！');
        resetForm();
      })
      .catch((error: any) => {
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
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
}

.reason-item {
  flex: 1; /* 让按钮平均分配宽度 */
  min-width: 100px; /* 设置最小宽度，避免太窄 */
}
</style>
