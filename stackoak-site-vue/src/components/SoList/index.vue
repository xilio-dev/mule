<script setup lang="ts">
import { ImageUtils } from "@/utils/file.ts";

defineProps<{
  list: any[], // 修改为 any[] 以避免空数组类型问题
  loadMore: boolean // 明确布尔类型
}>();
</script>

<template>
  <div v-for="item in list" :key="item.id" class="title-container">
    <a-flex :gap="8" style="width: 100%;">
      <div class="cover" v-if="item.cover">
        <slot name="cover" :item="item">
          <a-image
              style="width: 120px; height: 68px; object-fit: cover;"
              :src="ImageUtils.getImgUrl(item.cover)"
              :preview="false"
          />
        </slot>
      </div>
      <a-flex style="flex: 1; min-width: 0;" vertical justify="space-between">
        <a-flex justify="space-between" :gutter="4" style="min-width: 0;">
          <h3 class="so-title so-ellipsis-text">
            <slot name="title" :item="item">
              <!-- 使用 v-html 处理特殊字符并保持样式 -->
              <span v-html="item.title"></span>
            </slot>
          </h3>
          <div class="so-right">
            <slot name="right" :item="item">
              {{ item.createdAt }}
            </slot>
          </div>
        </a-flex>
        <div class="content-wrapper">
          <slot name="content" :item="item" />
        </div>
        <a-flex justify="space-between">
          <a-flex :gap="8">
            <slot name="tag" :item="item" />
          </a-flex>
          <a-flex :gap="8" style="cursor: pointer">
            <slot name="action" :item="item" />
          </a-flex>
        </a-flex>
      </a-flex>
    </a-flex>
    <a-divider />
  </div>
  <div style="text-align: center" v-if="loadMore">
    <slot name="loadMore">
      <a-button type="default">加载更多</a-button>
    </slot>
  </div>
</template>

<style scoped>
.title-container {
  width: 100%;
  max-width: 100%;
  overflow: hidden;
}

.cover {
  border-radius: 8px;
  flex-shrink: 0; /* 防止封面图被压缩 */
}

.so-title {
  margin: 0; /* 移除默认外边距 */
  flex: 1; /* 允许标题占据可用空间 */
  min-width: 0; /* 关键：允许元素收缩 */
  cursor: pointer;
}

.so-title span {
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-word; /* 处理特殊字符换行 */
}

/* 特殊字符处理 */
.so-ellipsis-text {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
}

.so-right {
  flex-shrink: 0; /* 防止右侧内容被压缩 */
  white-space: nowrap; /* 保持时间格式不换行 */
}

.content-wrapper {
  width: 100%;
  overflow: hidden; /* 防止内容溢出 */
}
</style>
