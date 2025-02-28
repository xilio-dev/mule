<template>

  <a-row :gutter="15">
    <a-col :span="3" class="index-nav index-nav-top  ">
      <a-menu
          id="leftNavMenu"
          v-model:openKeys="openKeys"
          v-model:selected-keys="selectedKeys"
          style=" float: left;border:none;border-radius: 4px;"
          mode="inline"
          :items="items"
          @click="handleClick"
      ></a-menu>
    </a-col>
    <a-col :span="15">
      <a-carousel style="margin-bottom: 10px" :after-change="onChange">
        <img src="https://picsum.photos/1920/1080?random=2"/>
        <img src="https://picsum.photos/1920/1080?random=3"/>
        <img src="https://picsum.photos/1920/1080?random=4"/>
        <img src="https://picsum.photos/1920/1080?random=5"/>
        <img src="https://picsum.photos/1920/1080?random=6"/>
      </a-carousel>

      <a-card style="margin-top: 8px; " class="index-article-card">
        <a-tabs v-model:activeKey="activeKey" @tabClick="onTabClick">
          <a-tab-pane key="1" tab="关注">

          </a-tab-pane>
          <a-tab-pane key="2" tab="推荐" force-render>
            <ArticleList :article-list="articles"/>
          </a-tab-pane>
          <a-tab-pane key="3" tab="最新">
            <ArticleList :article-list="articles"/>
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
      <a-card v-if="false" title="实时交流" :bordered="false" style="margin-top: 8px">
        <div style="text-align: center">
          <a-qrcode
              error-level="H"
              value="https://www.antdv.com"
              icon="https://www.antdv.com/assets/logo.1ef800a8.svg"/>
        </div>

      </a-card>
      <a-card title="推荐关注" :bordered="false" style="margin-top: 12px;min-height: 150px">

      </a-card>
      <!--广告位-->
      <a-card v-for="i in 1" v-if="true" :bordered="false" style="margin-top: 12px;cursor: pointer  ">
        <template #cover>
          <img style="height: 120px;border-radius: 4px" alt="example"
               src="http://localhost:9856/profile/upload/2024_06_13_00_13_IMG_8743.JPG"/>
        </template>
      </a-card>
      <a-card title="阅读排行" :bordered="false" style="margin-top: 12px;min-height: 150px">

      </a-card>
      <a-card title="热门新闻" :bordered="false" style="margin-top: 12px;min-height: 150px">

      </a-card>
      <a-card title="友情链接" :bordered="false" style="margin-top: 12px;min-height: 100px">
        <a-flex wrap="wrap" justify="space-around" align="center" style="margin: 8px" :gap="4">
          <template v-for="item in friendLinks" >
             <a-button  type="dashed">{{item.name}}</a-button>
          </template>
        </a-flex>
      </a-card>
      <!--广告位-->
      <a-card v-for="i in 1" v-if="true" :bordered="false" style="margin-top: 12px;cursor: pointer  ">
        <template #cover>
          <img style="height: 120px;border-radius: 4px" alt="example"
               src="http://localhost:9856/profile/upload/2024_06_13_00_13_IMG_8743.JPG"/>
        </template>
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
            举报邮箱：xiliojubao@163.com
          </div>
          <div>
            公安备案号：11010596030143
          </div>
          <div>
            ©2024-2025 苏州栈木网络有限责任公司
          </div>
          <div>公司域名 stackoak.com</div>
        </a-flex>
      </a-card>

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
  <a-modal width="40%" :footer="null" v-model:open="openLoginModal" title="登陆智简畅享更多权益">
    <Login/>
  </a-modal>
</template>
<script lang="ts" setup>
import {onMounted} from 'vue';
import {ref, reactive, watch} from 'vue';
import {QuestionCircleOutlined} from '@ant-design/icons-vue';
import type {ItemType, MenuProps} from "ant-design-vue";
import {categoryList} from "@/api/category.ts";
import {articleList} from "@/api/post.ts";
import ArticleList from "@/components/ArticleList.vue";
import {useUserStore} from "@/stores/user.ts";

const userStore = useUserStore()
import router from "@/router";
import Login from "@/components/Login.vue";
import {friendLinkList} from "@/api/friendlink.ts";
import {JSX} from "vue/jsx-runtime";

const openLoginModal = ref(false)/*是否打开登陆框*/
const selectedKeys = ref([0]);
const openKeys = ref([0]);
const items: ItemType[] = reactive([]);

//进入创作中心登陆判断处理
const onOpenLoginModel = (to: string) => {
  if (!userStore.isLogin()) {
    //打开登陆框
    openLoginModal.value = true
    return;
  }
  router.push({path: to})
}
const activeKey = ref('3');
const articles = ref([])
const queryParam = ref({
  current: 1,
  size: 15,
  categoryId: 0,
  showType: activeKey.value
})

const onChange = (current: string) => {
  console.log(current);
};

//加载主页文章数据
const loadHomeData = async () => {
  await articleList(queryParam.value).then(res => {
    if (res == null) {
      articles.value = []
      return
    }
    articles.value = res.records
  })
}
//加载左侧菜单
const loadLeftMenu = async () => {
  try {
    await categoryList().then(res => {
      res.forEach((item) => {
        items.push({
          key: item.id,
          label: item.name,
          title: item.name,
        });
      });
    })
  } catch (err) {
  }
}
const friendLinks = ref()
const loadFriendLink = async () => {
  const res = await friendLinkList()
  friendLinks.value = res || []
}
onMounted(async () => {
  await loadLeftMenu()
  await loadHomeData()
  await loadFriendLink()
})
//左侧菜单点击事件
const handleClick: MenuProps['onClick'] = e => {
  let categoryId = e.key;
  queryParam.value.categoryId = categoryId
  //筛选数据
  loadHomeData()
};
const onTabClick = (targetKey: string) => {
  queryParam.value.showType = targetKey
  loadHomeData()
};
watch(openKeys, val => {
  console.log('openKeys', val);
});

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


</style>

