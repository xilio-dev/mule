<template>
  <a-flex align="center" justify="center" style="margin-top: 15%">
    <a-card class="login" :bordered="false" style="box-shadow: none">
      <Login/>
    </a-card>
  </a-flex>
</template>
<script lang="ts" setup>
import Login from "@/components/Login.vue";
</script>
<style scoped>
</style>
