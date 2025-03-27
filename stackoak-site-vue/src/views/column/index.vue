<script setup lang="ts">
import {computed, onMounted, onUnmounted, reactive, ref} from 'vue';
import UserInfoCard from '@/components/UserInfoCard/index.vue';
import ArticleList from '@/components/ArticleList.vue';
import {useUserStore} from '@/store';
import {useRoute, useRouter} from 'vue-router';
import {getAuthorColumnDetail} from '@/api/column';
import {getColumnPublishArticle} from '@/api/post';
import {ImageUtils} from '@/utils/file';
import {message} from 'ant-design-vue';
import {followUser, unFollowUser} from "@/api/user.ts";

/*------------------------------------类型定义---------------------------------------------*/
interface IAnalyse {
  isFollowColumn: boolean,
  isFollowAuthor: boolean,
  subTotalCount: number;
  articleTotalCount: number;
  viewTotalCount: number;
}

/*------------------------------------变量定义------------------------------------------*/
const userStore = useUserStore();
const route = useRoute();
const router = useRouter();
const cid = route.query.cid as string;

const column = reactive({});
const userInfo = reactive({});
const analyse = reactive<IAnalyse>({
  isFollowColumn: false,
  isFollowAuthor: false,
  subTotalCount: 0,
  articleTotalCount: 0,
  viewTotalCount: 0,
});
const articleList = reactive<any[]>([]);

const columnArticleQuery = reactive({
  total: 0,
  current: 1,
  size: 10,
  id: '',
});
const maxLoadedPage = ref(0);
const loading = ref(false);
const lastScrollTop = ref(0);
const hasMore = computed(() => articleList.length < columnArticleQuery.total);

/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadColumnInfo();
  loadColumnArticleList(false); // 仅加载第一页
  window.addEventListener('scroll', debounce(handleScroll, 200));
});

onUnmounted(() => {
  window.removeEventListener('scroll', debounce(handleScroll, 200));
});

/*------------------------------------数据加载--------------------------------------------*/
const loadColumnInfo = () => {
  getAuthorColumnDetail(cid).then((res) => {
    Object.assign(column, res.column);
    Object.assign(userInfo, {...res.userInfo, userId: res.userInfo.id});
    Object.assign(analyse, {
      isFollowColumn: res.isFollowColumn,
      isFollowAuthor: res.isFollowAuthor,
      subTotalCount: res.subTotalCount,
      articleTotalCount: res.articleTotalCount,
      viewTotalCount: res.viewTotalCount,
    });
  });
};

const loadColumnArticleList = async (append = true) => {
  if (loading.value || (!hasMore.value && append)) return;
  if (append && columnArticleQuery.current <= maxLoadedPage.value) {
    return;
  }
  loading.value = true;
  const res = await getColumnPublishArticle({...columnArticleQuery, id: cid});
  if (res && res.records) {
    try {
      const enhancedArticles = res.records.map((article: any) => ({
        ...article,
        ...userInfo,
        userId: userInfo.id,
      }));
      if (append) {
        articleList.push(...enhancedArticles);
        columnArticleQuery.current += 1;
        maxLoadedPage.value = Math.max(maxLoadedPage.value, columnArticleQuery.current - 1);
      } else {
        articleList.length = 0;
        articleList.push(...enhancedArticles);
        columnArticleQuery.current = 2;
        columnArticleQuery.total = res.total || 0;
        maxLoadedPage.value = 1;
      }
    } finally {
      loading.value = false;
    }
  }
};

/*------------------------------------滚动加载--------------------------------------------*/
const debounce = (fn: Function, delay: number) => {
  let timeout: number;
  return (...args: any[]) => {
    clearTimeout(timeout);
    timeout = setTimeout(() => fn(...args), delay);
  };
};

const handleScroll = () => {
  const scrollTop = window.scrollY || document.documentElement.scrollTop;
  const windowHeight = window.innerHeight;
  const documentHeight = document.documentElement.scrollHeight;

  const isScrollingDown = scrollTop > lastScrollTop.value;
  lastScrollTop.value = scrollTop;

  if (
      isScrollingDown &&
      scrollTop + windowHeight >= documentHeight - 100 &&
      !loading.value &&
      hasMore.value &&
      columnArticleQuery.current > maxLoadedPage.value
  ) {
    loadColumnArticleList(true);
  }
};

/*------------------------------------核心业务--------------------------------------------*/
const onSubscribeToColumn = () => {
  if (!userStore.isLogin()) {
    router.push({path: '/login'});
  }
};
//关注和取消关注
const onToggleFollow = async (isFollow: boolean) => {
  userInfo.isFollow = isFollow/*切换关注状态*/
  userInfo.fansCount = isFollow ? userInfo.fansCount + 1 : userInfo.fansCount - 1/*粉丝数变更*/
  //已经关注，取消关注
  if (isFollow) {
    await unFollowUser(userInfo.userId)
  } else {
    //如果未关注，执行关注
    await followUser(userInfo.userId)
  }
}
</script>

<template>
  <a-row :gutter="20" style="width: 100%;">
    <a-col :span="6">
      <a-card>
        <UserInfoCard :is-follow="analyse.isFollowAuthor" :user-info="userInfo" @toggleFollow="onToggleFollow"/>
      </a-card>
    </a-col>
    <a-col :span="18">
      <a-flex vertical :gap="15">
        <a-card>
          <a-flex :gap="15">
            <div>
              <a-image
                  :preview="false"
                  :src="ImageUtils.getImgUrl(column.cover)"
                  style="height: 80px; width: 150px"
              />
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
                <div v-if="userInfo.id!==userStore.userinfo.userId">
                  <a-button @click="onSubscribeToColumn" type="primary">订阅专栏</a-button>
                </div>
              </a-flex>
            </a-flex>
          </a-flex>
        </a-card>
        <a-card>
          <ArticleList :article-list="articleList"/>
          <div v-if="loading" style="text-align: center; padding: 20px;">加载中...</div>
          <div v-else-if="!hasMore" style="text-align: center; padding: 20px;">
            没有更多文章了
          </div>
        </a-card>
      </a-flex>
    </a-col>
  </a-row>
</template>

<style scoped></style>
