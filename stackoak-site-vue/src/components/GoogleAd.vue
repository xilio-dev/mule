<template>
  <div class="ad-container">
    <ins
        class="adsbygoogle"
        style="display: block"
        data-ad-client="ca-pub-5856655690528931"
        data-ad-slot="7452068893"
        data-ad-format="auto"
        data-full-width-responsive="true"
    ></ins>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from "vue";

const loadAdScript = () => {
  // 检查是否已经加载过广告脚本
  if (window.adsbygoogle) {
    (window.adsbygoogle = window.adsbygoogle || []).push({});
    return;
  }

  // 动态加载 Google Ads 脚本
  const script = document.createElement("script");
  script.async = true;
  script.src = "https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-5856655690528931";
  script.crossOrigin = "anonymous";
  script.onload = () => {
    (window.adsbygoogle = window.adsbygoogle || []).push({});
  };
  document.head.appendChild(script);
};

// 在组件挂载时加载广告脚本
onMounted(loadAdScript);
</script>

<style scoped>
.ad-container {
  margin: 10px auto;
}
</style>
