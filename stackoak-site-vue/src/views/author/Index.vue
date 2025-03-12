<script setup lang="ts">
import {
  TwitterOutlined,
  YoutubeOutlined,
  FacebookOutlined,
  LinkedinOutlined,
} from '@ant-design/icons-vue';

import ColumnList from "@/components/ColumnList.vue";
import {computed, onMounted, ref} from "vue";
import {getFans, getFollows, getUserDetailInfo} from "@/api/user.ts";
import {useRoute} from "vue-router";
import {useUserStore} from "@/store";
/*------------------------------------变量定义------------------------------------------*/
const activeKey = ref('1')
const route = useRoute()
const authorId = route.params.userId
const authorInfo = ref({})
const userStore = useUserStore()

//判断是否是自己本人
const isSelf = computed(() => {
  return userStore.userinfo.userId == authorId
})
/*------------------------------------生命周期-------------------------------------------*/
onMounted(async () => {
  await loadAuthorInfo()
  await loadFollows()
  await loadFans()

})

/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
//加载作者关注的人
const loadFollows = async () => {
  const res = getFollows({current: 1, size: 10, authorId: authorId})
}
//加载作者的粉丝
const loadFans = async () => {
  const res = getFans({current: 1, size: 10, authorId: authorId})
}
//加载作者信息
const loadAuthorInfo = async () => {
  const res = await getUserDetailInfo(authorId)
  authorInfo.value = res || {}
}
//加载作者的专栏
const loadUserColumns = async () => {

}

/*------------------------------------核心业务--------------------------------------------*/




/*-------------------------------------其他函数-------------------------------------------*/
//
const onEnterPD = () => {

}


</script>

<template>
  <a-row class="header-container">
    <a-row class="author-info">
      <a-row align="bottom" style="margin-bottom: 30px;width: 100%" :gutter="8">
        <a-col :span="!isSelf?20:24">
          <a-flex :gap="12">
            <div>
              <a-avatar class="author-avatar" :src="authorInfo.avatar" :size="90"/>
            </div>
            <a-flex vertical justify="space-around" style="width: 100%;">
              <div class="author-title">
                {{ authorInfo.nickname }}
                <span v-if="authorInfo.jobTitle" class="author-job">{{ authorInfo.jobTitle }}</span>
              </div>
              <div class="author-analyse">
                <a-flex :gap="12" style="font-size: 18px;">
                  <div>{{ authorInfo.gotViewCount }} 阅读量</div>
                  <div>{{ authorInfo.gotLikeCount }} 获赞</div>
                  <div>{{ authorInfo.fansCount }} 粉丝</div>
                </a-flex>
              </div>
              <div class="author-introduce">
                {{ authorInfo.introduce }}
              </div>
            </a-flex>
          </a-flex>
        </a-col>

        <a-col :span="4" v-if="!isSelf">
          <a-flex :gap="12" align="end" class="operation-btn">
            <a-button type="primary">已关注</a-button>
            <a-button type="default">私信</a-button>
          </a-flex>
        </a-col>
      </a-row>
    </a-row>
  </a-row>
  <a-row style=" width: 100%;  padding-top: 15px;  ">
    <a-col :span="6" style="padding-right: 15px">
      <a-card :bordered=false title="个人成就" style=" box-shadow: none">
        <h3>文章被阅读 1,118,166 </h3>
        <h3>文章被点赞 13,034</h3>
        <h3>内容获得 183 次评论</h3>
        <h3>获得 26321 次收藏</h3>
        <h3>收获 14563 粉丝</h3>
      </a-card>
      <a-card :bordered=false style=" box-shadow: none;margin-top: 15px">
        <a-flex wrap="wrap" gap="small">
          <a-tag color="#55acee">
            <template #icon>
              <twitter-outlined/>
            </template>
            Twitter
          </a-tag>
          <a-tag color="#cd201f">
            <template #icon>
              <youtube-outlined/>
            </template>
            Youtube
          </a-tag>
          <a-tag color="#3b5999">
            <template #icon>
              <facebook-outlined/>
            </template>
            Facebook
          </a-tag>
          <a-tag color="#55acee">
            <template #icon>
              <linkedin-outlined/>
            </template>
            LinkedIn
          </a-tag>
        </a-flex>

      </a-card>
      <a-card :bordered=false title="阅读榜单" style=" box-shadow: none;margin-top: 15px;min-height: 150px">

      </a-card>
    </a-col>
    <a-col :span="18" style="background-color: white">
      <div style="margin-left: 15px">
        <a-tabs v-model:activeKey="activeKey">
          <a-tab-pane key="1" tab="文章" force-render>

          </a-tab-pane>
          <a-tab-pane key="2" tab="合集" force-render>
            <ColumnList/>
          </a-tab-pane>
          <a-tab-pane key="3" tab="粉丝">Content of Tab Pane 3</a-tab-pane>
          <a-tab-pane key="4" tab="关注的人">Content of Tab Pane 3</a-tab-pane>
          <template #rightExtra>
            <a-button @click="onEnterPD" type="link" size="small" style="margin-right: 15px">进入私域</a-button>
          </template>
        </a-tabs>
      </div>
    </a-col>
  </a-row>
</template>

<style scoped>
.header-container {
  height: 200px;
  width: 100%;
  background-size: cover;
  background-image: url("/bg1.jpg");
  background-position: center; /* 背景图片居中 */
  position: relative; /* 为绝对定位的子元素提供参照 */
}

.header-container::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0)); /* 从底部到顶部由暗到亮的渐变 */
  z-index: 1; /* 确保渐变层在背景图片之上，但低于内容 */
}

.author-info {
  width: 100%;
  margin-top: 8%;
  margin-left: 5%;
  margin-right: 5%;
  position: relative; /* 确保内容在渐变层之上 */
  z-index: 2; /* 确保内容在渐变层之上 */
}

.author-avatar {
}

.author-title {
  color: white;
  font-size: 24px;
  font-weight: 700;
  text-shadow: 0 1px 1px rgba(0, 0, 0, .4);
}

.author-analyse {
  color: white;
  font-size: 18px;
  display: inline-block; /* 使背景仅应用于文字 */
}

.author-introduce {
  color: white;
  text-shadow: 0 1px 2px rgba(0, 0, 0, .4);
  display: inline-block; /* 使背景仅应用于文字 */
  -webkit-font-smoothing: antialiased;
  white-space: nowrap; /* 防止文字换行 */
  overflow: hidden; /* 超出部分隐藏 */
  text-overflow: ellipsis; /* 超出部分显示省略号 */
  /* 限制宽度 */
  max-width: calc(100% - 120px); /* 减去 avatar 和 padding 的宽度 */
}

.operation-btn {
  /* 确保操作按钮的宽度固定，避免布局问题 */
  width: 100%;
  justify-content: flex-end;
}

.author-job {
  font-size: 12px;
  color: #ffffff;
}

/* 卡片修改*/
:deep(.ant-card .ant-card-head ) {
  min-height: 40px;
  padding: 0 8px;
}

/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}
</style>
