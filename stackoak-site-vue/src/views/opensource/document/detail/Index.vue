<!--<template>-->
<!--  <div>-->
<!--    <h1>文档树</h1>-->
<!--    <DocumentTree :nodes="nodes" />-->
<!--  </div>-->
<!--</template>-->

<!--<script setup lang="ts">-->
<!--import DocumentTree from '@/components/DocTree/index.vue';-->
<!--import {ref} from "vue";-->
<!--const nodes =ref( [-->
<!--  {-->
<!--    id: 1,-->
<!--    name: 'Node 1',-->
<!--    isOpen: false, // 初始化 isOpen-->
<!--    children: [-->
<!--      {-->
<!--        id: 2,-->
<!--        name: 'Node 1.1',-->
<!--        isOpen: false, // 初始化 isOpen-->
<!--        children: [-->
<!--          {-->
<!--            id: 2.2,-->
<!--            name: 'Node 1.1.2',-->
<!--            isOpen: false, // 初始化 isOpen-->
<!--            children: [-->
<!--              {-->
<!--                id: 2.2,-->
<!--                name: 'Node 1.1.2',-->
<!--                isOpen: false, // 初始化 isOpen-->
<!--                children: []-->
<!--              },-->
<!--              {-->
<!--                id: 2.2,-->
<!--                name: 'Node 1.1.2',-->
<!--                isOpen: false, // 初始化 isOpen-->
<!--                children: []-->
<!--              }-->
<!--            ]-->
<!--          }-->
<!--        ]-->
<!--      }-->
<!--    ]-->
<!--  },-->
<!--  {-->
<!--    id: 3,-->
<!--    name: 'Node 2',-->
<!--    isOpen: false, // 初始化 isOpen-->
<!--    children: [-->
<!--      {-->
<!--        id: 2.2,-->
<!--        name: 'Node 1.1.2',-->
<!--        isOpen: false, // 初始化 isOpen-->
<!--        children: []-->
<!--      },-->
<!--      {-->
<!--        id: 2.2,-->
<!--        name: 'Node 1.1.2',-->
<!--        isOpen: false, // 初始化 isOpen-->
<!--        children: []-->
<!--      }-->
<!--    ]-->
<!--  }-->
<!--])-->
<!--</script>-->

<template>
  <div>
    <h1>文档树</h1>
    <DocumentTree2 :nodes="menuData"/>
  </div>
</template>

<script setup lang="ts">
import DocumentTree2 from '@/components/DocTree2/index.vue';
import {ref} from "vue";

const menuData = ref([
  {
    id: 1,
    label: "操作指南",
    isExpanded: false,
    children: [
      {id: 1.1, label: "快速开始"},
      {id: 1.2, label: "遥控器按键说明"},
      {id: 1.3, label: "遥控器按键说明3"},
    ],
  },
  {
    id: 2,
    label: "网络安全",
    isExpanded: false,
    children: [
      {
        id: 2.1, label: "网络安全2", children:
            [
              {
                id: 2.11,
                label: "网络安全2.2",
                isExpanded: false,
              }
            ]
      },
      {id: 2.2, label: "网络安全3"},
    ],
  },
]);
const nodes = ref([
  {
    id: 1,
    name: 'Node 1',
    isOpen: false, // 初始化 isOpen
    children: [
      {
        id: 2,
        name: 'Node 1.1',
        isOpen: false, // 初始化 isOpen
        children: [
          {
            id: 2.2,
            name: 'Node 1.1.2',
            isOpen: false, // 初始化 isOpen
            children: [
              {
                id: 2.2,
                name: 'Node 1.1.2',
                isOpen: false, // 初始化 isOpen
                children: []
              },
              {
                id: 2.2,
                name: 'Node 1.1.2',
                isOpen: false, // 初始化 isOpen
                children: []
              }
            ]
          }
        ]
      }
    ]
  },
  {
    id: 3,
    name: 'Node 2',
    isOpen: false, // 初始化 isOpen
    children: [
      {
        id: 2.2,
        name: 'Node 1.1.2',
        isOpen: false, // 初始化 isOpen
        children: []
      },
      {
        id: 2.2,
        name: 'Node 1.1.2',
        isOpen: false, // 初始化 isOpen
        children: []
      }
    ]
  }
])
</script>



