<script setup lang="ts">
import {onMounted, nextTick, ref} from 'vue';
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
        userStore.setToken(res.token);
        // 获取用户信息
        return getUserInfo();
      })
      .then(res => {
        userStore.setUserInfo(res);
        window.location.href = '/';
      })
      .catch(error => {
        console.error('登录失败:', error);
      });
};
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
  emailCodeLogin(values).then(res=>{
    if (res){

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
const activeKey = ref('1');


import {type MenuProps, message} from "ant-design-vue";
import {emailCodeLogin, emailLogin} from "@/api/auth.ts";
import {getUserInfo} from "@/api/user.ts";
import {sendEmail} from "@/api/email.ts";


//三方登陆授权
const onAuth = (type: string) => {
  if (type === 'gitee') {
    window.open('https://gitee.com/oauth/authorize?client_id=e6fa7f51960698ac3216d31543f73b1e83fae27296828d823525ff78f7c45c3c&redirect_uri=http://localhost:9856/auth/login&response_type=code', '_blank')
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
const countdownInterval = ref(null); // 定时器引用
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
</script>

<template>
  <a-row :gutter="40">
    <a-col :span="14">
      <a-row style="width: 100%">
        <a-tabs v-model:activeKey="activeKey">
          <a-tab-pane key="1" tab="登陆">
            <a-form
                :model="emailLoginDTO"
                name="basic"
                autocomplete="off"
                @finish="onEmailLoginFinish">
              <a-form-item name="email" :rules="[{ required: true, message: '请填写邮箱!' }]">
                <a-input v-model:value="emailLoginDTO.email" :bordered="false" placeholder="邮箱号"
                         style="margin-top: 10px;background-color: #f2f3f5"/>
              </a-form-item>

              <a-form-item name="password" :rules="[{ required: true, message: '请输入账号密码!' }]">
                <a-input-password v-model:value="emailLoginDTO.password" :bordered="false" placeholder="密码"
                                  style="margin-top: 10px;background-color: #f2f3f5;border-radius: 6px;">
                  <template #addonAfter>
                    <span>忘记密码</span>
                  </template>
                </a-input-password>
              </a-form-item>
              <a-form-item>
                <a-button html-type="submit" type="primary" style="width: 100%;margin-top: 10px">登陆</a-button>
              </a-form-item>
            </a-form>

          </a-tab-pane>
          <a-tab-pane key="2" tab="注册" force-render>
            <a-form
                :model="emailRegisterDTO"
                name="basic"
                autocomplete="off"
                @finish="onEmailRegisterFinish">
              <a-form-item name="email" :rules="[{ required: true, message: '请填写邮箱!' }]">
                <a-input v-model:value="emailRegisterDTO.email" :bordered="false" placeholder="邮箱号"
                         style="margin-top: 10px;background-color: #f2f3f5;"/>
              </a-form-item>

              <a-form-item name="code" :rules="[{ required: true, message: '请输入验证码!' }]">
                <a-input v-model:value="emailRegisterDTO.code" :bordered="false" placeholder="验证码"
                         style="margin-top: 10px;background-color: #f2f3f5;border-radius: 6px;">
                  <template #addonAfter>
                    <span :style="buttonStyle" v-if="!countdown" @click="startCountdown">获取验证码</span>
                    <span :style="countdownStyle" v-else>{{ countdown }}秒后可重新获取</span>
                  </template>
                </a-input>
              </a-form-item>
              <a-form-item>
                <a-button html-type="submit" type="primary" style="width: 100%;margin-top: 10px">登陆 / 注册
                </a-button>
              </a-form-item>
            </a-form>


          </a-tab-pane>
        </a-tabs>

      </a-row>
      <a-row style="margin-top: 15px">
        <a-flex align="center" :gap="8">
          <div>其他登陆</div>

          <a-flex :gap="10" align="center">
            <svg @click="onAuth('github')" style="cursor: pointer" t="1739886883165" class="icon"
                 viewBox="0 0 1024 1024" version="1.1"
                 xmlns="http://www.w3.org/2000/svg" p-id="18579" width="20" height="20">
              <path
                  d="M20.48 503.72608c0 214.4256 137.4208 396.73856 328.94976 463.6672 25.8048 6.5536 21.87264-11.8784 21.87264-24.33024v-85.07392c-148.93056 17.44896-154.86976-81.1008-164.94592-97.52576-20.23424-34.52928-67.91168-43.33568-53.69856-59.76064 33.91488-17.44896 68.48512 4.42368 108.46208 63.61088 28.95872 42.88512 85.44256 35.6352 114.15552 28.4672a138.8544 138.8544 0 0 1 38.0928-66.7648c-154.25536-27.60704-218.60352-121.77408-218.60352-233.79968 0-54.31296 17.94048-104.2432 53.0432-144.54784-22.36416-66.43712 2.08896-123.24864 5.3248-131.6864 63.81568-5.7344 130.00704 45.6704 135.168 49.68448 36.2496-9.78944 77.57824-14.9504 123.82208-14.9504 46.4896 0 88.064 5.3248 124.5184 15.23712 12.288-9.4208 73.80992-53.53472 133.12-48.128 3.15392 8.43776 27.0336 63.93856 6.02112 129.4336 35.59424 40.38656 53.69856 90.76736 53.69856 145.24416 0 112.18944-64.7168 206.4384-219.42272 233.71776a140.0832 140.0832 0 0 1 41.7792 99.9424v123.4944c0.86016 9.87136 0 19.6608 16.50688 19.6608 194.31424-65.49504 334.2336-249.15968 334.2336-465.5104C1002.57792 232.48896 782.66368 12.77952 511.5904 12.77952 240.18944 12.65664 20.48 232.40704 20.48 503.72608z"
                  fill="#000000" opacity=".65" p-id="18580"></path>
            </svg>

            <svg @click="onAuth('gitee')" style="cursor: pointer" t="1739886982579" class="icon"
                 viewBox="0 0 1024 1024" version="1.1"
                 xmlns="http://www.w3.org/2000/svg" p-id="20726" width="20" height="20">
              <path
                  d="M512 1024C229.222 1024 0 794.778 0 512S229.222 0 512 0s512 229.222 512 512-229.222 512-512 512z m259.149-568.883h-290.74a25.293 25.293 0 0 0-25.292 25.293l-0.026 63.206c0 13.952 11.315 25.293 25.267 25.293h177.024c13.978 0 25.293 11.315 25.293 25.267v12.646a75.853 75.853 0 0 1-75.853 75.853h-240.23a25.293 25.293 0 0 1-25.267-25.293V417.203a75.853 75.853 0 0 1 75.827-75.853h353.946a25.293 25.293 0 0 0 25.267-25.292l0.077-63.207a25.293 25.293 0 0 0-25.268-25.293H417.152a189.62 189.62 0 0 0-189.62 189.645V771.15c0 13.977 11.316 25.293 25.294 25.293h372.94a170.65 170.65 0 0 0 170.65-170.65V480.384a25.293 25.293 0 0 0-25.293-25.267z"
                  fill="#C71D23" p-id="20727"></path>
            </svg>
          </a-flex>
        </a-flex>
      </a-row>

    </a-col>
    <a-col :span="10" class="row-with-line">
      <h3 style="margin-bottom: 15px;">APP扫码登陆</h3>
      <a-qrcode
          error-level="H"
          value="https://www.antdv.com"
          icon="https://www.antdv.com/assets/logo.1ef800a8.svg"
      />
    </a-col>
  </a-row>
</template>

<style scoped>
:deep(.slick-slide h3) {
  color: #fff;
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

</style>
