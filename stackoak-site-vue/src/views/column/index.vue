<script setup lang="ts">
import {ref} from "vue";

const userInfo=ref({
  userId:'',
  nickname:'',
  avatar:''
})
const userInteract=ref({})
</script>

<template>
  <a-row :gutter="20" style="width: 100%;">
    <a-col :span="6">
      <a-card style="height: 205px">
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
              <span style="font-size: 13px">后端开发工程师</span>
            </a-row>
          </a-col>
        </a-row>
        <a-flex :gap="6" justify="space-around" style="margin-top: 15px;" align="center">
          <span>63</span>
          <span>2k</span>
          <span>2w</span>
          <span>300</span>
        </a-flex>
        <a-flex :gap="6" justify="space-around" align="center">
          <span>文章</span>
          <span>获赞</span>
          <span>粉丝</span>
          <span>收藏</span>
        </a-flex>
        <a-row :gutter="10" style="margin-top: 15px;text-align: center">
          <a-col :span="12">
            <a-button @click="ontoChat" type="default" style="width: 100%;">私信</a-button>
          </a-col>
          <a-col :span="12">
            <a-button @click="toggleFollow" :type="!userInteract.isFollow?'primary':'default'" style="width: 100%;">
              {{ userInteract.isFollow ? '已关注' : '关注' }}
            </a-button>
          </a-col>
        </a-row>

      </a-card>
    </a-col>
    <a-col :span="18">
      <a-flex vertical :gap="15">
        <a-card >
          <a-flex :gap="15">
            <div>
              <a-image :preview="false" src="/bg1.jpg"  style="height: 80px;width: 150px"/>
            </div>
            <a-flex vertical justify="space-between" style="width: 100%;">
              <h2>人工智能技术开发</h2>
              <div>主要收入人工智能</div>
              <a-flex  align="center" justify="space-between" >
                <a-flex :gap="15">
                  <div>741篇文章</div>
                  <div>共9636人订阅</div>
                  <div>8415阅读量</div>
                </a-flex>
                <div>
                  <a-button type="primary">订阅专栏</a-button>
                </div>
              </a-flex>

            </a-flex>
          </a-flex>
        </a-card>
        <a-card>
          dd
        </a-card>
      </a-flex>
    </a-col>
  </a-row>

</template>

<style scoped>

</style>
