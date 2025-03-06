<script setup lang="ts">

</script>

<template>
<a-card title="博客设置" :bordered="false" style="box-shadow: none">
  <a-flex :gap="12" vertical>
    <a-flex>
      <div>图片水印：</div>
      <a-flex vertical :gap="4">
        <div>作品配图自动添加水印</div>
        <div>
         <a-image style="width: 150px;height: 100px" src="/avatar.jpeg"/>
        </div>
      </a-flex>
    </a-flex>
    <a-flex>
      <div>代码样式：</div>
      <a-flex vertical :gap="4">
        <div>选择代码片</div>
        <div>
          <a-image style="width: 150px;height: 100px" src="/avatar.jpeg"/>
        </div>
      </a-flex>
    </a-flex>
    <a-divider/>
  </a-flex>
</a-card>
</template>

<style scoped>

</style>
