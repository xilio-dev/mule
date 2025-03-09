<script setup lang="ts">
import {reactive, ref} from "vue";
import {sendEmail, validEmailCode} from "@/api/email.ts";
import {message} from "ant-design-vue";
import {useUserStore} from "@/stores/user.ts";
import {changeEmailBind} from "@/api/auth.ts";

const userStore = useUserStore()
const openChangeEmailBindModel = ref(false)
const next2 = ref(false)
const currentStep = ref(0)
const onNextStep = () => {
  if (currentStep.value == 0) {
    if (emailForm.code == '') {
      message.warning("请输入验证码！")
      return;
    }
    //验证验证码是否正确
    validEmailCode({email: userStore.userinfo.email, code: emailForm.code}).then(validSuccess => {
      if (!validSuccess) {
        message.info("验证码不正确！")
        return;
      } else {
        //验证成功，执行下一步
        clearInterval(countdownInterval.value);
        emailForm.email = ''
        emailForm.code = ''
        countdown.value = 0
        next2.value = true
        currentStep.value = 1
      }
    })
  }
  if (currentStep.value == 1) {
    //开始绑定新的邮箱
    changeEmailBind({email: emailForm.email, code: emailForm.code}).then(res => {
      //验证成功，执行下一步
      //更新本地存储email
      userStore.updateEmail(emailForm.email)
      clearInterval(countdownInterval.value);
      emailForm.email = ''
      emailForm.code = ''
      countdown.value = 0
      currentStep.value = 2
      message.success("已绑定！")
      //关闭对话框
      openChangeEmailBindModel.value = false
    }).catch(err => {
      message.error("邮箱换绑失败！")
      openChangeEmailBindModel.value = false
    })
  }
}

interface EmailDTO {
  email: string;
  code: string;
}

const emailForm = reactive<EmailDTO>({
  email: userStore.userinfo.email,
  code: '',
});
//获取邮箱验证码
const sendEmailCode = () => {
  if (emailForm.email == '') {
    return
  }
  //调用后端接口发送
  sendEmail(emailForm.email)
  message.success('验证码已发送！');
}
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
  //检查邮箱是否为空
  if (emailForm.email == '') {
    return;
  }
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
  <a-card title="账号设置">
    <a-flex justify="space-between" align="center">
      <a-flex :gap="8">
        <div>绑定邮箱</div>
        <span class="gray-label">{{ userStore.userinfo.email }}</span>
      </a-flex>
      <div>
        <span class="op-label" @click="openChangeEmailBindModel=true">换绑</span>
      </div>
    </a-flex>
    <a-divider/>
    <a-flex justify="space-between" align="center">
      <div>
        <div>密码</div>
        <div class="gray-label">未设置</div>
      </div>
      <div>
        <span class="op-label">编辑</span>
      </div>

    </a-flex>
    <a-divider/>
    <a-flex vertical>
      <div style="margin-bottom: 15px">
        绑定第三方账号
      </div>
      <a-flex justify="start" :gap="30">
        <a-flex :gap="4" align="center">
          <a-image src="/icon/github32.png" :preview="false"/>
          <a-flex vertical>
            <span> StackOak</span>
            <span v-if="false"> 绑定Github</span>
            <span class="gray-label"> 解绑</span>
          </a-flex>
        </a-flex>
        <a-flex :gap="4" align="center">
          <a-image src="/icon/gitee32.png" :preview="false"/>
          <a-flex vertical>
            <span class="thirty-account-name"> 绑定Gitee</span>
            <span v-if="false" class="gray-label"> 解绑</span>
          </a-flex>
        </a-flex>

      </a-flex>

    </a-flex>
    <a-divider/>
    <a-flex justify="space-between" align="center">
      <div>
        <span>账号注销</span>
      </div>
      <div>
        <span class="op-label">注销</span>
      </div>
    </a-flex>
    <a-divider/>
  </a-card>

  <a-modal width="350px" :footer="null" v-model:open="openChangeEmailBindModel" title="邮箱变更" @ok="handleOk">
    <div  v-if="!next2">原邮箱号：<span class="gray-label">{{ userStore.userinfo.email }}</span></div>
    <a-input v-if="next2" placeholder="请输入新的邮箱号" v-model:value="emailForm.email"/>
    <a-input v-model:value="emailForm.code" :bordered="false" placeholder="验证码"
             style="margin-top: 10px;background-color: #f2f3f5;border-radius: 6px;">
      <template #addonAfter>
        <span :style="buttonStyle" v-if="!countdown" @click="startCountdown">获取验证码</span>
        <span :style="countdownStyle" v-else>{{ countdown }}秒后可重新获取</span>
      </template>
    </a-input>
    <a-button style="width: 100%;margin-top: 20px" type="primary" @click="onNextStep">下一步</a-button>
  </a-modal>
</template>

<style scoped>
.thirty-account-name {
  color: color(display-p3 0.15546 0.38118 0.86881)
}

.gray-label {
  color: #758195;
  font-size: 13px;
}

.op-label {
  color: #1772F6;
  cursor: pointer;
}
</style>
