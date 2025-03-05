<script setup lang="ts">
import {ref} from "vue";

const options = [...Array(25)].map((_, i) => ({value: (i + 10).toString(36) + (i + 1)}));
const value2 = ref(['接收']);
</script>

<template>
  <a-card>
    <a-flex justify="space-between" align="center">
      <a-flex vertical>
        <div class="msg-type-name">评论消息</div>
        <div class="desc-label">有人对我的内容进行评论时，我会收到消息提醒</div>
      </a-flex>
      <a-select
          v-model:value="value2"
          :options="options"
          size="small"
          placeholder="Please select"
          style="width: 100px"
      ></a-select>
    </a-flex>
    <a-divider/>
    <a-flex justify="space-between" align="center">
      <a-flex vertical>
        <div class="msg-type-name">新增粉丝</div>
        <div class="desc-label">有人关注我后会收到消息提醒</div>
      </a-flex>
      <a-select
          v-model:value="value2"
          :options="options"
          size="small"
          placeholder="Please select"
          style="width: 100px"
      ></a-select>
    </a-flex>
    <a-divider/>
    <a-flex justify="space-between" align="center">
      <a-flex vertical>
        <div class="msg-type-name">赞和收藏</div>
        <div class="desc-label">有人对我表达的内容表示赞同时，我会收到消息提醒</div>
      </a-flex>
      <a-select
          v-model:value="value2"
          :options="options"
          size="small"
          placeholder="Please select"
          style="width: 100px"
      ></a-select>
    </a-flex>
    <a-divider/>
    <a-flex justify="space-between" align="center">
      <a-flex vertical>
        <div class="msg-type-name">陌生人私信</div>
        <div class="desc-label">开启后陌生人可以私信我【所有人、我关注的人、互相关注的人、粉丝、关闭】</div>
      </a-flex>
      <a-select
          v-model:value="value2"
          :options="options"
          size="small"
          placeholder="Please select"
          style="width: 100px"
      ></a-select>
    </a-flex>
    <a-divider/>
  </a-card>
</template>

<style scoped>
.msg-type-name {
  font-size: 15px;
}

.desc-label {
  font-size: 13px;
  color: #777888;
}
</style>
