<template>

  <a-row :gutter="15">
    <a-col :span="3" class="index-nav index-nav-top  ">
      <a-affix offset-bottom="bottom" :offset-top="59">
        <a-menu
            id="leftNavMenu"
            v-model:openKeys="openKeys"
            v-model:selected-keys="selectedKeys"
            style="float: left;border:none;border-radius: 4px;"
            mode="inline"
            :items="items"
            @click="handleClick"
        ></a-menu>
      </a-affix>
    </a-col>
    <a-col :span="15">
      <div class="video-container">
        <video
            class="background-video"
            autoplay
            loop
            muted
            :src="siteConfigInfo.searchVideoCover"
        >
        </video>
        <div class="search-container">
          <a-popover trigger="click" placement="bottom">
            <template #content>
              <div
                  class="centered-search"
                  :style="{zIndex: 10,width: '600px'}">
                <a-flex
                    justify="space-between"
                    style="padding: 8px 0;">
                  <h4>搜索历史</h4>
                  <a-button :disabled="searchHistory.length<1" size="small" @click="onClearSearchHistory">清空
                  </a-button>
                </a-flex>
                <div :style="{maxHeight: '160px',overflowY: searchHistory.length > 10 ? 'auto' : 'hidden'}">
                  <a-flex :gap="4" vertical style="font-size: 15px">
                    <div
                        v-if="searchHistory.length >0"
                        class="so-no-wrap search-his-item"
                        v-for="item in searchHistory"
                        @click="onHisSearch(item)">
                      <a-flex align="center" :gap="8">
                        <SearchOutlined />
                        <span> {{ item }}</span>
                      </a-flex>
                    </div>
                    <a-empty v-else>
                      <template #description>
                        <span>暂无搜索历史</span>
                      </template>
                    </a-empty>
                  </a-flex>
                </div>
              </div>
            </template>
            <a-input-search
                v-model:value="search_key"
                @search="onSearch"
                @focus="onSearchFocus"
                class="centered-search"
                size="large"
                enter-button
                placeholder="请输入搜索内容"
            />
          </a-popover>
        </div>
      </div>
      <a-card style="margin-top: 8px;box-shadow: none " class="index-article-card" :bordered="false">
        <a-tabs v-model:activeKey="activeKey" @tabClick="onTabClick">
          <a-tab-pane key="1" tab="关注">
            <a-button v-if="!userStore.isLogin()">
              登陆后可看
            </a-button>
            <ArticleList v-else-if="userStore.isLogin()&&authorArticles.length>0" :article-list="authorArticles"/>
            <a-empty v-else/>
          </a-tab-pane>
          <a-tab-pane key="2" tab="推荐" force-render>
            <ArticleList :article-list="recommendArticles"/>
          </a-tab-pane>
          <a-tab-pane key="3" tab="最新">
            <a-skeleton :loading="loading" active size="large" :block="true" :paragraph="{ rows: 5 }">
              <ArticleList :article-list="articles"/>
            </a-skeleton>
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-card title="创作中心" :bordered="false" style="border-radius: 4px;">
        <a-flex justify="space-around" align="center" style="margin-top: 15px">
          <div @click="onOpenLoginModel('/editor')" style="text-decoration: none" class="creator-link-container">
            <div class="creator-btn-write-icon creator-btn-icon"/>
            <p>写文章</p>
          </div>
          <a-tooltip placement="top" title="即将上线">
            <div @click="onOpenLoginModel('/')" style="text-decoration: none" class="creator-link-container">
              <div class="creator-btn-ask-icon creator-btn-icon"/>
              <p>记笔记</p>
            </div>
          </a-tooltip>
        </a-flex>
        <div style="margin:15px">
          <a-button @click="onOpenLoginModel('/creator')" type="primary" style="width: 100%">
            进入创作中心 >
          </a-button>
        </div>
      </a-card>
      <a-card title="推荐关注" :bordered="false" style="margin-top: 12px;min-height: 150px">
        <a-list item-layout="horizontal" :data-source="recommendAuthors" :split="false">
          <template #renderItem="{ item }">
            <a-list-item>
              <template #actions>
                <a @click="onFollowUser(item)">{{ item.isFollow ? '已关注' : '关注' }}</a>
              </template>
              <a-list-item-meta>
                <template #title>
                  <a :href="`/author/${item.id}`" target="_blank">{{ item.nickname }}</a>
                </template>
                <template #description>
                  <span class="so-no-wrap">{{ item.introduce }}</span>
                </template>
                <template #avatar>
                  <router-link :to="`/author/${item.id}`" target="_blank">
                    <a-avatar :src="ImageUtils.getImgUrl(item.avatar)"/>
                  </router-link>
                </template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-card>

      <a-card title="热门榜单" :bordered="false" style="margin-top: 12px;min-height: 150px">
        <template #extra>
          <span @click="onNextPageArticleRank">换一换</span>
        </template>
        <a-list item-layout="horizontal" :data-source="articleRankList" :split="false">
          <template #renderItem="{ item,index }">
            <a-list-item>
              <a-list-item-meta>
                <template #title>
                  <span @click="CommonUtil.openNewPage(`/post/${item.id}`)" class="so-no-wrap rank-title">{{
                      item.title
                    }}</span>
                </template>
                <template #avatar>
                  <span :class="getArticleRankClass(getCurrentIndex(index))">{{ getCurrentIndex(index) }}</span>
                </template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-card>
      <a-affix offset-bottom="bottom" :offset-top="45">
        <a-card title="热门资讯" :bordered="false" style="margin-top: 12px;min-height: 150px">
          <a-list item-layout="horizontal" :data-source="articleRankList" :split="false">
            <template #renderItem="{ item }">
              <a-list-item>
                <a-list-item-meta>
                  <template #title>
                    <span class="so-no-wrap rank-title">{{ item.title }}</span>
                  </template>
                </a-list-item-meta>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
        <a-card title="友情链接" :bordered="false" style="margin-top: 12px;min-height: 100px">
          <a-flex wrap="wrap" justify="space-around" align="center" style="margin: 8px" :gap="4">
            <template v-for="item in friendLinks">
              <a-button type="dashed">{{ item.name }}</a-button>
            </template>
          </a-flex>
        </a-card>
        <a-card :bordered="false" style="margin-top: 12px;width: 100%;background-color: transparent;box-shadow: none">
          <a-flex gap="small" vertical style="color: #9aa3ab;">
            <a-flex gap="small" justify="start" align="center">
              <div>用户协议</div>
              <div>营业执照</div>
              <div>关于我们</div>
              <div>商务合作</div>
            </a-flex>
            <a-flex gap="small" justify="start" align="center">
              <div>版权申诉</div>
              <div>版权与免责声明</div>
            </a-flex>
            <div>
              举报邮箱：{{ siteConfigInfo.reportMail }}
            </div>
            <div>
              公安备案号：{{ siteConfigInfo.securityRecord }}
            </div>
            <div>
              {{ siteConfigInfo.copyright }} {{ siteConfigInfo.companyName }}
            </div>
            <div>网站域名 {{ siteConfigInfo.domainName }}</div>
          </a-flex>
        </a-card>
      </a-affix>
    </a-col>
  </a-row>
  <a-float-button-group shape="circle" :style="{ right: '24px' }">
    <a-float-button>
      <template #icon>
        <QuestionCircleOutlined/>
      </template>
    </a-float-button>
    <a-float-button/>
    <a-back-top/>
  </a-float-button-group>
  <a-modal width="40%" :footer="null" v-model:open="openLoginModal" title="登陆StackOak">
    <Login/>
  </a-modal>

  <a-row>
    <a-drawer width="45%" :mask="false" :closable="true" placement="right"
              :open="openCategoryDrawer" @close="openCategoryDrawer=false">

      <div style="display: grid; grid-template-columns: repeat(3, 1fr); align-items: start;">
        <div v-for="category in categoryTree" :key="category.id">
          <h3 @click="onSelectCategory(category)">{{ category.name }}</h3>
          <ul>
            <li v-for="sub in category.children" :key="sub.name">
              <a @click="onSelectCategory(sub)">{{ sub.name }}</a>
            </li>
          </ul>
        </div>
      </div>

    </a-drawer>
  </a-row>
</template>
<script lang="ts" setup>
import {onMounted} from 'vue';
import {ref, reactive, watch} from 'vue';
import {QuestionCircleOutlined} from '@ant-design/icons-vue';
import {type ItemType, type MenuProps, message} from "ant-design-vue";
import {categoryList, twoLevelCategoryTree} from "@/api/category.ts";
import {articleList, getFollowAuthorArticles} from "@/api/post.ts";
import ArticleList from "@/components/ArticleList.vue";
import {useUserStore} from '@/store';
import {SearchOutlined} from '@ant-design/icons-vue';
import router from "@/router";
import Login from "@/components/Login.vue";
import {friendLinkList} from "@/api/friendlink.ts";
import {getSearchHistory} from "@/api/search.ts";
import {getSysConfigInfo} from "@/api/config.ts";
import {CommonUtil} from "@/utils/common.ts";
import {getArticleRecommend} from "@/api/recommend.ts";
import {API} from "@/api/ApiConfig.ts";
import {Https} from "@/api/https.ts";
import {ImageUtils} from "@/utils/file.ts";
import {followUser, unFollowUser} from "@/api/user.ts";
/*------------------------------------变量定义------------------------------------------*/
const userStore = useUserStore()
const categoryTree = ref([])
const authorArticles = reactive([])
const articleRankList = reactive([])
const articleRankTotalPage = ref(0)
const loading = ref(true)/*数据加载中*/
const openLoginModal = ref(false)/*是否打开登陆框*/
const selectedKeys = ref(['0']);
const openKeys = ref(['0']);
const items: ItemType[] = reactive([]);
const openCategoryDrawer = ref(false)
const friendLinks = ref()
//获取用户搜索历史
const searchHistory = ref()
const siteConfigInfo = ref({})
//搜索
const search_key = ref('')
const list = ref([]);
const articleRankPageQuery = reactive({
  current: 1,
  size: 5
})
const activeKey = ref('2');
const articles = ref([])
const recommendArticles = reactive([])/*推荐的文章列表*/
const recommendAuthors = reactive([])/*推荐的作者列表*/
const queryParam = ref({
  current: 1,
  size: 20,
  categoryId: '0',
  showType: activeKey.value
})
/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadLeftMenu()
  loadHomeData()
  loadFriendLink()
  loadSiteConfigInfo()

  loadTwoLevelCategoryTree()
  loadArticleRecommend()
  loadFollowAuthorArticles()
  loadArticleComprehensiveRank()
  loadRecommendAuthor()
})
/*------------------------------------数据加载--------------------------------------------*/
const loadTwoLevelCategoryTree = () => {
  twoLevelCategoryTree().then(res => {
    //@ts-ignore
    categoryTree.value = res
  })
}
//加载用户关注作者的文章
const loadFollowAuthorArticles = async () => {
  if (userStore.isLogin()) {
    const res = await getFollowAuthorArticles(queryParam.value)
    //@ts-ignore
    authorArticles.splice(0, authorArticles.length, ...(res.records ?? []))
  }
}

//加载文章综合排名
const loadArticleComprehensiveRank = async () => {
  const res = await Https.action(API.ARTICLE.articleComprehensiveRank, articleRankPageQuery);
  //const res = await articleComprehensiveRank(articleRankPageQuery)
  if (res) {
    Object.assign(articleRankList, res.records)
    articleRankTotalPage.value = res.pages
  }
}
//加载主页文章数据
const loadHomeData = async () => {
  loading.value = true
  try {
    await articleList(queryParam.value).then(res => {
      if (res == null) {
        articles.value = []
        return
      }
      articles.value = res.records
    })
    loading.value = false
  } finally {
    loading.value = false
  }
}
//加载推荐文章
const loadArticleRecommend = async () => {
  const res = await getArticleRecommend({current: 0, size: 20})
  //@ts-ignore
  recommendArticles.splice(0, recommendArticles.length, ...(res.records ?? []))
}
//加载为用户推荐的作者
const loadRecommendAuthor = async () => {
  const res = await Https.action(API.RECOMMEND.author, {current: 0, size: 6})
  if (res) {
    //@ts-ignore
    recommendAuthors.splice(0, recommendAuthors.length, ...(res.records ?? []))
  }
}
//加载左侧菜单
const loadLeftMenu = async () => {
  try {
    items.push({
      key: '0',
      label: '综合',
      title: '综合',
    });
    await categoryList().then(res => {
      res.forEach((item) => {
        items.push({
          key: item.id,
          label: item.name,
          title: item.name,
        });
      });
    })
    items.push({
      key: '99',
      label: '更多分类',
      title: '更多分类',
    });
  } catch (err) {
  }
}
const loadFriendLink = async () => {
  const res = await friendLinkList()
  friendLinks.value = res || []
}

const loadSiteConfigInfo = async () => {
  const res = await getSysConfigInfo()
  if (res) {
    siteConfigInfo.value = res || {}
  }
}
/*------------------------------------核心业务--------------------------------------------*/
const onNextPageArticleRank = () => {
  // 如果没有下一页，直接返回
  if (articleRankPageQuery.current >= articleRankTotalPage.value) {
    return;
  }
  articleRankPageQuery.current += 1
  loadArticleComprehensiveRank()
}
// 计算当前序号
const getCurrentIndex = (index: number) => {
  return (articleRankPageQuery.current - 1) * articleRankPageQuery.size + index + 1;
};
// 根据索引动态设置类名
const getArticleRankClass = (index: number) => {
  if (index <= 3) {
    return `article-rank-color-${index}`; // 前三个页码分别设置不同颜色
  } else {
    return 'article-rank-color-default'; // 后面的页码统一颜色
  }
};
//左侧菜单点击事件
const handleClick: MenuProps['onClick'] = e => {
  let categoryId = e.key;
  //打开更多分类
  if ('99' == e.key) {
    if (openCategoryDrawer.value == true) {
      openCategoryDrawer.value = false
      return
    }
    openCategoryDrawer.value = true
  } else {
    openCategoryDrawer.value = false
    queryParam.value.categoryId = categoryId
    //筛选数据
    loadHomeData()
  }
};
const onSelectCategory = (cat: any) => {
  message.info(JSON.stringify(cat))
}
const onChange = (current: string) => {
  console.log(current);
};
const onSearchFocus = async () => {
  if (userStore.isLogin()) {
    const res = await getSearchHistory()
    searchHistory.value = res || []
  }
}
//通过历史记录进行搜索
const onHisSearch = (keyword: string) => {
  search_key.value = keyword
  onSearch()
}
//进入创作中心登陆判断处理
const onOpenLoginModel = (to: string) => {
  if (!userStore.isLogin()) {
    //打开登陆框
    openLoginModal.value = true
    return;
  }
  router.push({path: to})
}
const onTabClick = (targetKey: string) => {
  queryParam.value.showType = targetKey
  loadHomeData()
};
watch(openKeys, val => {
  console.log('openKeys', val);
});

const onSearch = () => {
  if (search_key.value == '') {
    return;
  }
  router.push({path: '/search', query: {keyword: search_key.value}})
}
//关注推荐的作者
const onFollowUser = async (user: object) => {
  //检查用户是否登陆了
  if (!userStore.isLogin()) {
    //打开登陆框
    openLoginModal.value = true
    return;
  }
  //已经关注，取消关注
  if (user.isFollow) {
    await unFollowUser(user.id)
  } else {
    //如果未关注，执行关注
    await followUser(user.id)
  }
  user.isFollow = !user.isFollow/*切换关注状态*/
}
//清空搜索历史
const onClearSearchHistory = async () => {
  await Https.action(API.SEARCH_HISTORY.delSearchHis)
  searchHistory.value = []
}
/*-------------------------------------其他函数-------------------------------------------*/

</script>
<style scoped>

:deep(.slick-slide) {
  text-align: center;
  height: 100px;
  line-height: 100px;
  background: #364d79;
  overflow: hidden;
  border-radius: 4px;

}

:deep(.slick-slide h3) {
  color: #fff;
}


:deep(.ant-list .ant-list-item) {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 6px 14px;
  color: rgba(0, 0, 0, 0.88);
}


:deep(.ant-card .ant-card-body) {
  padding: 2px;
  border-radius: 0 0 8px 8px;
}

:deep(.ant-tabs-top >.ant-tabs-nav), :deep(.ant-tabs-bottom >.ant-tabs-nav), :deep(.ant-tabs-top >div>.ant-tabs-nav), :deep(.ant-tabs-bottom >div>.ant-tabs-nav) {
  margin: 0;
}

:deep(.ant-tabs >.ant-tabs-nav .ant-tabs-nav-list), :deep(.ant-tabs >div>.ant-tabs-nav .ant-tabs-nav-list) {
  position: relative;
  display: flex;
  transition: opacity 0.3s;
  margin-left: 20px;
}


/*创作中心*/

.creator-link-container {
  display: block;
  text-align: center;
  font-size: 12px;
  color: #373a40;
  font-weight: 600;
}

.creator-btn-write-icon {
  background-image: url(@/assets/icon/write_article_128x128.png);
  background-color: #f4c8071a;
  transition: background-color 0.3s ease-in-out; /* 添加过渡效果 */
}

.creator-btn-ask-icon {
  background-image: url(@/assets/icon/ask_question_128x128.png);
  background-color: #1782d31a;
  transition: background-color 0.3s ease-in-out; /* 添加过渡效果 */
}

.creator-btn-write-icon:hover {
  background-color: rgba(138, 115, 14, 0.1);

}

.creator-btn-ask-icon:hover {
  background-color: rgba(21, 68, 103, 0.1);
}

.creator-btn-icon {
  background-position: center;
  background-size: auto 20px;
  background-repeat: no-repeat;
  width: 40px;
  height: 40px;
  border-radius: 6px;
  margin-bottom: 8px;
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

.video-container {
  position: relative;
  width: 100%;
  height: 100px; /* 或根据需要调整高度 */
  background-color: gray;
}

.background-video {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 确保视频填充整个容器 */
}

.search-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 10; /* 确保搜索框在视频上方 */
  width: calc(100% - 100px);
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  padding: 0 30px; /* 确保左右间距 */
}

/*-------------------搜索框定制start--------------------*/
.centered-search {
  width: 100%; /* 搜索框宽度占满容器 */
}

/* 统一设置输入框边框样式 */
.centered-search :deep(.ant-input) {
  border-color: transparent; /* 默认边框颜色 */
  outline: none; /* 去除聚焦边框 */
  box-shadow: none; /* 去除阴影 */
}

/* 悬浮状态 */
.centered-search :deep(.ant-input:hover) {
  border-color: transparent; /* 与默认一致 */
  box-shadow: none;
}

/* 聚焦状态 */
.centered-search :deep(.ant-input:focus) {
  border-color: transparent; /* 与默认一致 */
  box-shadow: none;
}

/* 统一设置按钮边框样式 */
.centered-search :deep(.ant-btn) {
  border-color: transparent;
  outline: none;
  box-shadow: none;
  transition: none;
}

/* 按钮悬浮状态 */
.centered-search :deep(.ant-btn:hover) {
  border-color: transparent;
  box-shadow: none;
}

/* 按钮聚焦状态 */
.centered-search :deep(.ant-btn:focus) {
  border-color: transparent;
  box-shadow: none;
}

/*-------------------搜索框定制end--------------------*/
:deep(.ant-list .ant-list-item .ant-list-item-action) {
  margin-inline-start: 2px;
}



.search-his-item {
  cursor: pointer;
  border-radius: 4px;
}

.search-his-item:hover {
  background-color: #F0F2F5;
  border-radius: 4px;
}

.rank-title {
  cursor: pointer;
  font-weight: 800;
  color: rgb(37, 41, 51);
}

/*排名样式*/
.article-rank-color-1 {
  color: #F56C6C;
  font-weight: 600;
}

.article-rank-color-2 {
  color: #E6A23C;
  font-weight: 600;
}

.article-rank-color-3 {
  color: #67C23A;
  font-weight: 600;
}

.article-rank-color-default {
  font-weight: 600;
  color: #909399
}

/*排名样式*/
</style>

