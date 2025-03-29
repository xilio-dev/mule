<script setup lang="ts">

/*------------------------------------变量定义------------------------------------------*/
import { onMounted, reactive } from "vue";
const props = defineProps<{
  type: number
}>();
const emit = defineEmits(['checkCover'])
const pageQuery = reactive({
  type: props.type,
  current: 1,
  size: 100
});
const photos = reactive<any[]>([]); // 添加类型注解，避免TS报错

/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadThemePhoto();
});

/*------------------------------------数据加载--------------------------------------------*/
import { Https } from "@/utils/request/https.ts";
import { API } from "@/api/ApiConfig.ts";
const loadThemePhoto = async () => {
  const res = await Https.action(API.THEME_PHOTO.list, pageQuery);
  // @ts-ignore
  photos.splice(0, photos.length, ...(res.records ?? []));
};

/*------------------------------------核心业务--------------------------------------------*/


</script>

<template>
  <div class="grid-container">
    <div class="grid-item" v-for="item in photos" :key="item.id">
      <img :src="item.limg" :alt="item.name" />
      <div class="overlay">
        <span class="photo-name">{{ item.name }}</span>
        <a-button type="primary" size="small" @click="emit('checkCover',item,type)" >使用</a-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 容器样式 */
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3列，每列等宽 */
  gap: 20px; /* 图片之间的间隔 */
  padding: 20px; /* 容器内边距 */
  max-width: 1200px; /* 最大宽度，可根据需要调整 */
  margin: 0 auto; /* 居中 */
}

/* 图片项样式 */
.grid-item {
  width: 100%; /* 占满网格单元 */
  height: 150px; /* 固定高度，与img一致 */
  position: relative; /* 为overlay定位 */
  overflow: hidden; /* 防止内容溢出 */
  border-radius: 8px; /* 圆角 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 阴影 */
}

/* 图片样式 */
.grid-item img {
  width: 100%;
  height: 150px;
  object-fit: cover;
  display: block;
}

/* 悬浮层样式 */
.overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 40px; /* 固定高度，可调整 */
  background: rgba(0, 0, 0, 0.7); /* 半透明黑色背景 */
  color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 10px;
  opacity: 0; /* 默认隐藏 */
  transition: opacity 0.3s ease; /* 平滑过渡 */
}

/* 鼠标悬浮时显示 */
.grid-item:hover .overlay {
  opacity: 1;
}

/* 图片名称样式 */
.photo-name {
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis; /* 超长部分显示省略号 */
  max-width: 70%; /* 限制宽度，避免与按钮重叠 */
}
/* 响应式调整 */
@media (max-width: 768px) {
  .grid-container {
    grid-template-columns: repeat(2, 1fr); /* 小屏幕显示2列 */
  }
}

@media (max-width: 480px) {
  .grid-container {
    grid-template-columns: 1fr; /* 更小屏幕显示1列 */
  }
}
</style>
