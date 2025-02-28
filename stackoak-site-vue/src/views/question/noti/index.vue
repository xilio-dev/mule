<template>
  <div>
    <h3>接收到的消息:</h3>
    <ul>
      <li v-for="(message, index) in messages" :key="index">{{ message }}</li>
    </ul>
  </div>
</template>
<script>
import { onMounted, onBeforeUnmount, ref } from 'vue';
import axios from 'axios';
export default {
  setup() {
    const messages = ref([]);
    let eventSource = null;
    onMounted(() => {
      eventSource = new EventSource('http://localhost:9856/createSse/user1');
      eventSource.onmessage = (event) => {
        messages.value.push(event.data);
      };
      eventSource.onerror = (error) => {
        console.error('SSE 连接出错:', error);
        eventSource.close();
      };
    });

    onBeforeUnmount(() => {
      if (eventSource) {
        eventSource.close();
      }
    });
    return {
      messages,
    };
  }
};
</script>
