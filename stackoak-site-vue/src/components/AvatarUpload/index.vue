<template>
  <a-upload
      v-model:file-list="fileList"
      name="file"
      list-type="picture-card"
      class="avatar-uploader"
      :show-upload-list="false"
      :action="action"
      :before-upload="beforeUpload"
      @change="handleChange"
      :multiple="false"
      :max-count="1"
      :headers="headers"
      :withCredentials="true"
  >
    <img class="avatar-image" v-if="imgUrl" :src="ImageUtils.getImgUrl(imgUrl)" alt="avatar"/>
    <div v-else>
      <loading-outlined v-if="loading"></loading-outlined>
      <plus-outlined v-else></plus-outlined>
      <div class="ant-upload-text">上传</div>
    </div>
  </a-upload>
</template>
<script lang="ts" setup>
import {ref} from 'vue';
import {PlusOutlined, LoadingOutlined} from '@ant-design/icons-vue';
import {message} from 'ant-design-vue';
import type {UploadChangeParam, UploadProps} from 'ant-design-vue';
import {ImageUtils} from "@/utils/file.ts";

const emit = defineEmits(['update:imgUrl'])
const props = defineProps<{
  action: string,
  imgUrl: string,
  headers: object
}>()
const fileList = ref([]);
const loading = ref<boolean>(false);
const handleChange = (info: UploadChangeParam) => {
  if (info.file.status === 'uploading') {
    loading.value = true;
    return;
  }
  if (info.file.status === 'done') {
    const data = info.file.response.data
    emit('update:imgUrl', data.imgUrl)
    loading.value = false;
  }
  if (info.file.status === 'error') {
    loading.value = false;
  }
};

const beforeUpload = (file: UploadProps['fileList'][number]) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  if (!isJpgOrPng) {
    message.error('只能上传jpeg或png格式');
  }
  const isLt2M = file.size / 1024 / 1024 < 1;
  if (!isLt2M) {
    message.error('图片必须小于1MB!');
  }
  return isJpgOrPng && isLt2M;
};
</script>
<style scoped>
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
.avatar-image{
  width: 100px;
  height: 100px;
  border-radius: 8px;
}
</style>
