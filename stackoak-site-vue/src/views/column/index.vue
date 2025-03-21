<script setup lang="ts">
import {computed, onMounted, onUnmounted, reactive, ref} from "vue";
import UserInfoCard from '@/components/UserInfoCard/index.vue'
import {useUserStore} from "@/store";
import router from "@/router";
import {getAuthorColumnDetail} from "@/api/column.ts";
import {useRoute} from "vue-router";
import {ImageUtils} from "@/utils/file.ts";
import {getColumnPublishArticle} from "@/api/post.ts";
import ArticleList from "@/components/ArticleList.vue";
/*------------------------------------类型定义---------------------------------------------*/
interface IAnalyse {
  subTotalCount: string;
  articleTotalCount: string;
  viewTotalCount: string;
}

/*------------------------------------变量定义------------------------------------------*/
const userStore = useUserStore()
const articleList = reactive<any[]>([]); // 专栏文章
const column = reactive({})
const userInfo = reactive({})
const analyse = reactive<IAnalyse>({
  subTotalCount: '',
  articleTotalCount: '',
  viewTotalCount: '',
});

const route = useRoute()
const cid = route.query.cid as string;

const columnArticleQuery = reactive({
  total: 0, // 新增：总记录数，用于判断是否还有更多数据
  current: 1,
  size: 10,
  id: ''
})
const loading = ref(false); // 加载状态
// 修改 hasMore 逻辑，初次加载时允许请求
const isInitialLoad = ref(true); // 新增：标记初次加载
const hasMore = computed(() => {
  if (isInitialLoad.value) return true; // 初次加载时始终允许
  return articleList.length < columnArticleQuery.total;
});
/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadColumnInfo()
  loadColumnArticleList(false)
  window.addEventListener('scroll', handleScroll); // 添加滚动监听
})
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll); // 清理监听
});

/*------------------------------------数据加载--------------------------------------------*/
//加载专栏信息
const loadColumnInfo = () => {
  getAuthorColumnDetail(cid).then(res => {
    Object.assign(column, res.column)
    Object.assign(userInfo, res.userInfo)
    Object.assign(analyse, {
      subTotalCount: res.subTotalCount,
      articleTotalCount: res.articleTotalCount,
      viewTotalCount: res.viewTotalCount
    })
  })
}
//加载专栏文章
const loadColumnArticleList = async (append = true) => {
  if (loading.value || (!hasMore.value && !isInitialLoad.value)) return; // 避免重复加载
  loading.value = true;
  const res = await getColumnPublishArticle({...columnArticleQuery, id: cid})
  if (res && res.records) {
    try {
      // 增强文章数据
      const enhancedArticles = res.records.map((article: any) => ({
        ...article,
        ...userInfo, // 深拷贝用户信息
      }));
      if (append) {
        // 追加数据到 reactive 数组
        articleList.push(...enhancedArticles);
      } else {
        // 清空并重新赋值
        articleList.length = 0;
        articleList.push(...enhancedArticles);
      }
      // 更新分页信息
      columnArticleQuery.total = res.total || 0;
      if (append) columnArticleQuery.current += 1;
    } finally {
      loading.value = false;
      isInitialLoad.value = false; // 初次加载完成后置为 false
    }
  }
}
/*------------------------------------滚动加载--------------------------------------------*/
const handleScroll = () => {
  const scrollTop = window.scrollY || document.documentElement.scrollTop;
  const windowHeight = window.innerHeight;
  const documentHeight = document.documentElement.scrollHeight;

  // 当滚动到底部 100px 时触发加载
  if (scrollTop + windowHeight >= documentHeight - 100 && !loading.value) {
    loadColumnArticleList(true);
  }
};

/*------------------------------------核心业务--------------------------------------------*/
//订阅专栏
const onSubscribeToColumn = () => {
  if (!userStore.isLogin()) {
    //跳转到登录页面
    router.push({path: '/login'})
  }

}
/*------------------------------------ 工具函数 -------------------------------------------*/

</script>

<template>
  <a-row :gutter="20" style="width: 100%;">
    <a-col :span="6">
      <a-card style="height: 205px">
        <UserInfoCard :user-info="userInfo"/>
      </a-card>
    </a-col>
    <a-col :span="18">
      <a-flex vertical :gap="15">
        <a-card>
          <a-flex :gap="15">
            <div>
              <a-image :preview="false" :src="ImageUtils.getImgUrl(column.cover)" style="height: 80px;width: 150px"/>
            </div>
            <a-flex vertical justify="space-between" style="width: 100%;">
              <h2>{{ column.name }}</h2>
              <div>{{ column.intro }}</div>
              <a-flex align="center" justify="space-between">
                <a-flex :gap="15">
                  <div>{{ analyse.articleTotalCount || 0 }}篇文章</div>
                  <div>共{{ analyse.subTotalCount || 0 }}人订阅</div>
                  <div>{{ analyse.viewTotalCount || 0 }}阅读量</div>
                </a-flex>
                <div>
                  <a-button @click="onSubscribeToColumn" type="primary">订阅专栏</a-button>
                </div>
              </a-flex>
            </a-flex>
          </a-flex>
        </a-card>
        <a-card>
          <ArticleList :article-list="articleList"/>
          <div v-if="loading" style="text-align: center; padding: 20px;">
            加载中...
          </div>
          <div v-else-if="!hasMore" style="text-align: center; padding: 20px;">
            没有更多文章了
          </div>
        </a-card>
      </a-flex>
    </a-col>
  </a-row>

</template>

<style scoped>

</style>
