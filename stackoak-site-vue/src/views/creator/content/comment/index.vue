<script setup lang="ts">

/*------------------------------------变量定义------------------------------------------*/
import CommentInput from "@/components/CommentInput/index.vue";

const activeTab = ref('1');
const activeCommentStatusTab = ref('1');
const curCommentItem = ref()/*当前打开的评论项*/
const commentBody = ref({
  commentId: '',
  content: ''
})

/*------------------------------------生命周期-------------------------------------------*/



/*------------------------------------初始化---------------------------------------------*/
const data = ref([
  {id: "1001", content: '的时代'},
  {id: "1002", content: 's得到的'},
  {id: "1003", content: '单身狗'},
  {id: "1004", content: '颠三倒四'},
])


/*------------------------------------数据加载--------------------------------------------*/



/*------------------------------------核心业务--------------------------------------------*/
//打开或关闭评论回复框
const toggleReply = (item: any) => {
  //如果当前打开的是上次已经打开的项则关闭
  if (curCommentItem.value == item && item.isOpen) {
    item.isOpen = false
    curCommentItem.value = null
    return
  }
  if (curCommentItem.value) {
    curCommentItem.value.isOpen = false/*关闭上一个*/
  }
  commentBody.value.commentId = item.id/*设置当前父评论ID*/
  commentBody.value.content = ''/*清空内容*/
  item.isOpen = true/* 打开新的评论项*/
  curCommentItem.value = item
}
//删除评论
const onRemoveComment = (item: any) => {

}
//回复用户的评论
const onReplyComment = (item: any) => {

}
//点赞评论
const onDiggComment = (item: any) => {

}

/*-------------------------------------其他函数-------------------------------------------*/


import dayjs from 'dayjs';
import {LikeFilled, LikeOutlined, DislikeFilled, DislikeOutlined} from '@ant-design/icons-vue';
import {ref} from 'vue';
import relativeTime from 'dayjs/plugin/relativeTime';

dayjs.extend(relativeTime);

const likes = ref<number>(0);
const dislikes = ref<number>(0);
const action = ref<string>();

const like = () => {
  likes.value = 1;
  dislikes.value = 0;
  action.value = 'liked';
};

const dislike = () => {
  likes.value = 0;
  dislikes.value = 1;
  action.value = 'disliked';
};
</script>

<template>
  <a-card :bordered="false">
    <a-tabs v-model:activeKey="activeTab">
      <a-tab-pane key="1" tab="文章评论">
        <a-tabs v-model:activeKey="activeCommentStatusTab">
          <a-tab-pane key="1" tab="评论我的">
            <a-comment v-for="item in data">
              <template #actions>
                  <span key="comment-basic-like">
                    <a-tooltip title="Like">
                      <template v-if="action === 'liked'">
                        <LikeFilled @click="like"/>
                      </template>
                      <template v-else>
                        <LikeOutlined @click="like"/>
                      </template>
                    </a-tooltip>
                    <span style="padding-left: 8px; cursor: auto">
                      {{ likes }}
                    </span>
                      </span>
                <span key="comment-basic-dislike">
        <a-tooltip title="Dislike">
          <template v-if="action === 'disliked'">
            <DislikeFilled @click="dislike"/>
          </template>
          <template v-else>
            <DislikeOutlined @click="dislike"/>
          </template>
        </a-tooltip>
        <span style="padding-left: 8px; cursor: auto">
          {{ dislikes }}
        </span>
      </span>
                <span @click="toggleReply(item)" key="comment-basic-reply-to">回复</span>
              </template>
              <template #author><a>小虎开发</a></template>
              <template #avatar>
                <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo"/>
              </template>
              <template #content>
                <p>
                  不太懂啊，老哥，可以帮忙一下吗
                </p>

                <CommentInput   v-if="item.isOpen" class="comment-container" placeholder="说点什么吧"
                              ref="commentInputRef"
                              :disabled="commentBody.content==''" v-model:value="commentBody.content"/>
              </template>
              <template #datetime>
                <a-tooltip :title="dayjs().format('YYYY-MM-DD HH:mm:ss')">
                  <span>{{ dayjs().fromNow() }}</span>
                </a-tooltip>
              </template>
            </a-comment>
          </a-tab-pane>
          <a-tab-pane key="2" tab="我发布的评论">
            <a-comment v-for="item in data">
              <template #actions>
                <span>删除评论</span>
              </template>
              <template #author><a>小虎开发</a></template>
              <template #avatar>
                <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo"/>
              </template>
              <template #content>
                <p>
                  不太懂啊，老哥，可以帮忙一下吗
                </p>
              </template>
              <template #datetime>
                <a-tooltip :title="dayjs().format('YYYY-MM-DD HH:mm:ss')">
                  <span>{{ dayjs().fromNow() }}</span>
                </a-tooltip>
              </template>
            </a-comment>
          </a-tab-pane>
        </a-tabs>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<style scoped>
/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}

.comment-container{
  margin-top: 10px;
}
</style>
