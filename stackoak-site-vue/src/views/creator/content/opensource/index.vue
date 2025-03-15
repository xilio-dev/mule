<script setup lang="ts">
import {computed, ref} from "vue";
import {CommonUtil} from "@/utils/common.ts";

const project = ref()
/*------------------------------------变量定义------------------------------------------*/



/*------------------------------------生命周期-------------------------------------------*/



/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/



/*------------------------------------核心业务--------------------------------------------*/
const {username,projectName}=CommonUtil.parseGitUrl('https://github.com/apache/answer.git')

fetch(`https://api.github.com/repos/${username}/${projectName}`)
    .then(response => response.json())
    .then(data => {
      project.value = data
      console.log('Star count:', data.stargazers_count);
      console.log('Fork count:', data.forks_count);
    });

// fetch(`https://gitee.com/api/v5/repos/${username}/${projectName}`)
//     .then(response => response.json())
//     .then(data => {
//       project.value = data
//       console.log('Star count:', data.stargazers_count);
//       console.log('Fork count:', data.forks_count);
//     });



/*-------------------------------------其他函数-------------------------------------------*/





</script>

<template>
  <a-card :bordered="false" title="开源项目">
    {{ project }}

    <template #extra>
      <a-button type="primary" size="small">新建项目</a-button>
    </template>
  </a-card>
</template>

<style scoped>

</style>
