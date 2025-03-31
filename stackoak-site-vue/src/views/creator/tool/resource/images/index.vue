<script setup lang="ts">

import ImageSelect from "@/components/ImageSelect/index.vue";
import {onMounted, reactive, ref} from "vue";
import {materialList} from "@/api/material.ts";
import {ImageUtils} from "@/utils/file.ts";
import {Https} from "@/utils/request/https.ts";
import {API} from "@/api/ApiConfig.ts";
const userMaterialQuery = reactive({
  current: 1,
  size: 10
})
const materials = ref([]);
onMounted(() => {
  loadMaterialList();
})
// 加载用户素材
const loadMaterialList = async () => {
  try {
    // 调用 API 获取素材列表
    const res = await Https.action(API.MATERIAL.user, userMaterialQuery)
    if (res.records) {
      materials.value = res.records.map(item => ({
        ...item,
        imgUrl: ImageUtils.getImgUrl(item.imgUrl)
      }));
    } else {
      materials.value = [];
    }
  } catch (err) {

  }
};

const selectChange = (items: Array) => {
  if (items && items.length > 0) {

  } else {

  }
}
</script>

<template>
  <a-card title="图片素材">
    <template #extra>
      <a-button size="small" type="primary">上传图片</a-button>
    </template>
    <a-flex style="margin-bottom:10px;margin-left: 15px">
      <div>批量管理</div>
    </a-flex>
    <ImageSelect ref="imageSelectRef" @select-change="selectChange" :items="materials" :multi="true"/>
  </a-card>
</template>

<style scoped>

</style>
