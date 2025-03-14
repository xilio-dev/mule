<script setup lang="ts">

import ColumnList from "@/components/ColumnList.vue";
import {computed, onMounted, ref} from "vue";
import {getFans, getFollows, getUserDetailInfo, unFollowUser} from "@/api/user.ts";
import {useRoute} from "vue-router";
import {useUserStore} from "@/store";
import {message} from "ant-design-vue";
import {columnLists} from "@/api/column.ts";
import {authorArticleRank} from "@/api/post.ts";
/*------------------------------------变量定义------------------------------------------*/
const activeKey = ref('1')
const route = useRoute()
const userStore = useUserStore()
const authorId = route.params.userId
const authorInfo = ref({})
const columns = ref([])
const articleRank = ref([])
const authorFansList=ref([])
const authorFollowList=ref([])
const openDrawer = ref(false)
//判断是否是自己本人
const isSelf = computed(() => {
  return userStore.userinfo.userId == authorId
})
/*------------------------------------生命周期-------------------------------------------*/
onMounted(async () => {
  await loadAuthorInfo()
  await loadFollows()
  await loadFans()
  await loadUserColumns()
  await loadAuthorArticleRank()

})

/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
//加载作者关注的人
const loadFollows = async () => {
  getFollows({current: 1, size: 10, authorId: authorId}).then(res=>{
    authorFollowList.value=res.records
  })
}
//加载作者的粉丝
const loadFans = async () => {
  getFans({current: 1, size: 10, authorId: authorId}).then(res=>{
    authorFansList.value=res.records
  })
}
//加载作者信息
const loadAuthorInfo = async () => {
  const res = await getUserDetailInfo(authorId)
  authorInfo.value = res || {}
}
//加载作者的专栏
const loadUserColumns = async () => {
  columnLists({current: 1, size: 10, userId: authorId}).then(res => {
    columns.value = res.records
  })
}
//
const loadAuthorArticleRank = async () => {
  authorArticleRank({current: 1, size: 5, authorId: authorId}).then(res => {
    articleRank.value = res.records
  })
}

/*------------------------------------核心业务--------------------------------------------*/
//点击关注或取消关注
const onFollowAuthor = () => {
  //如果没有关注，执行关注
  //  followUser(authorId).then(res=>{
  //    message.success("已关注")
  //  })
  unFollowUser(authorId).then(res => {
    message.success("已取消关注")
  })
  //如果已经关注，执行取消关注

}
//点击私信
const onChat = () => {

}



/*-------------------------------------其他函数-------------------------------------------*/
//
const onEnterPD = () => {

}
const openLink = (url: string) => {
  window.open(url, '_blank')
}
</script>

<template>
  <a-row class="header-container">
    <a-row class="author-info">
      <a-row align="bottom" style="margin-bottom: 30px;width: 100%" :gutter="8">
        <a-col :span="20">
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

        <a-col :span="4">
          <a-flex :gap="12" align="end" class="operation-btn">
            <a-button @click="onFollowAuthor" v-if="!isSelf" type="primary">关注</a-button>
            <a-button @click="onChat" v-if="!isSelf" type="default">私信</a-button>
            <div class="change-theme" v-if="isSelf" @click="openDrawer=true">
            </div>
          </a-flex>
        </a-col>
      </a-row>
    </a-row>
  </a-row>
  <a-row style=" width: 100%;  padding-top: 15px;">
    <a-col :span="6" style="padding-right: 15px">
      <a-card :bordered=false style=" box-shadow: none;margin-bottom: 15px">
        <a-flex wrap="wrap" gap="small" align="center" justify="space-around">
          <a-image @click="openLink(authorInfo.personBlogAddress)" src="/icon/gerenwangzhan32.svg" :preview="false"/>
          <a-image @click="openLink(authorInfo.github)" src="/icon/github32.png" :preview="false"/>
          <a-image @click="openLink(authorInfo.gitee)" src="/icon/gitee32.png" :preview="false"/>
          <a-image @click="openLink(authorInfo.csdn)" src="/icon/csdn32.svg" :preview="false"/>
          <a-image v-if="authorInfo.bilibli" @click="openLink(authorInfo.bilibli)" src="/icon/Bilibili32.svg"
                   :preview="false"/>
          <a-image @click="openLink(authorInfo.bokeyuan)" src="/icon/cnblogs32.svg" :preview="false"/>
          <a-image @click="openLink('#')" src="/icon/jinritoutiao.svg" :preview="false"/>
          <a-image @click="openLink('#')" src="/icon/zhihu.svg" :preview="false"/>
          <a-image @click="openLink('#')" src="/icon/weixin32.png" :preview="false"/>
          <a-image @click="openLink('')" src="/icon/juejin32.svg" :preview="false"/>
        </a-flex>
      </a-card>
      <a-affix offset-bottom="bottom" :offset-top="60">
        <a-card :bordered=false title="个人成就" style=" box-shadow: none;">
          <h3>文章被阅读 1,118,166 </h3>
          <h3>文章被点赞 13,034</h3>
          <h3>内容获得 183 次评论</h3>
          <h3>获得 26321 次收藏</h3>
          <h3>收获 14563 粉丝</h3>
        </a-card>

        <a-card :bordered=false title="阅读榜单" style=" box-shadow: none;margin-top: 15px;min-height: 200px">
          <div v-for="item in articleRank">
            {{item.title}}
          </div>
        </a-card>
      </a-affix>
    </a-col>
    <a-col :span="18" style="background-color: white">
      <div style="margin-left: 15px">
        <a-tabs v-model:activeKey="activeKey">
          <a-tab-pane key="1" tab="文章" force-render>

          </a-tab-pane>
          <a-tab-pane key="2" tab="合集" force-render>
            <ColumnList v-model:column-list="columns"/>
          </a-tab-pane>
          <a-tab-pane key="3" tab="粉丝">
            <a-list item-layout="horizontal" :data-source="authorFansList" :split="false">
              <template #renderItem="{ item }">
                <a-list-item>
                  <template #actions>
                    <a>{{item.relation==1?'互相关注':'关注'}}</a>
                  </template>
                  <a-list-item-meta>
                    <template #title>
                      <a href="https://www.antdv.com/">{{ item.nickname }}</a>
                    </template>
                    <template #description>
                      <span class="no-wrap">{{item.introduce}}</span>
                    </template>
                    <template #avatar>
                      <a-avatar :src="item.avatar"/>
                    </template>
                  </a-list-item-meta>
                </a-list-item>
              </template>
            </a-list>
          </a-tab-pane>
          <a-tab-pane key="4" tab="关注的人">
            <a-list item-layout="horizontal" :data-source="authorFollowList" :split="false">
              <template #renderItem="{ item }">
                <a-list-item>
                  <template #actions>
                    <a>{{item.relation==1?'互相关注':'关注'}}</a>
                  </template>
                  <a-list-item-meta>
                    <template #title>
                      <a href="https://www.antdv.com/">{{ item.nickname }}</a>
                    </template>
                    <template #description>
                      <span class="no-wrap">{{item.introduce}}</span>
                    </template>
                    <template #avatar>
                      <a-avatar :src="item.avatar"/>
                    </template>
                  </a-list-item-meta>
                </a-list-item>
              </template>
            </a-list>
          </a-tab-pane>

          <a-tab-pane key="5" tab="收藏" force-render>

          </a-tab-pane>
        </a-tabs>
      </div>
    </a-col>
  </a-row>
  <!-- 顶部封面选择器 -->
  <a-drawer :closable="false" placement="bottom" v-model:open="openDrawer">
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane key="1" tab="封面">
        封面设置
      </a-tab-pane>
      <a-tab-pane key="2" tab="背景" force-render>
        背景设置
      </a-tab-pane>
    </a-tabs>
  </a-drawer>
</template>

<style scoped>
.header-container {
  height: 200px;
  width: 100%;
  background-size: cover;
  background-image: url("/bg5.jpg");
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

.change-theme {
  cursor: pointer;
  width: 34px;
  height: 34px;
  color: white;
  border-radius: 6px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid rgba(255, 255, 255, .2);
  background-color: rgba(255, 255, 255, .14);
  transition: all .3s;
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
