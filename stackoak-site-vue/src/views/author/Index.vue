<script setup lang="ts">

import ColumnList from "@/components/ColumnList.vue";
import {computed, onMounted, reactive, ref} from "vue";
import {getFans, getFollows, getUserDetailInfo, unFollowUser} from "@/api/user.ts";
import {useRoute} from "vue-router";
import {useUserStore} from "@/store";
import {message} from "ant-design-vue";
import {columnLists} from "@/api/column.ts";
import {authorArticleList, authorArticleRank, getAuthorHotArticleList} from "@/api/post.ts";
import ArticleList from "@/components/ArticleList.vue";
import {CommonUtil} from "@/utils/common.ts";
/*------------------------------------变量定义------------------------------------------*/
const activeKey = ref('1')
const collectActiveKey = ref('1')
const route = useRoute()
const userStore = useUserStore()
const authorId = route.params.userId
const authorInfo = ref({})
const columns = ref([])
const articleRank = ref([])
const authorFansList = ref([])
const authorFollowList = ref([])
const openDrawer = ref(false)
const authorArticles = reactive([])
const authorHotArticles = reactive([])/*作者热门文章*/

//判断是否是自己本人
const isSelf = computed(() => {
  return userStore.userinfo.userId == authorId
})
const authorHotArticleQuery = reactive({
  current: 1,
  size: 6,
  id: authorId
})
/*------------------------------------生命周期-------------------------------------------*/
onMounted(async () => {
  await loadAuthorInfo()
  await loadFollows()
  await loadFans()
  await loadUserColumns()
  // await loadAuthorArticleRank()
  await loadAuthorArticles()
  await loadAuthorHotArticles()
})

/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
//加载作者关注的人
const loadFollows = async () => {
  getFollows({current: 1, size: 10, authorId: authorId}).then(res => {
    authorFollowList.value = res.records
  })
}
//加载作者的粉丝
const loadFans = async () => {
  getFans({current: 1, size: 10, authorId: authorId}).then(res => {
    authorFansList.value = res.records
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
//加载作者已经发布的文章
const loadAuthorArticles = async () => {
  const res = await authorArticleList({current: 1, size: 100, id: authorId})
  Object.assign(authorArticles, res.records)
}
//加载作者热门文章
const loadAuthorHotArticles = async () => {
  const res = await getAuthorHotArticleList(authorHotArticleQuery)
  Object.assign(authorHotArticles, res.records)
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
//更换作者热门文章
const onChangeHotArticle = () => {
  authorHotArticleQuery.current = authorHotArticleQuery.current + 1
  loadAuthorHotArticles()
}

/*-------------------------------------其他函数-------------------------------------------*/

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

        <a-card :bordered=false title="热门文章" style=" box-shadow: none;margin-top: 15px ">
          <template #extra>
            <span @click="onChangeHotArticle" class="change-label">换一换</span>
          </template>
          <div v-for="item in authorHotArticles" :key="item.id">
            <a-flex :gap="2" justify="space-between" >
              <div class="article-title  " @click="CommonUtil.openNewPage(`/post/${item.id}`)">
                {{ item.title }}
              </div>
              <a-flex align="center">
                <svg t="1742654875404" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3944" width="16" height="16"><path d="M710.287 320.707c-87.785 26.446-103.857 101.388-98.198 148.99-62.617-77.737-60.05-167.163-60.05-295.997C351.224 253.674 397.88 484.262 391.9 554.283c-50.513-43.664-60.055-147.987-60.055-147.987-53.328 28.965-80.076 106.364-80.076 169.138 0 151.81 116.513 274.867 260.23 274.867 143.714 0 260.23-123.059 260.23-274.867 0-90.22-62.713-131.85-61.957-254.727h0.015m0 0" p-id="3945" fill="#515151"></path></svg>
                <span>{{ item.heat }}</span>
              </a-flex>
            </a-flex>
          </div>
        </a-card>
      </a-affix>
    </a-col>
    <a-col :span="18" style="background-color: white">
      <div style="margin-left: 15px">
        <a-tabs v-model:activeKey="activeKey">
          <a-tab-pane key="1" tab="文章" force-render>
            <ArticleList v-if="userStore.isLogin()&&authorArticles.length>0" :article-list="authorArticles"/>
          </a-tab-pane>
          <a-tab-pane key="2" tab="合集" force-render>
            <ColumnList v-model:column-list="columns" :user-id="authorId"/>
          </a-tab-pane>
          <a-tab-pane key="3" tab="粉丝">
            <a-list item-layout="horizontal" :data-source="authorFansList" :split="false">
              <template #renderItem="{ item }">
                <a-list-item>
                  <template #actions>
                    <a>{{ item.relation == 1 ? '互相关注' : '关注' }}</a>
                  </template>
                  <a-list-item-meta>
                    <template #title>
                      <a href="https://www.antdv.com/">{{ item.nickname }}</a>
                    </template>
                    <template #description>
                      <span class="no-wrap">{{ item.introduce }}</span>
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
                    <a>{{ item.relation == 1 ? '互相关注' : '关注' }}</a>
                  </template>
                  <a-list-item-meta>
                    <template #title>
                      <a href="https://www.antdv.com/">{{ item.nickname }}</a>
                    </template>
                    <template #description>
                      <span class="no-wrap">{{ item.introduce }}</span>
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
            <a-tabs v-model:activeKey="collectActiveKey">
              <a-tab-pane key="1" tab="我创建的">
                我创建的
              </a-tab-pane>
              <a-tab-pane key="2" tab="我关注的">

              </a-tab-pane>
              <template #rightExtra>
                <div style="margin-right: 10px">
                  <a-button type="primary" size="small">新建收藏夹</a-button>
                </div>
              </template>
            </a-tabs>
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

:deep(.ant-card .ant-card-body) {
  padding: 8px 12px;
  border-radius: 0 0 8px 8px;
}

.article-title {
  white-space: nowrap; /* 强制单行显示 */
  overflow: hidden; /* 隐藏超出内容 */
  text-overflow: ellipsis; /* 超出部分显示... */
  word-break: break-all;
  transition: all 0.3s ease; /* 添加过渡动画 */
  margin-bottom: 8px
}

.article-title:hover {
  cursor: pointer;
}

.article-title:last-child {
  margin-bottom: 0; /* 最后一项移除下间距 */
}

.change-label {
  cursor: pointer;
  color: gray;
}

.change-label:hover {
  color: #4c4ce6;
}

</style>
