<script setup lang="ts">
//@ts-ignore
import {ref} from "vue";

const value = defineModel<string>('value', {required: true})
const emit = defineEmits(['onClick'])
defineProps(['disabled','placeholder'])
const commentInputRef = ref();
const blur = () => {
  if (commentInputRef.value) {
    commentInputRef.value.blur()
  }
}
const focus = () => {
  if (commentInputRef.value) {
    commentInputRef.value.focus()
  }
}
// 暴露 textareaRef 给父组件
defineExpose({blur,focus});
</script>

<template>
  <div class="main-input">
    <div class="comment-wrapper">
      <a-flex vertical>
        <textarea ref="commentInputRef" v-model.trim="value" class="comment-input" :placeholder="placeholder"/>
        <a-flex justify="space-between" align="center">
          <a-flex :gap="12">
            <svg t="1742233266151" class="icon emoji-icon" viewBox="0 0 1024 1024" version="1.1"
                 xmlns="http://www.w3.org/2000/svg"
                 p-id="7622" width="24" height="24">
              <path
                  d="M712.5 788.8H194.2c-22.5 0-43-8.8-58.7-23.3l146.9-183.2c7.9-9.8 19-15.5 31.2-15.9 12.2-0.4 23.6 4.6 32 13.9l12.9 14.3c17.1 19 41 28.8 65.8 27.1 24.7-1.7 47.2-14.9 61.8-36L560.2 478c8-11.7 20.5-18.4 34.1-18.5h0.3c13.6 0 26 6.6 34.1 18.1l76.7 108.9c6.8 9.6 19.6 11.5 28.6 4.3 8.9-7.1 10.7-20.4 4-29.9L661.3 452c-15.9-22.5-40.1-35.4-66.7-35.4h-0.5c-26.8 0.2-51.1 13.4-66.8 36.2l-74.1 107.8c-7.4 10.8-18.9 17.5-31.6 18.4-12.6 0.9-24.9-4.2-33.6-13.8l-12.9-14.3c-16.2-18-39.1-28-62.7-27.3-23.6 0.7-45.8 12-61.1 31l-140 174.7c-4-10.7-6.3-22.5-6.3-34.7V328.9c0-52 40-94.2 89.2-94.2H767c49.3 0 89.2 42.2 89.2 94.2v181.3c0 11.9 9.1 21.5 20.3 21.5s20.3-9.6 20.3-21.5V328.9c0-75.7-58.2-137.1-129.9-137.1H194.2c-71.7 0-129.9 61.4-129.9 137.1v365.7c0 75.7 58.2 137.1 129.9 137.1h518.3c11.2 0 20.3-9.6 20.3-21.5 0-11.8-9.1-21.4-20.3-21.4z"
                  p-id="7623"></path>
              <path
                  d="M926.6 672.7h-52.5v-54.5c0-19.4-14.9-35.1-33.2-35.1s-33.2 15.7-33.2 35.1v54.5h-53.6c-18.3 0-33.2 15.7-33.2 35.1 0 19.4 14.9 35.1 33.2 35.1h53.6v53.9c0 19.4 14.9 35.1 33.2 35.1s33.2-15.7 33.2-35.1v-53.9h52.5c18.3 0 33.2-15.7 33.2-35.1 0-19.4-14.9-35.1-33.2-35.1zM256.5 416.1c-35 0-63.6-28.5-63.6-63.6 0-35 28.5-63.6 63.6-63.6s63.6 28.5 63.6 63.6-28.6 63.6-63.6 63.6z m0-89.2c-14.1 0-25.6 11.5-25.6 25.6 0 14.1 11.5 25.6 25.6 25.6 14.1 0 25.6-11.5 25.6-25.6 0-14.1-11.5-25.6-25.6-25.6z"
                  p-id="7624"></path>
            </svg>
            <svg t="1742233400045" class="icon emoji-icon" viewBox="0 0 1024 1024" version="1.1"
                 xmlns="http://www.w3.org/2000/svg"
                 p-id="8686" width="24" height="24">
              <path
                  d="M512 78.592a433.408 433.408 0 1 0 287.5392 757.76 25.6 25.6 0 0 0-33.9968-38.2976 381.4912 381.4912 0 1 1 71.168-84.2752 25.6 25.6 0 1 0 43.4176 27.0848A433.4592 433.4592 0 0 0 512 78.592z"
                  fill="#272636" p-id="8687"></path>
              <path
                  d="M366.848 449.8944A40.2944 40.2944 0 1 0 326.5536 409.6a40.3456 40.3456 0 0 0 40.2944 40.2944zM656.0256 449.8944a40.2944 40.2944 0 1 0-40.3456-40.2944 40.3456 40.3456 0 0 0 40.3456 40.2944zM328.0896 653.1072a206.6432 206.6432 0 0 0 367.8208 0A25.6 25.6 0 0 0 650.24 629.76a155.392 155.392 0 0 1-276.48 0 25.6 25.6 0 0 0-45.568 23.3472z"
                  fill="#272636" p-id="8688"></path>
            </svg>
          </a-flex>
          <button :disabled="disabled" @click="emit('onClick')" type="button" class="comment-btn">评论</button>
        </a-flex>
      </a-flex>
    </div>
  </div>
</template>

<style scoped>
.main-input {
  margin-top: 0;
  margin-bottom: 24px;
}

.comment-wrapper {
  position: relative;
  border-radius: 8px;
  padding: 16px;
  background: #f8f8f8;
}

.comment-input {
  display: block;
  border: 0;
  padding: 0;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  width: 100%;
  height: auto;
  min-height: 24px;
  max-height: 72px;
  overflow-y: scroll;
  font-size: 16px;
  line-height: 24px;
  color: #222;
  outline: none;
  resize: none;
  background: #f8f8f8;
  -webkit-transition: height .3s ease-in-out;
  -moz-transition: height .3s ease-in-out;
  transition: height .3s ease-in-out;
  word-break: break-all;
}

.comment-btn {
  border: none;
  border-radius: 4px;
  padding: 6px 18px;
  font-size: 14px;
  font-weight: 500;
  line-height: 20px;
  color: #fff;
  background: #505050;
  cursor: pointer;
}

button:disabled {
  opacity: 0.3 !important;
  cursor: default;
  pointer-events: none; /* 禁用鼠标交互 */
}

.comment-btn:hover {
  opacity: .8;
  cursor: default;
}

button, input {
  outline: none;
}

.emoji-icon {
  width: 24px;
  height: 24px;
  cursor: pointer;
}
</style>
