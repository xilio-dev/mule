<script setup lang="ts">

import ImageUpload from "@/components/ImageUpload/index.vue";
import {computed, onMounted, reactive, ref, watch} from "vue";
import {bindMaterial, materialList} from "@/api/material.ts";
import ImageSelect from "@/components/ImageSelect/index.vue";
import {ImageUtils} from "@/utils/file.ts";
import {Https} from "@/utils/request/https.ts";
import {API} from "@/api/ApiConfig.ts";
/*------------------------------------交互---------------------------------------------*/
const emit = defineEmits(['confirm-select', 'close-drawer'])
defineProps(['openDrawer'])

/*------------------------------------变量定义------------------------------------------*/
const systemMaterialList = ref([])
const userMaterialList = ref([]);
const spice = ref(false)
const activeKey = ref('1');
const uploadInfo = ref({})
const uploadImgRef = ref()
const imageSelectRef = ref()
const userMaterialQuery = reactive({
  current: 1,
  size: 10
})
const sysMaterialQuery = reactive({
  current: 1,
  size: 10
})
/*------------------------------------生命周期-------------------------------------------*/

onMounted(() => {

})
watch(activeKey, async (newKey, oldKey) => {
  try {
    if (newKey === '2') {
      await loadUserMaterialList();
    } else if (newKey === '3') {
      await loadSystemMaterialList()
    }
  } finally {
  }
})
/*------------------------------------核心业务--------------------------------------------*/
const onConfirmSelectImg = (imgUrl: string) => {
  uploadInfo.value.imgUrl = imgUrl
}
const onConfirmSelectUserImg = (imgUrl: string) => {
  uploadInfo.value.imgUrl = imgUrl
}
const bindMaterial = async (params: any) => {
  try {
    await Https.action(API.MATERIAL.bind, params)
  } catch (err) {

  }
}
/*------------------------------------
})

/*------------------------------------数据加载--------------------------------------------*/
// 加载用户素材
const loadUserMaterialList = async () => {
  try {
    const res = await Https.action(API.MATERIAL.user, userMaterialQuery)
    if (res.records) {
      userMaterialList.value = res.records.map(item => ({
        ...item,
        imgUrl: ImageUtils.getImgUrl(item.imgUrl)
      }));
    } else {
      userMaterialList.value = [];
    }
  } catch (err) {

  }
};
// 加载系统素材
const loadSystemMaterialList = async () => {
  try {
    const res = await Https.action(API.MATERIAL.list, sysMaterialQuery)
    if (res.records) {
      systemMaterialList.value = res.records.map(item => ({
        ...item,
        imgUrl: ImageUtils.getImgUrl(item.imgUrl)
      }));
    } else {
      systemMaterialList.value = [];
    }
  } catch (err) {

  }
};
/*------------------------------------核心业务--------------------------------------------*/
const onConfirmSelect = () => {
  //保存到我的素材
  if (spice.value && uploadInfo.value.imgUrl !== '' && activeKey.value === '1') {
    //调用接口保存
    bindMaterial({id: uploadInfo.value.id});
    //刷新素材
    loadUserMaterialList();
  }
  emit('confirm-select', uploadInfo.value.imgUrl)
  emit('close-drawer'); // 通知父组件抽屉已关闭
  clearAll()
};

/*------------------------------------ 工具函数 -------------------------------------------*/
const uploadSuccessCallback = (info: any) => {
  uploadInfo.value = info
}
const hasImg = computed(() => {
  return uploadInfo.value.imgUrl !== null && uploadInfo.value.imgUrl !== undefined
})
const clearAll = () => {
  uploadImgRef.value.clear()
  uploadInfo.value = {}
  //清空我的素材图片选择
  imageSelectRef.value.clear()
  //激活选项菜单1
  activeKey.value = '1'
  //取消勾选保存到我的素材选项
  spice.value = false
}
const onCloseDrawer = () => {
  emit('close-drawer'); // 通知父组件抽屉已关闭
  clearAll()
};
const onCancel = () => {
  emit('close-drawer')
  clearAll()
}
const selectChange = (items: Array) => {
  if (items && items.length > 0) {
    uploadInfo.value.imgUrl = items[0].imgUrl;
  } else {
    uploadInfo.value.imgUrl = undefined;
  }
}
</script>

<template>
  <a-drawer :footer-style="{ textAlign: 'right'  }" :bodyStyle="{padding:'0 24px'}" width="50%"
            :z-index="999999"
            title="设置文章封面" placement="right"
            :open="openDrawer" @close="onCloseDrawer">
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane key="1" tab="上传图片">
        <a-flex vertical justify="space-between" style="height: 100%;">
          <ImageUpload ref="uploadImgRef" @uploadSuccess="uploadSuccessCallback"/>
        </a-flex>
      </a-tab-pane>
      <a-tab-pane key="2" tab="我的素材">
        <ImageSelect ref="imageSelectRef" @select-change="selectChange" :items="userMaterialList" :multi="false"/>
      </a-tab-pane>
      <a-tab-pane key="3" tab="免费图片" force-render>
        <ImageSelect ref="imageSelectRef" @select-change="selectChange" :items="systemMaterialList" :multi="false"/>
      </a-tab-pane>
    </a-tabs>
    <template v-if="hasImg" #footer>
      <a-flex justify="space-between" align="center" style="margin: 0 15px;">
        <a-checkbox v-show="activeKey==='1'" v-model:checked="spice">保存到我的素材</a-checkbox>
        <a-flex :gap="15" style="float: right; margin-left: auto;">
          <a-button @click="onCancel" type="default">取消</a-button>
          <a-button @click="onConfirmSelect" type="primary">确定</a-button>
        </a-flex>
      </a-flex>
    </template>
  </a-drawer>
</template>

<style scoped>

</style>
