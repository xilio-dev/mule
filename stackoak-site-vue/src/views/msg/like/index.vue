<script setup lang="ts">
/*------------------------------------变量定义------------------------------------------*/



/*------------------------------------生命周期-------------------------------------------*/



/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/



/*------------------------------------核心业务--------------------------------------------*/




/*-------------------------------------其他函数-------------------------------------------*/
import {onMounted, ref, nextTick} from 'vue';

const count = 3;
const fakeDataUrl = `https://randomuser.me/api/?results=${count}&inc=name,gender,email,nat,picture&noinfo`;

const initLoading = ref(true);
const loading = ref(false);
const data = ref([]);
const list = ref([]);
onMounted(() => {
  fetch(fakeDataUrl)
      .then(res => res.json())
      .then(res => {
        initLoading.value = false;
        data.value = res.results;
        list.value = res.results;
      });
});

const onLoadMore = () => {
  loading.value = true;
  list.value = data.value.concat(
      [...new Array(count)].map(() => ({loading: true, name: {}, picture: {}})),
  );
  fetch(fakeDataUrl)
      .then(res => res.json())
      .then(res => {
        const newData = data.value.concat(res.results);
        loading.value = false;
        data.value = newData;
        list.value = newData;
        nextTick(() => {
          // Resetting window's offsetTop so as to display react-virtualized demo underfloor.
          // In real scene, you can using public method of react-virtualized:
          // https://stackoverflow.com/questions/46700726/how-to-use-public-method-updateposition-of-react-virtualized
          window.dispatchEvent(new Event('resize'));
        });
      });
};
</script>

<template>
  <a-list
      :loading="initLoading"
      item-layout="horizontal"
      :data-source="list"
  >
    <template #loadMore>
      <div
          v-if="!initLoading && !loading"
          :style="{ textAlign: 'center', marginTop: '12px', height: '32px', lineHeight: '32px' }"
      >
        <a-button @click="onLoadMore">加载更多</a-button>
      </div>
    </template>
    <template #renderItem="{ item }">
      <a-list-item>
        <template #actions>
           <a-flex vertical justify="start">
             <div>2022-02-23</div>
             <a-button size="small" type="link" style="color: #8a919f">删除</a-button>
           </a-flex>
        </template>
        <a-skeleton avatar :title="false" :loading="!!item.loading" active>
          <a-list-item-meta
              description="SQLServer 密码验证登录18456错误解决方案"
          >
            <template #title>
              <a href="https://www.antdv.com/">{{ item.name.last }}</a>
              <a-tag :bordered="false" color="blue">粉丝</a-tag>
              <span>收藏了你的文章</span>
            </template>
            <template #avatar>
              <a-avatar :src="item.picture.large"/>
            </template>
          </a-list-item-meta>

        </a-skeleton>
      </a-list-item>
    </template>
  </a-list>
</template>

<style scoped>

</style>
