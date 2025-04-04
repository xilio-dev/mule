<template>
  <a-flex style="padding:0 20%;padding-top: 20%" align="center" justify="center">
    <a-form
        :model="formState"
        name="normal_login"
        class="login-form"
        @finish="onFinish"
        @finishFailed="onFinishFailed"
    >
      <a-form-item
          label="用户名"
          name="username"
          :rules="[{ required: true, message: 'Please input your username!' }]"
      >
        <a-input v-model:value="formState.username">
          <template #prefix>
            <UserOutlined class="site-form-item-icon"/>
          </template>
        </a-input>
      </a-form-item>

      <a-form-item
          label="密码"
          name="password"
          :rules="[{ required: true, message: 'Please input your password!' }]"
      >
        <a-input-password v-model:value="formState.password">
          <template #prefix>
            <LockOutlined class="site-form-item-icon"/>
          </template>
        </a-input-password>
      </a-form-item>

      <a-form-item>
        <a-form-item name="remember" no-style>
          <a-checkbox v-model:checked="formState.remember">记住我</a-checkbox>
        </a-form-item>
        <a class="login-form-forgot" href="">忘记密码</a>
      </a-form-item>

      <a-form-item>
        <a-button :disabled="disabled" type="primary" html-type="submit" class="login-form-button">
          登陆
        </a-button>
        或者
        <a href="">注册!</a>
      </a-form-item>
    </a-form>
  </a-flex>
</template>
<script lang="ts" setup>
import {reactive, computed} from 'vue';
import {UserOutlined, LockOutlined} from '@ant-design/icons-vue';
import {useUserStore} from "@/store/modules/user.ts";
import router from "@/router";
import {usePermissionStore} from "@/store";

const userStore = useUserStore()

interface FormState {
  username: string;
  password: string;
  remember: boolean;
}

const formState = reactive<FormState>({
  username: 'admin',
  password: '123456',
  remember: true,
});
const onFinish = (values: any) => {
  usePermissionStore().generateRoutes(["admin"])
  userStore.loginAction({...values}).then(res=>{
    router.push({path:"/"})
  })
};

const onFinishFailed = (errorInfo: any) => {
  console.log('Failed:', errorInfo);
};
const disabled = computed(() => {
  return !(formState.username && formState.password);
});
</script>
<style scoped>
#components-form-demo-normal-login .login-form {
  max-width: 300px;
}

#components-form-demo-normal-login .login-form-forgot {
  float: right;
}

#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
</style>
