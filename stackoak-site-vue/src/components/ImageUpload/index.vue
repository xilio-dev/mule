<template>
  <a-upload-dragger
      v-model:fileList="fileList"
      name="file"
      :multiple="false"
      :max-count="1"
      :headers="headers"
      :action=uploadUrl
      :withCredentials="true"
      list-type="picture"
      @change="handleChange"
      @drop="handleDrop"
  >
    <p class="ant-upload-drag-icon">
      <inbox-outlined></inbox-outlined>
    </p>
    <p class="ant-upload-text"><span style="color: red">单击</span>或<span style="color: red">拖动</span>图片到此区域进行上传
    </p>
    <p class="ant-upload-hint">
      文件上传不能大于5MB，对于大文件系统会自动进行压缩
    </p>
  </a-upload-dragger>
</template>

<script lang="ts" setup>
import {ref} from 'vue';
import {InboxOutlined} from '@ant-design/icons-vue';
import {message} from 'ant-design-vue';
import type {UploadChangeParam} from 'ant-design-vue';

const emit = defineEmits(['upload-success','clear-upload-list'])

const headers = ref({})
const uploadUrl = ref(import.meta.env.VITE_APP_BASE_API + "/material/image/upload")
const fileList = ref([]);
const handleChange = (info: UploadChangeParam) => {
  const status = info.file.status;
  if (status !== 'uploading') {
    // console.log(info.file, info.fileList);
  }
  if (status === 'done') {
    const {id,imgUrl} = info.file.response.data
    emit("upload-success", {imgUrl: imgUrl,id:id})
    message.success('图片上传成功');
  } else if (status === 'error') {
    message.error('图片上传失败');
  }
};
const clear=  ()=>{
  fileList.value=[]
}
//一定要在声明后用
defineExpose({clear})
function handleDrop(e: DragEvent) {

}
</script>

