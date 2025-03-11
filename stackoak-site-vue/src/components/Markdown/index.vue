<template>
  <div :id="mdId" :style="{ height: height + 'vh' }"/>
</template>
<script setup lang="ts">

import {ref, onMounted, onUnmounted} from "vue";
import "cherry-markdown/dist/cherry-markdown.min.css";
import Cherry from "cherry-markdown";
import {getConfig} from "./config";

const emit = defineEmits(['markdown-change'])
const props = defineProps(['mdId', 'height', 'width', 'preview', 'value'])
const cherryInstance = ref<Cherry | null>(null);
onMounted(() => {
  initCherryMD()
});

onUnmounted(() => {
  // @ts-ignore
  cherryInstance.value.destroy()
})

//@ts-nocheck
const initCherryMD = () => {
  cherryInstance.value = new Cherry(getConfig({
    id: props.mdId,
    value: props.value,
    preview: !props.preview,
    float: true,
    codeTheme: 'tomorrow dark',
    mainTheme: 'default',
    anchorStyle: 'none',
    cherryInstance: cherryInstance,
    emit: emit
  }))
}
</script>
<style scoped>
:deep(.cherry) {
  box-shadow: none;
  border: none;
  min-height: 0;
}

</style>
