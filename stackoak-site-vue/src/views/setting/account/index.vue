<script setup lang="ts">

</script>

<template>
  <a-card title="账号设置">
    <a-flex justify="space-between" align="center">
      <div>
        <div>绑定邮箱</div>
      </div>
      <div>
        <span class="op-label">换绑</span>
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
</template>

<style scoped>
.thirty-account-name {
  color: color(display-p3 0.15546 0.38118 0.86881)
}

.gray-label {
  color: #758195;
  font-size: 13px;
}
.op-label{
  color: #1772F6;
  cursor: pointer;
}
</style>
