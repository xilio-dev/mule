<script setup lang="ts">
import {NumberUtils} from "../../utils/number-util.ts";
import {computed} from "vue";
import {useUserStore} from "@/store";
import {ImageUtils} from "@/utils/file.ts";

const useStore = useUserStore()
const emit = defineEmits(['toggle-follow', 'on-chat'])
const props = defineProps<{
  userInfo: UserInfo,
  isLoading: boolean
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

//判断是否是当前作者用户
const isSelf = computed(() => {
  return props.userInfo.userId == useStore.userinfo.userId
})
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
  <a-skeleton v-if="isLoading" avatar :paragraph="{ rows: 3 }"/>
  <a-row :class="{ 'hidden-until-mounted': isLoading }" style="text-align: left;width: 100%" v-else>
    <a-col :span="6">
      <RouterLink :to="`/author/${userInfo.userId}`" target="_blank">
        <a-avatar :size="50" :src="ImageUtils.getImgUrl(userInfo.avatar)"/>
      </RouterLink>
    </a-col>
    <a-col :span="18" style="padding-left: 7px">
      <a-row>
        <RouterLink :to="`/author/${userInfo.userId}`">
          <span style="font-size: 17px;color: black">{{ userInfo.nickname }}</span>
        </RouterLink>
      </a-row>
      <a-row>
        <span style="font-size: 13px">{{ userInfo.jobTitle }}</span>
      </a-row>
    </a-col>
  </a-row>
  <a-flex :class="{ 'hidden-until-mounted': isLoading }" :gap="6" justify="space-around" style="margin-top: 15px;"
          align="center">
    <a-flex vertical align="center">
      <span>{{ NumberUtils.formatNumber(userInfo.articleCount) || 0 }}</span>
      <span class="title-label">文章</span>
    </a-flex>
    <a-flex vertical align="center">
      <span>{{ NumberUtils.formatNumber(userInfo.gotLikeCount) || 0 }}</span>
      <span class="title-label">获赞</span>
    </a-flex>
    <a-flex vertical align="center">
      <span>{{ NumberUtils.formatNumber(userInfo.fansCount) || 0 }}</span>
      <span class="title-label">粉丝</span>
    </a-flex>
    <a-flex vertical align="center">
      <span>{{ NumberUtils.formatNumber(userInfo.gotCollectCount) || 0 }}</span>
      <span class="title-label">收藏</span>
    </a-flex>
  </a-flex>
  <a-row v-if="!isSelf" :class="{ 'hidden-until-mounted': isLoading }" :gutter="10"
         style="margin-top: 15px;text-align: center">
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
.title-label {
  color: rgb(138, 145, 159)
}

/* 在 Vue 挂载完成前隐藏内容 */
.hidden-until-mounted {
  visibility: hidden;
}
</style>
