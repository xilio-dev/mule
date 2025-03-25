<template>
  <a-row style="padding: 0 10%;background-color: white;width: 100%">
    <a-col :span="10">
      <div style="float: left;margin-top: 10px;">
        <RouterLink to="/">
          <div class="logo-container"></div>
        </RouterLink>
      </div>
      <a-menu @click="handleClick" v-model:selectedKeys="current" mode="horizontal" :items="items1"/>
    </a-col>
    <a-col :span="14" style="white-space: nowrap">
      <div style="float: right;margin-top: 6px">
        <a-input-search
            v-if="false"
            style="width: 400px;margin-right: 15px"
            v-model:value="search_key"
            placeholder="发现更多精彩"
            @search="onSearch"
            enter-button/>

        <span>苏州</span>

        <a-image src="/w1.png" style="width: 24px;height: 24px" :preview="false" aria-hidden="true"/>

        <span style="margin-right: 15px">晴 21度 良</span>


        <a-popover placement="bottomRight">
          <template #content>
            <a-flex vertical :gap="8" justify="flex-start">
              <a-button @click="openMsgManage('/msg/comment')" type="text">
                <a-flex align="center" justify="space-between" :gap="8">
                  <span> 评论</span>
                  <a-badge :count="unread.count['1']"/>
                </a-flex>
              </a-button>
              <a-button @click="openMsgManage('/msg/like')" type="text">

                <a-flex align="center" justify="space-between" :gap="8" style="width: 100%">
                  <span style="flex: 1; text-align: left">赞和收藏</span>
                  <a-badge :count="unread.count['4']+unread.count['2']"/>
                </a-flex>
              </a-button>
              <a-button @click="openMsgManage('/msg/chat')" type="text">

                <a-flex align="center" justify="space-between" :gap="8" style="width: 100%">
                  <span style="flex: 1; text-align: left">私信</span>
                  <a-badge :count="unread.count['6']"/>
                </a-flex>
              </a-button>
              <a-button @click="openMsgManage('/msg/attention')" type="text">

                <a-flex align="center" justify="space-between" :gap="8" style="width: 100%">
                  <span style="flex: 1; text-align: left">新增粉丝</span>
                  <a-badge :count="unread.count['3']"/>
                </a-flex>
              </a-button>
              <a-button @click="openMsgManage('/msg/system')" type="text">

                <a-flex align="center" justify="space-between" :gap="8" style="width: 100%">
                  <span style="flex: 1; text-align: left">系统通知</span>
                  <a-badge :count="unread.count['5']"/>
                </a-flex>
              </a-button>
              <a-button @click="openMsgManage('/setting/notification')" type="text">
                <a-flex align="center" justify="space-between" :gap="8" style="width: 100%">
                  <span style="flex: 1; text-align: left">消息设置</span>
                </a-flex>
              </a-button>
            </a-flex>
          </template>
          <a-badge :dot="unread.total>0">
            <a-image style="cursor: pointer" @click="onOpenMsg" :preview="false" src="/xiaoxi.svg"/>
          </a-badge>
        </a-popover>
        <a-button style="margin-left: 15px" v-if="!isLogin" type="primary" @click="openLoginModal=true">登陆</a-button>
        <a-popover v-if="isLogin" placement="bottomRight">
          <template #content>
            <a-flex vertical>
              <router-link :to="`/author/${userStore.userinfo.userId}`" target="_blank">
                <a-button type="text">个人主页</a-button>
              </router-link>
              <router-link to="/creator" target="_blank">
                <a-button type="text">创作中心</a-button>
              </router-link>
              <router-link to="/setting/profile" target="_blank">
                <a-button type="text">账号设置</a-button>
              </router-link>
              <a-button @click="logout" type="text">退出登陆</a-button>
            </a-flex>
          </template>
          <a-avatar style="margin-left:  15px; " :src="ImageUtils.getImgUrl(userStore.userinfo.avatar||'')"
                    shape="square"/>
        </a-popover>
      </div>
    </a-col>
    <a-modal width="40%" :footer="null" v-model:open="openLoginModal" title="登陆StackOak" @cancel="closeLoginModel">
      <Login :openStatus="openLoginModal" ref="loginPageRef"/>
    </a-modal>
  </a-row>
</template>
<script lang="ts" setup>
import {onMounted, nextTick, onBeforeUnmount} from 'vue';
import {reactive} from 'vue';
import {useUserStore} from '@/store';
/*------------------------------------变量定义------------------------------------------*/

const loginPageRef = ref()

/*------------------------------------生命周期-------------------------------------------*/



/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
const loadUnReadMessageCount = async () => {
  const res = await getUnreadCount();
  if (res) {
    unread.value = res || {}
  }

}


/*------------------------------------核心业务--------------------------------------------*/




/*-------------------------------------其他函数-------------------------------------------*/
const closeLoginModel = () => {
  openLoginModal.value = false
  if (loginPageRef.value) {
    loginPageRef.value.clear()
  }
}
const isLogin = ref(false)
const userStore = useUserStore()


const unread = ref({})

const messages = ref([]);
const eventSource = ref();
onMounted(() => {
  if (userStore.isLogin()) {

    eventSource.value = new EventSource(`${import.meta.env.VITE_APP_BASE_API}/notification/createSse/${userStore.userinfo.userId}`);
    eventSource.value.onmessage = (event: any) => {
      if (event.data) {
        console.log(event.data)
        const res = JSON.parse(event.data)
        message.info(res.content)
        messages.value.push(event.data);
      }
    };
    eventSource.value.onerror = (error) => {
      console.error('SSE 连接出错:', error);
      eventSource.value.close();
    };
  }
});

onBeforeUnmount(() => {
  if (userStore.isLogin() && eventSource) {
    eventSource.value.close();
  }
});

onMounted(() => {
  if (userStore.isLogin()) {
    isLogin.value = true
  }
  loadUnReadMessageCount()
})
const openMsgManage = (path: string) => {
  router.push({path: path})
}

//邮箱账号-密码登陆
interface EmailLoginDTO {
  email: string;
  password: string;
}


//退出登陆
const logout = () => {
  userStore.logout()
  window.location.href = '/'
}
//注册

//邮箱账号-密码登陆
interface EmailRegisterDTO {
  email: string;
  code: Number;
}

const emailRegisterDTO = reactive<EmailRegisterDTO>({
  email: 'StackOak@163.com',
  code: 123456,
});

//获取邮箱验证码
const sendEmailCode = () => {
  if (emailRegisterDTO.email === '') {
    return
  }
  //调用后端接口发送
  sendEmail(emailRegisterDTO.email)
  message.success('验证码已发送！');
}
const search_key = ref('')
const onSearch = () => {
  if (search_key.value == '') {
    return;
  }

  router.push({path: '/search', query: {keyword: search_key.value}})
}
//三方登陆
const openLoginModal = ref(false)

const count = 2;
const fakeDataUrl = `https://randomuser.me/api/?results=${count}&inc=name,gender,email,nat,picture&noinfo`;
const activeKey = ref('1');
const initLoading = ref(true);
const loading = ref(false);
const data = ref([]);
const list = ref([]);
onMounted(() => {
  fetch(fakeDataUrl)
      .then(res => res.json())
      .then(res => {
        initLoading.value = false;
        data.value = res.results;
        list.value = res.results;
      });
});
import {ref, watch} from 'vue';
import router from "@/router";
import {type MenuProps, message, notification} from "ant-design-vue";
import {sendEmail} from "@/api/email.ts";
import Login from "@/components/Login.vue";
import {getUnreadCount} from "@/api/notify.ts";
import {ImageUtils} from "@/utils/file.ts";


const listData: Record<string, string>[] = [];

for (let i = 0; i < 23; i++) {
  listData.push({
    href: 'https://www.antdv.com/',
    title: `具身智能中 VLA 主流方案全解来展望 ${i}`,
    avatar: 'https://joeschmoe.io/api/v1/random',
    description:
        '具身智能中 VL中 VLA 主流方案全解析：来展望',
    content:
        'We s ypes beautifully and efficiently.',
  });
}
const current = ref<string[]>(['mail']);
const items1 = ref<MenuProps['items']>([
  {
    key: '/',
    label: '首页',
  },
]);

const openKeys = ref<string[]>(['sub1']);

watch(openKeys, val => {
  console.log('openKeys', val);
});
const handleClick: MenuProps['onClick'] = menuInfo => {
  router.push({path: menuInfo.key})
};
//如果用户已登陆则打开消息界面
const onOpenMsg = () => {
  if (!userStore.isLogin()) {
    //打开登陆框
    openLoginModal.value = true
    return;
  }
  router.push({path: '/msg'})
}
</script>
<style scoped>
:deep(.slick-slide h3) {
  color: #fff;
}

:deep(.ant-menu-horizontal ) {
  line-height: 46px;
  border: 0;
  border-bottom: none;
  box-shadow: none;
}

/*输入框颜色*/
:deep(.ant-input-group .ant-input-group-addon) {
  cursor: pointer;
  background-color: transparent;
  border: none;
  border-radius: 6px;
}

:deep(.ant-input-group .ant-input-group-addon):hover {
  color: #1e80ff;
}

.logo-container {
  width: 137px; /* 容器宽度 */
  height: 28px; /* 容器高度 */
  background-image: url('/logo.jpeg'); /* 替换为你的图片路径 */
  background-size: cover; /* 让背景图覆盖整个容器 */
  background-position: center;
  background-repeat: no-repeat;
}

</style>

