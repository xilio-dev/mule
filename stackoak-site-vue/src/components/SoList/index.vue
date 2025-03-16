<script setup lang="ts">
import {ImageUtils} from "@/utils/file.ts";

defineProps<{
  list: [],
  loadMore:false
}>();
</script>

<template>
  <div v-for="item in list" class="title-container">
    <a-flex :gap="8" style="width: 100%;">
      <div class="cover" v-if="item.cover">
        <slot name="cover" :item="item">
          <a-image style="width: 120px;height: 68px" :src="ImageUtils.getImgUrl(item.cover)" :preview="false"/>
        </slot>
      </div>
      <a-flex style="width: 100%;" vertical justify="space-between">
        <a-flex justify="space-between" :gutter="4">
          <h3 class="so-title so-ellipsis-text">
            <slot name="title" :item="item">
              {{ item.title }}
            </slot>
          </h3>
          <div class="so-right">
            <slot name="right" :item="item">
              {{ item.createdAt }}
            </slot>
          </div>
        </a-flex>
        <div>
          <slot name="content" :item="item">

          </slot>
        </div>
        <a-flex justify="space-between">
          <a-flex :gap="8">
            <slot name="tag" :item="item"/>
          </a-flex>
          <a-flex :gap="8" style="cursor: pointer">
            <slot name="action" :item="item"/>
          </a-flex>
        </a-flex>
      </a-flex>
    </a-flex>
    <a-divider/>
  </div>
  <div style="text-align: center" v-if="loadMore">
    <slot name="loadMore">
      <a-button type="default">加载更多</a-button>
    </slot>
  </div>
</template>

<style scoped>
.cover {
  border-radius: 8px;
  background-size: 100%;
}

.so-title {
  flex-shrink: 1;
  cursor: pointer;
}

.so-title a {
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%; /* 确保宽度不超过父容器 */
}

.so-right {
  flex-shrink: 0;
}

/* 父容器的宽度限制 */
.title-container {
  width: 100%; /* 确保父容器宽度有限制 */
  overflow: hidden; /* 防止子元素超出 */
}

/* 子元素的省略号样式 */
.so-ellipsis-text {
  white-space: nowrap; /* 防止文本换行 */
  overflow: hidden; /* 隐藏超出部分 */
  text-overflow: ellipsis; /* 显示省略号 */
  max-width: 100%; /* 确保宽度不超过父容器 */
  display: block; /* 确保样式生效 */
}
</style>
