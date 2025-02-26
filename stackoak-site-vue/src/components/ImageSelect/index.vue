<script lang="ts" setup>
import {ref, computed} from 'vue';
import {ImageUtils} from "@/utils/file.ts";

const props = defineProps({
  items: {
    type: Array,
    required: true
  },
  multi: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['selectChange'])
const itemList = ref([])
itemList.value = props.items
const isSingleSelect = ref(true); // 是否为单选模式，默认为单选
isSingleSelect.value = !props.multi
const selectedOrder = ref([]);
// 切换选中状态
const toggleChecked = (item: { id: string; imgUrl: string; checked: boolean }) => {
  const index = selectedOrder.value.findIndex(selected => selected.id === item.id);
  if (item.checked) {
    // 如果当前项已选中，点击时取消选中
    if (index !== -1) {
      // 移除当前项
      selectedOrder.value.splice(index, 1);
      item.checked = false;
      // 重新调整所有后续选中项的序号
      selectedOrder.value = selectedOrder.value.map((entry, newIndex) => ({
        ...entry,
        order: newIndex
      }));
    }
  } else {
    // 如果当前项未选中，添加到选中列表
    if (!props.multi) {
      // 单选模式：清除其他所有选中状态
      itemList.value.forEach(i => (i.checked = false));
      selectedOrder.value = [];
    }
    // 添加当前项到选中列表
    selectedOrder.value.push({order: selectedOrder.value.length,...item});
    item.checked = true;
  }
  emit('selectChange', selectedOrder.value)
};

// 获取选中的顺序编号
const getSelectedIndex = (item: { id: string }) => {
  const selected = selectedOrder.value.find(selected => selected.id === item.id);
  return selected ? selected.order : -1;
};
const gridStyle = computed(() => ({
  display: 'grid',
  gap: '12px',
  gridTemplateColumns: `repeat(auto-fill, minmax(116px, 1fr))` // 动态计算每行的列数
}));
const clear=  ()=>{
  itemList.value.forEach(i => (i.checked = false));
  selectedOrder.value = [];
}
//一定要在声明后用
defineExpose({clear})
</script>

<template>
  <div class="container" style="padding-left: 12px; padding-right: 12px;">
    <!-- 图片列表 -->
    <div class="grid-container" :style="gridStyle">
      <div
          class="resource-item"
          @click="toggleChecked(item)"
          v-for="(item, index) in items"
          :key="item.id">
        <div class="resource-item-img" :class="{ checked: item.checked }">
          <div class="pop-layer" v-if="item.checked">
            <div class="pop-number">{{ getSelectedIndex(item) + 1 }}</div>
            <div class="pop-bg"></div>
          </div>
          <img :src="item.imgUrl" class="img-span">
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%; /* 确保容器宽度占满父容器 */
  padding-left: 12px;
  padding-right: 12px;
}

.grid-container {
  width: 100%; /* 确保网格容器宽度占满父容器 */
  display: grid;
  gap: 12px; /* 项目之间的间距 */
}

.resource-item {
  width: 116px;
  height: 116px;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  overflow: hidden;
  position: relative;
  cursor: pointer;
  background-color: #fafafa;
}

.resource-item-img {
  width: 100%;
  height: 100%;
  position: relative;
}

.checked {
  border-color: #409eff;
}

.pop-layer {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  pointer-events: none;
}

.pop-number {
  position: absolute;
  left: 8px;
  top: 8px;
  color: #fff;
  background: #ff5e5e;
  width: 24px;
  height: 24px;
  line-height: 24px;
  border-radius: 12px;
  text-align: center;
  font-size: 14px;
}

.pop-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
}

.img-span {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
