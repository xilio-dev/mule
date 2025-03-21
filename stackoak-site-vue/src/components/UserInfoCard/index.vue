<script setup lang="ts">
const props = defineProps<{
  articleCount?: number
  likeCount?: number
  fansCount?: number
  collectCount?: number
  isFollow?: boolean
  userId?: string
  nickname?: string
  jobTitle?: boolean
}>()
const emit = defineEmits(['toggleFollow', 'onChat'])
//关注和取消关注
const toggleFollow = () => {
  emit('toggleFollow')
}
//私信作者
const toChat = () => {
  emit('onChat')
}
</script>

<template>
  <a-row>
    <RouterLink :to="`/author/${userId}`">
      <span style="font-size: 17px;color: black">{{ nickname }}</span>
    </RouterLink>
  </a-row>
  <a-row>
    <span style="font-size: 13px">{{ jobTitle }}</span>
  </a-row>
  <a-row>
  </a-row>
  <a-flex :gap="6" justify="space-around" style="margin-top: 15px;" align="center">
    <a-flex vertical align="center">
      <span>{{ articleCount }}</span>
      <span>文章</span>
    </a-flex>
    <a-flex vertical align="center">
      <span>{{ likeCount }}</span>
      <span>获赞</span>
    </a-flex>
    <a-flex vertical align="center">
      <span>{{ fansCount }}</span>
      <span>粉丝</span>
    </a-flex>
    <a-flex vertical align="center">
      <span>{{ collectCount }}</span>
      <span>收藏</span>
    </a-flex>
  </a-flex>
  <a-row :gutter="10" style="margin-top: 15px;text-align: center">
    <a-col :span="12">
      <a-button @click="toChat" type="default" style="width: 100%;">私信</a-button>
    </a-col>
    <a-col :span="12">
      <a-button @click="toggleFollow" :type="!isFollow?'primary':'default'" style="width: 100%;">
        {{ isFollow ? '已关注' : '关注' }}
      </a-button>
    </a-col>
  </a-row>
</template>

<style scoped>

</style>
