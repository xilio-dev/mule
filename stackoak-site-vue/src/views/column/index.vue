<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import UserInfoCard from '@/components/UserInfoCard/index.vue'
import {useUserStore} from "@/store";
import router from "@/router";
import {getAuthorColumnDetail} from "@/api/column.ts";
import {useRoute} from "vue-router";
import {ImageUtils} from "@/utils/file.ts";
/*------------------------------------类型定义---------------------------------------------*/
interface IAnalyse {
  subTotalCount: string;
  articleTotalCount: string;
  viewTotalCount: string;
}
/*------------------------------------变量定义------------------------------------------*/
const userStore = useUserStore()
const column = reactive({})
const userInfo = reactive({})
const analyse = reactive<IAnalyse>({
  subTotalCount: '',
  articleTotalCount: '',
  viewTotalCount: '',
});
const articleList = reactive<any[]>([]); // 专栏文章
const route = useRoute()
const cid = route.query.cid as string;

/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadColumnInfo()
})
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
const loadColumnArticleList = () => {

}


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
          dd
        </a-card>
      </a-flex>
    </a-col>
  </a-row>

</template>

<style scoped>

</style>
