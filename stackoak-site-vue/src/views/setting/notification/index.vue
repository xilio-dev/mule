<script setup lang="ts">
import {onMounted, ref} from "vue";
import {getNotifySetting, setNotifySetting} from "@/api/notify.ts";
import {message} from "ant-design-vue";

const options = ref([
  {label: '接收', value: true},
  {label: '不接收', value: false}
]);

onMounted(() => {
  loadNotifySet()
})
const notify = ref()
//加载用户的消息配置
const loadNotifySet = () => {
  getNotifySetting().then(res => {
    notify.value = res || {}
  })
}
//更新消息设置
const updateSet = (type: number, enabled: boolean, msg: string) => {
  setNotifySetting({type: type, enabled: enabled}).then(res => {
    message.info(enabled ? `已开启${msg}通知` : `已关闭${msg}通知`)
  })
}
</script>

<template>
  <a-card v-if="notify">
    <a-flex justify="space-between" align="center">
      <a-flex vertical>
        <div class="msg-type-name">评论消息</div>
        <div class="desc-label">有人对我的内容进行评论时，我会收到消息提醒</div>
      </a-flex>
      <a-select
          v-model:value="notify.commentEnabled"
          :options="options"
          @change="updateSet(1,notify.commentEnabled,'评论消息')"
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
          v-model:value="notify.followEnabled"
          :options="options"
          @change="updateSet(3,notify.followEnabled,'新增粉丝')"
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
          v-model:value="notify.likeCollectEnabled"
          :options="options"
          @change="updateSet(2,notify.likeCollectEnabled,'赞和收藏')"
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
          v-model:value="notify.chatEnabled"
          :options="options"
          @change="updateSet(4,notify.chatEnabled,'陌生人私信')"
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
