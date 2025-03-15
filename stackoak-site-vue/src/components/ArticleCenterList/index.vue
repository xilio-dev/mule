<script setup lang="ts">
import {ImageUtils} from "@/utils/file.ts";

const props = defineProps<{
  articleList: [];
}>();
const emit = defineEmits(['onCallData', 'onCallComment', 'onCallEdit'])
const onCallData = (item: any) => {
  emit("onCallData", {item: item})
}
const onCallComment = (item: any) => {
  emit("onCallComment", {item: item})
}
const onCallEdit = (item: any) => {
  emit("onCallEdit", {item: item})
}
</script>

<template>
  <div v-for="item in articleList" class="title-container">
    <a-flex :gap="8" style="width: 100%;">
      <div class="cover">
        <a-image style="width: 120px;height: 68px" :src="ImageUtils.getImgUrl(item.cover)" :preview="false"/>
      </div>
      <a-flex style="width: 100%;" vertical justify="space-between">
        <a-flex justify="space-between" :gutter="4">
          <h3 class="so-title so-ellipsis-text">
            {{ item.title }}
          </h3>
          <div class="so-date">
            2022-05-03
          </div>
        </a-flex>
        <div>
          <span :style="{color: true?'green':'red'}">{{ true ? '已发布' : '未通过审核' }}</span>
        </div>
        <a-flex justify="space-between">
          <a-flex :gap="8">
            <span>阅读 20</span>
            <span>收藏 20</span>
            <span>点赞 452</span>
            <span>评论 36545</span>
          </a-flex>
          <a-flex :gap="8">
            <div @click="onCallData(item)">数据</div>
            <div @click="onCallComment(item)">评论</div>
            <div @click="onCallEdit(item)">修改</div>
            <a-dropdown>
              <a class="ant-dropdown-link" @click.prevent>
                更多
                <DownOutlined/>
              </a>
              <template #overlay>
                <a-menu>
                  <a-menu-item>
                    <a href="javascript:;">删除</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a href="javascript:;">分享</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a href="javascript:;">置顶</a>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>

          </a-flex>
        </a-flex>

      </a-flex>

    </a-flex>
    <a-divider/>
  </div>
</template>

<style scoped>
.cover {
  border-radius: 8px;
  background-size: 100%;
}

.so-title {
  flex-shrink: 1;
}

.so-title a {
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%; /* 确保宽度不超过父容器 */
}

.so-date {
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
