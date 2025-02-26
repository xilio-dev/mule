<script setup lang="ts">
import Navbar from "@/components/Navbar.vue";
import {QuestionCircleOutlined, CustomerServiceOutlined} from "@ant-design/icons-vue";
import {ref} from "vue";
import Markdown from "@/components/Markdown.vue";

const mdValue = ref("#标题\n这就是我的第一篇文章，非常牛逼\n\n\n##hello\n##nnn");
</script>

<template>

  <a-row style="padding-top: 60px ;padding-bottom: 60px;padding-left: 12%;padding-right: 13%" :gutter="20">
    <a-col :span="6">
      <a-card style="height: 200px">
        <div style="text-align: center; width: 100%">
          <a-button style="width: 100%">关注</a-button>
          <a-button style="width: 100%">关注</a-button>
        </div>
      </a-card>
      <a-card style="height: 200px; margin-top: 8px">ddd</a-card>
      <a-card title="相关推荐" style="height: 200px; margin-top: 8px">ddd</a-card>
      <a-card title="精选内容" style="height: 200px; margin-top: 8px">ddd</a-card>
    </a-col>
    <a-col :span="18" style="background-color: white;float: left">
      <Markdown md-id="1" :preview="false" :value="mdValue"/>
      <div v-for="i in 40">dd</div>

    </a-col>
  </a-row>
  <a-float-button-group shape="circle" style="position: fixed; right: 8%; bottom: 50%;">
    <a-float-button shape="circle" :badge="{ count: '50', color: 'rgb(194, 200, 209)' }">
      <template #icon>
        <QuestionCircleOutlined/>
      </template>
    </a-float-button>
    <a-float-button :badge="{ count: '5w', color: 'rgb(194, 200, 209)' }"/>
    <a-float-button :badge="{ count: '5k', color: 'rgb(194, 200, 209)' }"></a-float-button>
    <a-float-button></a-float-button>
  </a-float-button-group>

  <a-float-button-group shape="circle">
    <a-float-button>
      <template #icon>
        <QuestionCircleOutlined/>
      </template>
    </a-float-button>

    <a-float-button
        shape="circle"
        type="primary"
        :style="{right: '94px'}">
      <template #icon>
        <CustomerServiceOutlined/>
      </template>
    </a-float-button>
    <a-back-top/>
  </a-float-button-group>
</template>

<style scoped>
:deep(.ant-float-btn .ant-badge .ant-badge-count ) {
  transform: translate(0, 0);
  transform-origin: center;
  top: -10px;
  inset-inline-end: -13px;
}
</style>
