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
            style="width: 400px;margin-right: 15px"
            v-model:value="search_key"
            placeholder="探索未来科技"
            enter-button/>
        <a-popover  placement="bottomRight">
          <template #content>
            <a-flex vertical :gap="8">
              <a-button type="text">评论</a-button>
              <a-button type="text">赞和收藏</a-button>
              <a-button type="text">私信</a-button>
              <a-button type="text">新增粉丝</a-button>
              <a-button type="text">系统通知</a-button>
              <a-button type="text">消息设置</a-button>
            </a-flex>
          </template>
          <a-badge :dot="true">
            <a-image style="cursor: pointer" @click="onOpenMsg" :preview="false" src="/xiaoxi.svg"/>
          </a-badge>
        </a-popover>
        <a-button style="margin-left: 15px" v-if="!isLogin" type="primary" @click="openLoginModal=true">登陆</a-button>
        <a-popover v-if="isLogin" placement="bottomRight">
          <template #content>
            <a-flex vertical>
              <router-link to="/author" target="_blank">
                <a-button type="text">我的主页</a-button>
              </router-link>
              <a-button @click="logout" type="text">退出登陆</a-button>
            </a-flex>
          </template>
          <a-avatar style="margin-left:  15px; " :src="userStore.userinfo.avatar" shape="square"/>
        </a-popover>
      </div>
    </a-col>
    <a-modal width="40%" :footer="null" v-model:open="openLoginModal" title="登陆StackOak开始您的创作">
      <Login/>
    </a-modal>
  </a-row>
</template>
<script lang="ts" setup>
import {onMounted, nextTick} from 'vue';
import {reactive} from 'vue';
import {useUserStore} from '@/stores/user'

const isLogin = ref(false)
const userStore = useUserStore()

onMounted(() => {
  if (userStore.isLogin()) {
    isLogin.value = true
  }
})

//邮箱账号-密码登陆
interface EmailLoginDTO {
  email: string;
  password: string;
}

const emailLoginDTO = reactive<EmailLoginDTO>({
  email: 'StackOak@163.com',
  password: '123456',
});
const onEmailLoginFinish = (values: EmailLoginDTO) => {
  console.log(values)
  emailLogin(values)
      .then(res => {
        userStore.setToken(res.data.token);
        // 获取用户信息
        return getUserInfo();
      })
      .then(res => {
        userStore.setUserInfo(res.data);
        window.location.href = '/';
      })
      .catch(error => {
        console.error('登录失败:', error);
      });
};

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
const onEmailRegisterFinish = (values: EmailLoginDTO) => {
  emailCodeLogin(values).then(res => {
    if (res.data) {

    }
    message.success("登陆成功");
    window.location.href = '/'
  })
}
//获取邮箱验证码
const sendEmailCode = () => {
  if (emailRegisterDTO.email === '') {
    return
  }
  //调用后端接口发送
  sendEmail(emailRegisterDTO.email)
  message.success('验证码已发送！');
}

//三方登陆
const openLoginModal = ref(false)
const search_key = ref('')
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
import {emailCodeLogin, emailLogin} from "@/api/auth.ts";
import {getUserInfo} from "@/api/user.ts";
import {sendEmail} from "@/api/email.ts";
import Login from "@/components/Login.vue";


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
  {
    key: '/opensource',
    label: '官方开源',
    title: '官方开源',
    children: [
      {
        label: '开源项目',
        key: '/opensource/project',
      },
      {
        label: '文档中心',
        key: '/opensource/document',
      },
      {
        label: '使用教程',
        key: '/opensource/guide',
      },
    ],
  },
  {
    key: '/resource',
    label: '资源中心',
    title: 'Navigation Four - Link',
  },
  {
    key: '/qus',
    label: 'test',
    title: 'Navigation Four - Link',
  },
  {
    key: '/noti',
    label: 'noti',
    title: 'Navigation Four - Link',
  },
]);

const openKeys = ref<string[]>(['sub1']);

watch(openKeys, val => {
  console.log('openKeys', val);
});
const handleClick: MenuProps['onClick'] = menuInfo => {
  router.push({path: menuInfo.key})
};
//三方登陆授权
const onAuth = (type: string) => {
  if (type === 'gitee') {
    window.open('https://gitee.com/oauth/authorize?client_id=e6fa7f51960698ac3216d31543f73b1e83fae27296828d823525ff78f7c45c3c&redirect_uri=http://localhost:9856/login&response_type=code', '_blank')
  }
}

//获取邮箱验证码

//验证码样式
// 动态样式
const buttonStyle = ref({
  color: '#1e80ff', // 初始颜色为蓝色
  cursor: 'pointer',
});

const countdownStyle = ref({
  color: '#000', // 倒计时状态为黑色
  cursor: 'default',
  textDecoration: 'none'
});
//倒计时实现
const countdown = ref(0); // 倒计时秒数
const countdownInterval = ref(); // 定时器引用
// 开始倒计时
function startCountdown() {
  // 如果已经在倒计时，直接返回
  if (countdown.value > 0) return;
  //发送验证码
  sendEmailCode();
  countdown.value = 60; // 设置倒计时为60秒
  countdownInterval.value = setInterval(() => {
    countdown.value -= 1; // 每秒减1
    if (countdown.value <= 0) {
      clearInterval(countdownInterval.value); // 倒计时结束，清除定时器
      countdown.value = 0; // 重置倒计时
    }
  }, 1000);
}

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

/*添加竖线*/
.row-with-line {
  position: relative; /* 确保伪元素相对于该元素定位 */
}

/*添加竖线*/
.row-with-line::before {
  content: ""; /* 伪元素必须有 content 属性 */
  position: absolute; /* 绝对定位 */
  top: 0; /* 竖线从顶部开始 */
  bottom: 0; /* 竖线到底部结束 */
  left: 0; /* 竖线在左边 */
  width: 1px; /* 竖线的宽度 */
  background-color: #ede8e8; /* 竖线的颜色，可以根据需要修改 */
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

