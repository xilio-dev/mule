<script setup lang="ts">
import {NumberUtils} from "../../utils/number-util.ts";

const emit = defineEmits(['toggle-follow','on-chat'])
const props = defineProps<{
  userInfo: UserInfo
}>()

interface UserInfo {
  articleCount: number
  gotLikeCount: number
  fansCount: number
  gotCollectCount: number
  isFollow: boolean
  userId: string
  nickname: string
  jobTitle: string
  avatar: string
}


//关注和取消关注
const onToggleFollow = () => {
  emit('toggle-follow', !props.userInfo.isFollow);

}
//私信作者
const toChat = () => {
  emit('on-chat')
}
</script>

<template>
  <a-row style="text-align: left;width: 100%">
    <a-col :span="6">
      <RouterLink :to="`/author/${userInfo.userId}`" target="_blank">
        <a-avatar :size="50" :src="userInfo.avatar"/>
      </RouterLink>
    </a-col>
    <a-col :span="18" style="padding-left: 7px">
      <a-row>
        <RouterLink :to="`/author/${userInfo.userId}`">
          <span style="font-size: 17px;color: black">{{ userInfo.nickname }}</span>
        </RouterLink>
      </a-row>
      <a-row>
        <span style="font-size: 13px">{{ userInfo.jobTitle}}</span>
      </a-row>
    </a-col>
  </a-row>
  <a-flex :gap="6" justify="space-around" style="margin-top: 15px;" align="center">
    <a-flex vertical align="center">
      <span>{{ NumberUtils.formatNumber(userInfo.articleCount)||0 }}</span>
      <span>文章</span>
    </a-flex>
    <a-flex vertical align="center">
      <span>{{ NumberUtils.formatNumber(userInfo.gotLikeCount) ||0}}</span>
      <span>获赞</span>
    </a-flex>
    <a-flex vertical align="center">
      <span>{{ NumberUtils.formatNumber(userInfo.fansCount)||0 }}</span>
      <span>粉丝</span>
    </a-flex>
    <a-flex vertical align="center">
      <span>{{ NumberUtils.formatNumber(userInfo.gotCollectCount)||0 }}</span>
      <span>收藏</span>
    </a-flex>
  </a-flex>
  <a-row :gutter="10" style="margin-top: 15px;text-align: center">
    <a-col :span="12">
      <a-button @click="toChat" type="default" style="width: 100%;">私信</a-button>
    </a-col>
    <a-col :span="12">
      <a-button @click="onToggleFollow" :type="!userInfo.isFollow?'primary':'default'" style="width: 100%;">
        {{ userInfo.isFollow ? '已关注' : '关注' }}
      </a-button>
    </a-col>
  </a-row>
</template>

<style scoped>

</style>
