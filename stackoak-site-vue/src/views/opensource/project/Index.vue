<template>

    <a-row style="margin-bottom: 15px;width: 100%">
      <a-card style="width: 100%;">
        <a-tag>全部</a-tag>
      </a-card>
    </a-row>
    <a-row style="width: 100%;background-color: white">
      <a-list item-layout="horizontal" :data-source="data" style="width: 100%">
        <template #renderItem="{ item }">
          <a-list-item class="a-list-item">
              <a-list-item-meta
                  description="一款大数据开发的系统，具有丰富的功能，可以实现各种开发场景测试">
                <template #title>
                  <span>{{ item.title }}</span>
                </template>
                <template #avatar>
                  <a-avatar shape="square" size="large" style="background-color: rgb(241, 241, 255);">
                    <span style="color: black;font-size: 20px">{{ item.id }}</span>
                  </a-avatar>
                </template>
              </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </a-row>

</template>
<script lang="ts" setup>
import Navbar from "@/components/Navbar.vue";

interface DataItem {
  title: string;
}

const data: DataItem[] = [
  {
    id: 1,
    title: '深度学习系统',
  },
  {
    id: 2,
    title: '达模型',
  },
  {
    id: 3,
    title: '风控系统v1',
  },
  {
    id: 4,
    title: '大数据系统',
  },
];
</script>

<style scoped>
main {
  padding: 60px 12% 0;
}

a-row {
  width: 100%;
}
:deep(.ant-card .ant-card-body ){
  padding: 12px;
  border-radius: 0 0 8px 8px;
}
.a-list-item:hover{
  transition: box-shadow 0.3s ease;
  background-color: #f4f6f9;
}
</style>

