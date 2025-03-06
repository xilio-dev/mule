<script setup lang="ts">

import ImageSelect from "@/components/ImageSelect/index.vue";
import {onMounted, ref} from "vue";
import {materialList} from "@/api/material.ts";
import {ImageUtils} from "@/utils/file.ts";
// 初始化 materials
const materials = ref([]);
onMounted(() => {
  loadMaterialList();
})
// 加载用户素材
const loadMaterialList = async () => {
  try {
    // 调用 API 获取素材列表
    const rawMaterials = await materialList() || [];
    if (rawMaterials) {
      materials.value = rawMaterials.map(item => ({
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
