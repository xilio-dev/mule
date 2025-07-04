<template>
  <div class="tag-container">
    <template v-for="(tag, index) in tagState.tags" :key="tag">
      <a-tooltip v-if="tag.length > maxLength" :title="tag">
        <a-tag :closable="true" @close="handleClose(tag)" @dblclick="showInput">
          {{ `${tag.slice(0, maxLength)}...` }}
        </a-tag>
      </a-tooltip>
      <a-tag v-else :closable="true" @close="handleClose(tag)" @dblclick="showInput">
        {{ tag }}
      </a-tag>
    </template>
    <a-popover
        style="z-index: 20"
        v-if="tagState.tags.length < maxTags"
        v-model:visible="tagState.inputVisible"
        placement="bottomLeft"
        trigger="click"
        @click="showInput"
    >
      <template #content>
        <div style="width: 400px;height: 150px;overflow-y: auto">
          <a-flex vertical :gap="2" style="margin: 0 15px"  >
            <a-input
                ref="inputRef"
                v-model:value="tagState.inputValue"
                type="text"
                size="medium"
                @blur="handleInputConfirm"
                @keyup.enter="handleInputConfirm"
                :placeholder="`请输入${label}，按回车键确认添加`"
            />
            <div>
              <a-checkable-tag
                  style="margin-top: 8px;"
                  v-for="item in selectItems"
                  :key="item.name"
                  v-model:checked="item.checked"
                  @change="() => handleChanges(item)">
                {{ item.name }}
              </a-checkable-tag>
            </div>
          </a-flex>

        </div>


      </template>
      <a-tag style="background: #fff; border-style: dashed">
        <plus-outlined/>
        添加{{ label }}
      </a-tag>
    </a-popover>
    <a-tag v-else style="background: #fff; border-style: dashed">
      <info-circle-outlined/>
      {{ maxTagsTip }}
    </a-tag>
  </div>
</template>

<script setup>
import {ref, reactive, nextTick, watch} from 'vue';
import {PlusOutlined, InfoCircleOutlined} from '@ant-design/icons-vue';
import {message} from 'ant-design-vue';

const props = defineProps({
  label: {
    type: String,
    default: '标签',
  },
  name: {
    type: String,
    default: 'tags',
  },
  maxTags: {
    type: Number,
    default: 4,
  },
  maxLength: {
    type: Number,
    default: 5,
  },
  inputWidth: {
    type: String,
    default: '300px',
  },
  modelValue: {
    type: Array,
    default: () => [],
  },
  selectItems: {
    type: Array,
    default: () => []
  }
});

const emit = defineEmits(['update:modelValue']);
const inputRef = ref();
const tagState = reactive({
  tags: [],
  inputVisible: false,
  inputValue: '',
});
// 处理标签的选中状态变化
const handleChanges = (tag) => {
  if (tag.checked) {
    // 如果选中，添加到已选择的标签列表
    tagState.tags.push(tag.name);
    emit('update:modelValue', tagState.tags);
  } else {
    // 如果取消选中，从已选择的标签列表中移除
    handleClose(tag.name)
  }
};
// 监听父组件传入的 modelValue 变化，同步到本地状态
watch(() => props.modelValue, (newValue) => {
  tagState.tags = newValue;
}, {immediate: true});

const handleClose = (removedTag) => {
  tagState.tags = tagState.tags.filter((tag) => tag !== removedTag);
  const item = props.selectItems.find(item => item.name === removedTag);
  if (item) {
    item.checked = false;
  }
  emit('update:modelValue', tagState.tags);
};

const showInput = () => {
  if (tagState.tags.length >= props.maxTags) {
    return;
  }
  tagState.inputVisible = true;
  nextTick(() => {
    inputRef.value.focus();
  });
};
const handleInputConfirm = () => {
  const inputValue = tagState.inputValue.trim();
  if (inputValue.length > props.maxLength) {
    message.warning(`${props.label}最多只能输入 ${props.maxLength} 个字`);
    tagState.inputValue = ''; // 清空输入框
    return;
  }
  if (inputValue && !tagState.tags.includes(inputValue)) {
    tagState.tags.push(inputValue);
    emit('update:modelValue', tagState.tags);
  }
  tagState.inputValue = '';
};

const maxTagsTip = `${props.label}最多添加 ${props.maxTags} 个`;
</script>

<style scoped>
.tag-container {
  display: flex;
  flex-wrap: wrap;
}
</style>
