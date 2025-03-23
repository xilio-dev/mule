<script setup lang="ts">
import dayjs from 'dayjs';
import {LikeFilled, LikeOutlined} from '@ant-design/icons-vue';
import {onMounted, reactive, ref} from 'vue';
import relativeTime from 'dayjs/plugin/relativeTime';
import {commentListS} from "@/api/comment.ts";
import CommentInput from "@/components/CommentInput/index.vue";
import {API} from "@/api/ApiConfig.ts";
import {Https} from "@/api/https.ts";
import {message} from "ant-design-vue";

dayjs.extend(relativeTime);
/*------------------------------------变量定义------------------------------------------*/
const activeTab = ref('1');
const activeCommentStatusTab = ref('1');
const curCommentItem = ref()/*当前打开的评论项*/
const comments = reactive([])
const commentValue = ref('')
const commentInputRef = ref()
/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadComment()
})
/*------------------------------------数据加载--------------------------------------------*/
//加载评论消息
const loadComment = async () => {
  const res = await commentListS({current: 1, size: 200})
  //@ts-ignore
  comments.splice(0, comments.length, ...(res.records ?? []))
}
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
  commentValue.value = ''/*清空内容*/
  item.isOpen = true/* 打开新的评论项*/
  curCommentItem.value = item
}
//添加评论
const onReplyComment = (item: any) => {
  if (commentValue.value && commentValue.value.length > 0) {
    Https.action(API.COMMENT.add, {
      aid: item.articleId,
      commentPid: item.id,
      content: commentValue.value
    }).then(res => {
      commentValue.value = ''
      message.info("已回复")
      loadComment()
    })
  }
}
//评论点赞/取消点赞
const onDiggComment = (comment: any) => {
  if (comment.liked === 1) {
    Https.action(API.COMMENT.unDigg, {commentId: comment.id}).then(res => {
      comment['liked'] = 0
      message.info("取消点赞")
    })
  } else {
    Https.action(API.COMMENT.digg, {commentId: comment.id}).then(res => {
      comment['liked'] = 1
      message.success("点赞")
    })
  }
}
//删除评论
const onDeleteComment = (item: any) => {
  Https.action(API.COMMENT.deletes, {aid: item.articleId, commentId: item.id}).then(res => {
    message.success("删除成功！")
     loadComment()//todo 待优化
  })
}
/*-------------------------------------其他函数-------------------------------------------*/
</script>

<template>
  <a-card :bordered="false">
    <a-tabs v-model:activeKey="activeTab">
      <a-tab-pane key="1" tab="文章评论">
        <a-tabs v-model:activeKey="activeCommentStatusTab">
          <a-tab-pane key="1" tab="评论我的">
            <a-comment v-for="item in comments" :key="item.id">
              <template #actions>
                <span @click="toggleReply(item)" key="comment-basic-reply-to">回复</span>
                <span key="comment-basic-like">
                    <a-tooltip title="Like">
                      <template v-if="item['liked'] ==1">
                        <LikeFilled @click="onDiggComment(item)"/>
                      </template>
                      <template v-else>
                        <LikeOutlined @click="onDiggComment(item)"/>
                      </template>
                    </a-tooltip>
                    <span style="padding-left: 8px; cursor: auto">
                      {{ item.likeCount }}
                    </span>
                      </span>
                <span @click="onDeleteComment(item)">删除</span>
              </template>
              <template #author><a>{{ item.nickname }}</a></template>
              <template #avatar>
                <a-avatar :src="item.avatar" :alt="item.nickname"/>
              </template>
              <template #content>
                <p>
                  {{ item.content }}
                </p>

                <CommentInput @onClick="onReplyComment(item)" v-if="item.isOpen" class="comment-container"
                              placeholder="说点什么吧"
                              ref="commentInputRef"
                              :disabled="commentValue==''" v-model:value="commentValue"/>
              </template>
              <template #datetime>
                <a-tooltip :title="dayjs().format('YYYY-MM-DD HH:mm:ss')">
                  <span>{{ dayjs().from(item.createdAt) }}</span>
                </a-tooltip>
              </template>
            </a-comment>
          </a-tab-pane>
          <a-tab-pane key="2" tab="我发布的评论">
            <a-comment v-for="item in comments">
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

.comment-container {
  margin-top: 10px;
}
</style>
