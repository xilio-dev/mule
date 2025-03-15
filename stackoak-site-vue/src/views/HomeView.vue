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
            src="https://lf3-static.bytednsdoc.com/obj/eden-cn/111eh7nupehpqps/1220下酒菜.mp4"
        >
        </video>
        <div class="search-container">
          <a-popover trigger="click" placement="bottom">
            <template #content>
              <div class="centered-search " style="z-index: 10;height: 200px;width: 600px">
                <div style="cursor: pointer" class="no-wrap" v-for="item in searchHistory" @click="onHisSearch(item)">
                  {{ item }}
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


      <!--        <a-input-search style="margin: 15px" size="large"/>-->


      <!--      <a-carousel style="margin-bottom: 10px" :after-change="onChange">-->
      <!--        <img src="https://picsum.photos/1920/1080?random=2"/>-->
      <!--        <img src="https://picsum.photos/1920/1080?random=3"/>-->
      <!--        <img src="https://picsum.photos/1920/1080?random=4"/>-->
      <!--        <img src="https://picsum.photos/1920/1080?random=5"/>-->
      <!--        <img src="https://picsum.photos/1920/1080?random=6"/>-->
      <!--      </a-carousel>-->

      <a-card style="margin-top: 8px;box-shadow: none " class="index-article-card" :bordered="false">
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
        <a-list item-layout="horizontal" :data-source="list" :split="false">
          <template #renderItem="{ item }">
            <a-list-item>
              <template #actions>
                <a>关注</a>
              </template>
              <a-list-item-meta>
                <template #title>
                  <a href="https://www.antdv.com/">{{ item.name.last }}</a>
                </template>
                <template #description>
                  <span class="no-wrap">后端开发工程师,2年开发经</span>
                </template>
                <template #avatar>
                  <a-avatar :src="item.picture.large"/>
                </template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-card>
      <!--广告位-->
      <a-card v-for="i in 1" v-if="true" :bordered="false" style="margin-top: 12px;cursor: pointer  ">
        <template #cover>
          <img style="height: 120px;border-radius: 4px" alt="example"
               src="http://localhost:9856/profile/upload/2024_06_13_00_13_IMG_8743.JPG"/>
        </template>
      </a-card>

      <a-card title="阅读排行" :bordered="false" style="margin-top: 12px;min-height: 150px">
        <a-list item-layout="horizontal" :data-source="listWithIndex" :split="false">
          <template #renderItem="{ item }">
            <a-list-item>
              <a-list-item-meta>
                <template #title>
                  <span class="no-wrap rank-title">{{ item.name.last }}</span>
                </template>
                <template #avatar>
                  <span>{{ item.index }}</span>
                </template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-card>
      <a-affix offset-bottom="bottom" :offset-top="45">
        <a-card title="热门资讯" :bordered="false" style="margin-top: 12px;min-height: 150px">
          <a-list item-layout="horizontal" :data-source="listWithIndex" :split="false">
            <template #renderItem="{ item }">
              <a-list-item>
                <a-list-item-meta>
                  <template #title>
                    <span class="no-wrap rank-title">{{ item.name.last }}</span>
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
  <a-modal width="40%" :footer="null" v-model:open="openLoginModal" title="登陆StackOak畅享更多权益">
    <Login/>
  </a-modal>

  <a-row>
    <a-drawer width="45%" :mask="false" :closable="true" placement="right"
              :open="openCategoryDrawer" @close="openCategoryDrawer=false">

      <div style="display: grid; grid-template-columns: repeat(3, 1fr); align-items: start;">
        <div v-for="category in categories" :key="category.category">
          <h3 @click="onSelectCategory(category)">{{ category.category }}</h3>
          <ul>
            <li v-for="sub in category.subcategories" :key="sub.name">
              <a @click="onSelectCategory(category)"  >{{ sub.name }}</a>
            </li>
          </ul>
        </div>
      </div>

    </a-drawer>
  </a-row>
</template>
<script lang="ts" setup>
import {computed, onMounted} from 'vue';
import {ref, reactive, watch} from 'vue';
import {QuestionCircleOutlined} from '@ant-design/icons-vue';
import {type ItemType, type MenuProps, message} from "ant-design-vue";
import {categoryList} from "@/api/category.ts";
import {articleList} from "@/api/post.ts";
import ArticleList from "@/components/ArticleList.vue";
import {useUserStore} from '@/store';
/*------------------------------------变量定义------------------------------------------*/



/*------------------------------------生命周期-------------------------------------------*/



/*------------------------------------初始化---------------------------------------------*/
const categories = ref([
      {
        "category": "后端开发",
        "subcategories": [
          {"name": ".NET", "url": "/cate/108698/"},
          {"name": "Java", "url": "/cate/java/"},
          {"name": "Python", "url": "/cate/python/"},
          {"name": "Go", "url": "/cate/go/"},
          {"name": "PHP", "url": "/cate/php/"},
          {"name": "C++", "url": "/cate/cpp/"},
          {"name": "Ruby", "url": "/cate/ruby/"},
          {"name": "Swift", "url": "/cate/swift/"},
          {"name": "C语言", "url": "/cate/c/"},
          {"name": "Erlang", "url": "/cate/erlang/"},
          {"name": "Pascal/Delphi", "url": "/cate/delphi/"},
          {"name": "Scala", "url": "/cate/scala/"},
          {"name": "R语言", "url": "/cate/r/"},
          {"name": "Verilog", "url": "/cate/verilog/"},
          {"name": "Dart", "url": "/cate/dart/"},
          {"name": "Rust", "url": "/cate/rust/"},
          {"name": "其他语言", "url": "/cate/otherlang/"}
        ]
      },
      {
        "category": "软件设计",
        "subcategories": [
          {"name": "架构设计", "url": "/cate/design/"},
          {"name": "面向对象", "url": "/cate/oo/"},
          {"name": "设计模式", "url": "/cate/dp/"},
          {"name": "领域驱动设计", "url": "/cate/ddd/"}
        ]
      },
      {
        "category": "前端开发",
        "subcategories": [
          {"name": "Html/Css", "url": "/cate/web/"},
          {"name": "JavaScript", "url": "/cate/javascript/"},
          {"name": "jQuery", "url": "/cate/jquery/"},
          {"name": "HTML5", "url": "/cate/html5/"},
          {"name": "Angular", "url": "/cate/angular/"},
          {"name": "React", "url": "/cate/react/"},
          {"name": "Vue", "url": "/cate/vue/"}
        ]
      },
      {
        "category": "AI",
        "subcategories": [
          {"name": "AI综合", "url": "/cate/ai-misc/"},
          {"name": "机器学习", "url": "/cate/ml/"},
          {"name": "大模型", "url": "/cate/llm/"},
          {"name": "Semantic Kernel", "url": "/cate/sk/"}
        ]
      },
      {
        "category": "企业信息化",
        "subcategories": [
          {"name": "BPM", "url": "/cate/bpm/"},
          {"name": "SharePoint", "url": "/cate/sharepoint/"},
          {"name": "GIS技术", "url": "/cate/gis/"},
          {"name": "SAP", "url": "/cate/sap/"},
          {"name": "Oracle ERP", "url": "/cate/OracleERP/"},
          {"name": "Dynamics", "url": "/cate/dynamics/"},
          {"name": "信息安全", "url": "/cate/infosec/"},
          {"name": "企业信息化其他", "url": "/cate/3/"}
        ]
      },
      {
        "category": "移动端开发",
        "subcategories": [
          {"name": "Android开发", "url": "/cate/android/"},
          {"name": "iOS开发", "url": "/cate/ios/"},
          {"name": "Flutter", "url": "/cate/flutter/"},
          {"name": "鸿蒙", "url": "/cate/harmonyos/"},
          {"name": "其他手机开发", "url": "/cate/mobile/"}
        ]
      },
      {
        "category": "软件工程",
        "subcategories": [
          {"name": "敏捷开发", "url": "/cate/agile/"},
          {"name": "项目与团队管理", "url": "/cate/pm/"},
          {"name": "软件工程其他", "url": "/cate/Engineering/"}
        ]
      },
      {
        "category": "数据库",
        "subcategories": [
          {"name": "SQL Server", "url": "/cate/sqlserver/"},
          {"name": "Oracle", "url": "/cate/oracle/"},
          {"name": "MySQL", "url": "/cate/mysql/"},
          {"name": "PostgreSQL", "url": "/cate/postgresql/"},
          {"name": "NoSQL", "url": "/cate/nosql/"},
          {"name": "大数据", "url": "/cate/bigdata/"},
          {"name": "其他数据库", "url": "/cate/database/"}
        ]
      },
      {
        "category": "操作系统",
        "subcategories": [
          {"name": "Windows", "url": "/cate/win7/"},
          {"name": "Windows Server", "url": "/cate/winserver/"},
          {"name": "Linux", "url": "/cate/linux/"},
          {"name": "macOS", "url": "/cate/osx/"},
          {"name": "嵌入式", "url": "/cate/eos/"}
        ]
      },
      {
        "category": "其他",
        "subcategories": [
          {"name": "非技术区", "url": "/cate/life/"},
          {"name": "Kubernetes", "url": "/cate/k8s/"},
          {"name": "软件测试", "url": "/cate/testing/"},
          {"name": "代码与软件发布", "url": "/cate/software/"},
          {"name": "计算机图形学", "url": "/cate/cg/"},
          {"name": "游戏开发", "url": "/cate/gamedev/"},
          {"name": "程序人生", "url": "/cate/codelife/"},
          {"name": "求职面试", "url": "/cate/job/"},
          {"name": "读书区", "url": "/cate/book/"},
          {"name": "转载区", "url": "/cate/quoted/"},
          {"name": "翻译区", "url": "/cate/translate/"},
          {"name": "开源研究", "url": "/cate/opensource/"},
          {"name": "云计算", "url": "/cate/cloud/"},
          {"name": "算法与数据结构", "url": "/cate/algorithm/"},
          {"name": "区块链", "url": "/cate/blockchain/"},
          {"name": "网络安全", "url": "/cate/networksecurity/"},
          {"name": "实时音视频", "url": "/cate/rtc/"},
          {"name": "其他技术区", "url": "/cate/misc/"}
        ]
      }
    ]
)


/*------------------------------------数据加载--------------------------------------------*/



/*------------------------------------核心业务--------------------------------------------*/




/*-------------------------------------其他函数-------------------------------------------*/
const userStore = useUserStore()
import router from "@/router";
import Login from "@/components/Login.vue";
import {friendLinkList} from "@/api/friendlink.ts";
import {getSearchHistory} from "@/api/search.ts";
import {getSysConfigInfo} from "@/api/config.ts";

const openLoginModal = ref(false)/*是否打开登陆框*/
const selectedKeys = ref([0]);
const openKeys = ref([0]);
const items: ItemType[] = reactive([]);
const openCategoryDrawer = ref(false)
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
  size: 150,
  categoryId: 0,
  showType: activeKey.value
})
const onSelectCategory=(cat:any)=>{
  message.info(JSON.stringify(cat))
}
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
const siteConfigInfo = ref({})
const loadSiteConfigInfo = async () => {
  const res = await getSysConfigInfo()
  if (res) {
    siteConfigInfo.value = res || {}
  }
}
//获取用户搜索历史
const searchHistory = ref()
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
onMounted(async () => {
  await loadLeftMenu()
  await loadHomeData()
  await loadFriendLink()
  await loadSiteConfigInfo()
})
//左侧菜单点击事件
const handleClick: MenuProps['onClick'] = e => {
  let categoryId = e.key;
  //打开更多分类
  if ('99' == e.key) {
    if (openCategoryDrawer.value == true) {
      openCategoryDrawer.value = false
      alert("dd")
      return
    }
    openCategoryDrawer.value = true
  } else {
    queryParam.value.categoryId = categoryId
    //筛选数据
    loadHomeData()
  }
};
const onTabClick = (targetKey: string) => {
  queryParam.value.showType = targetKey
  loadHomeData()
};
watch(openKeys, val => {
  console.log('openKeys', val);
});
//搜索
const search_key = ref('')
const onSearch = () => {
  if (search_key.value == '') {
    return;
  }
  router.push({path: '/search', query: {keyword: search_key.value}})
}


const count = 3;
const fakeDataUrl = `https://randomuser.me/api/?results=${count}&inc=name,gender,email,nat,picture&noinfo`;


const data = ref([]);
const list = ref([]);
onMounted(() => {
  fetch(fakeDataUrl)
      .then(res => res.json())
      .then(res => {

        data.value = res.results;
        list.value = res.results;
      });
});
// 计算属性：为列表项添加序号
const listWithIndex = computed(() => {
  return list.value.map((item, index) => ({
    ...item,
    index: index + 1, // 序号从 1 开始
  }));
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

.centered-search {
  width: 100%; /* 搜索框宽度占满容器 */
  border: none;
  box-shadow: none;
}

:deep(.ant-list .ant-list-item .ant-list-item-action) {
  margin-inline-start: 2px;
}

.no-wrap {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  font-weight: 400;
}

.rank-title {
  font-weight: 800;
  color: rgb(37, 41, 51);
  font-family: Archivo;
}


</style>

