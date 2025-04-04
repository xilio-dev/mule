<template>
  <a-layout has-sider>
    <a-layout-sider
        v-model:collapsed="collapsed" collapsible
        :style="{ overflow: 'auto', height: '100vh', position: 'fixed', left: 0, top: 0, bottom: 0 }"
    >
      <Sidebar/>
    </a-layout-sider>
    <a-layout :style="{ marginLeft: collapsed?'80px':'200px',  }">
      <a-layout-header :style="{ background: '#fff', padding: 0 }">
        <Navbar/>
      </a-layout-header>
      <a-layout-content :style="{ margin: '15px 0 0', overflow: 'initial' }">
        <MainContent/>
      </a-layout-content>
      <a-layout-footer :style="{ textAlign: 'center' }">
        <Footer/>
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>
<script lang="ts" setup>
import {ref} from 'vue';
import Sidebar from "@/layout/Sidebar/index.vue";
import MainContent from "@/layout/main/index.vue"
import Footer from "@/layout/footer/index.vue"
import Navbar from "@/layout/Navbar/index.vue"

const collapsed = ref<boolean>(false);

</script>
<style scoped>


.site-layout .site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}
</style>
