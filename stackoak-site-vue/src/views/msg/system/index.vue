<script setup lang="ts">
/*------------------------------------变量定义------------------------------------------*/



/*------------------------------------生命周期-------------------------------------------*/



/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/



/*------------------------------------核心业务--------------------------------------------*/




/*-------------------------------------其他函数-------------------------------------------*/
import {onMounted, ref, nextTick} from 'vue';

const count = 6;
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
          <span>2022-02-23</span>
        </template>
        <a-skeleton avatar :title="false" :loading="!!item.loading" active>
          <a-list-item-meta>
            <template #description>
              <span>
                 文章内容: 内容格式有误，建议自查是否带有非主体类少数民族文字/外文，可新增译文
              </span>
            </template>
            <template #title>
              你的文章 <a style="color: green">是的是的</a> 未通过审核， 详细规则请见
              <a style="color: green" href="https://www.antdv.com/">社区规范</a>
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
