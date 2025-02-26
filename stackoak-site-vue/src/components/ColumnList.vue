<script setup lang="ts">
import { PlusOutlined } from '@ant-design/icons-vue';
import cover from '@/assets/avatar.jpeg';
</script>

<template>
  <div
      v-for="(item, index) in 3"
      :key="index"
      :class="{ 'border-bottom': index !== 9 }"
      class="row-item">
    <a-flex justify="space-between" align="stretch">
      <a-image
          style="width: 150px; height: 80px"
          :src="cover"
      />
      <div
          style="flex: 1; display: flex; flex-direction: column; justify-content: space-between;margin-left: 10px; margin-right: 15px">
        <a-flex justify="space-between" align="center">
          <div class="column-title">并发编程</div>
          <div>
            <a-button type="primary" size="small">
              <template #icon>
                <PlusOutlined />
              </template>
              已订阅</a-button>
          </div>
        </a-flex>
        <div class="column-desc">
          并发编程是非常有意思的学科并发编程是非常有意思的学科并发编程是非常有意思的学科并发编程是非常有意思的学科
        </div>
        <a-flex gap="small" class="column-info">
          <div>2023-02-25</div>
          <div>文章数 2563</div>
          <div>订阅人数 456156</div>
        </a-flex>
      </div>
    </a-flex>
  </div>
</template>

<style scoped>
.row-item {
  cursor: pointer;
  margin-bottom: 10px; /* 行间距 */
  padding-bottom: 5px; /* 调整线条位置 */
  transition: background-color 0.3s ease; /* 添加过渡效果 */
}
.row-item:hover{
  background-color: #F2F6FC;
}
.border-bottom {
  border-bottom: 1px solid #f1eeee; /* 添加灰色边框 */
}
.column-title {
  font-size: 16px;
  line-height: 24px;
  color: #1d2129;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.column-desc {
  max-width: 580px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #4e5969;
  font-weight: 400;
  font-size: 14px;
}

.column-info {
  font-weight: 400;
  font-size: 14px;
  color: #86909c;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
}
</style>
