<script setup lang="ts">
import {QuestionCircleOutlined, CustomerServiceOutlined} from "@ant-design/icons-vue";
import {ref, onMounted, reactive, computed} from 'vue';
import Markdown from "@/components/Markdown/index.vue";
import {addToFavor, diggArticle, postDetail} from "@/api/post.ts";
import {useRoute} from "vue-router";
import {useUserStore} from '@/store';
import router from "@/router";
import {NumberUtils} from "@/utils/number-util.ts";
import {message} from "ant-design-vue";
import Login from "@/components/Login.vue";
import {addComment, commentList, deleteComment, diggComment, unDiggComment} from "@/api/comment.ts";
import CommentInput from '@/components/CommentInput/index.vue'
import UserInfoCard from '@/components/UserInfoCard/index.vue'
import {followUser, unFollowUser} from "@/api/user.ts";
import {CommonUtil} from "@/utils/common.ts";
import {ImageUtils} from "@/utils/file.ts";
import {API} from "@/api/ApiConfig.ts";
import {Https} from "@/api/https.ts";
import type {Rule} from "ant-design-vue/es/form";
/*------------------------------------变量定义------------------------------------------*/
const openCommentDrawer = ref(false)
const useUser = useUserStore()
const route = useRoute()
const articleInfo = ref({})
const userInfo = ref({})
const tags = ref({})
const category = ref({})
const userInteract = reactive({})
const config = ref({})
const commentInputRef = ref()
const needVisitPass = ref(false)
//打开登陆
const openLoginModal = ref(false)
const openCollectModel = ref(false)/*打开收藏夹模态框*/
const collectList = reactive([])/*访问者收藏夹列表*/
const isLoading = ref(true); // 加载状态
const comments = reactive([]);
const commentValue = ref('')
const pid = ref("0")/*依赖的评论，0表示根评论*/
const activeColumnKey = ref('1')
const openReportModel = ref(false)/*打开举报模态框*/
const openNewCollectModel = ref(false)/*打开举报模态框*/
//判断文章是否至少被收藏到一个收藏夹
const hasCollected = computed(() => {
  return collectList.filter(item => item.isCollect).length > 0;
});
const collectLoading = ref(false);
const newCollectFormRef = ref();
const newCollectForm = reactive({
  name: '',
  description: '',
  status: 1
});
const newCollectRules: Record<string, Rule[]> = {
  name: [
    {required: true, message: '请输入收藏夹名字', trigger: 'change'},
    {min: 0, max: 8, message: '不能超过8个字', trigger: 'change'},
  ]
}
/*------------------------------------生命周期-------------------------------------------*/
onMounted(async () => {
  await fetchPostData();
  await loadComments()
});


/*------------------------------------初始化---------------------------------------------*/




/*------------------------------------数据加载--------------------------------------------*/
const fetchPostData = async () => {
  try {
    const res = await postDetail({id: route.params.id})
    if (res) {
      articleInfo.value = res.articleInfo || {}
      userInfo.value = res.userInfo || {}
      tags.value = res.tags || []
      category.value = res.category || {}
      Object.assign(userInteract, res.userInteract || {})
      config.value = res.config || {}
      isLoading.value = false
    }
  } catch (err) {
    isLoading.value = false
  }
}

const loadComments = async () => {
  if (articleInfo.value) {
    const res = await commentList({aid: articleInfo.value.id})
    comments.splice(0, comments.length, ...(res.records ?? []));
  }
}
//加载访问者的收藏夹列表
const loadCollects = async (current: number = 1, size: number = 1) => {
  try {
    const res = await Https.action(API.COLLECT.visit_collect, {current: current, size: size, id: articleInfo.value.id})
    //@ts-ignore
    collectList.splice(0, collectList.length, ...(res.records ?? []))
    collectPagination.total = res.total
    //collectList.value=res.records||[]
    collectLoading.value = false
  } finally {
    collectLoading.value = false
  }
}
/*------------------------------------核心业务--------------------------------------------*/
//文章点赞或取消点赞
const onDiggOrunDigg = () => {
  if (!useUser.isLogin()) {
    openLoginModal.value = true
    return
  }
  //如果之前是点赞状态则取消点赞
  if (userInteract.isLike) {
    Https.action(API.ARTICLE.unDigg, {aid: articleInfo.value.id}).then(res => {
      articleInfo.value.likeCount -= 1
      message.success("取消点赞")
    })
  } else {
    Https.action(API.ARTICLE.digg, {aid: articleInfo.value.id}).then(res => {
      articleInfo.value.likeCount += 1
      message.success("点赞")
    })
  }
  userInteract.isLike = !userInteract.isLike
}
//添加文章到收藏夹或从收藏夹取消收藏
const onSaveArticleToCollect = (item: object) => {
  if (item.isCollect) {
    Https.action(API.COLLECT.remove_article_from_collect, {
      aid: articleInfo.value.id,
      ids: [item.id]
    }).then(res => {
      message.success("已取消")
      item.isCollect = false
      //如果文章没有被任何收藏夹收藏才将其悬浮按钮设置为未收藏状态
      userInteract.isCollect = hasCollected

    })
  } else {
    Https.action(API.COLLECT.add_article_to_collect, {aid: articleInfo.value.id, ids: [item.id]}).then(res => {
      message.success("已收藏")
      item.isCollect = true
      //如果文章没有被任何收藏夹收藏才将其悬浮按钮设置为未收藏状态
      userInteract.isCollect = hasCollected
    })
  }

}
//删除评论
const onDeleteComment = (commentId: string) => {
  deleteComment({commentId: commentId}).then(res => {
    message.success("删除成功！")
    loadComments()
  })
}
//评论点赞/取消点赞
const onDiggComment = (comment: any) => {
  if (comment.liked === 1) {
    unDiggComment({commentId: comment.id}).then(res => {
      comment['liked'] = 0
      articleInfo.value.likeCount -= 1
      message.info("取消点赞")
    })
  } else {
    diggComment({commentId: comment.id}).then(res => {
      comment['liked'] = 1
      articleInfo.value.likeCount += 1
      message.success("点赞")
    })
  }
}

//添加评论
const onAddComment = () => {
  if (pid.value && commentValue.value.length > 0) {
    addComment({
      aid: articleInfo.value.id,
      content: commentValue.value,
      commentPid: pid.value
    }).then(res => {
      commentValue.value = ''
      commentInputRef.value.blur()
      loadComments()
    })
  }
}
//去回复时，设置依赖的评论
const toApply = (comment: string) => {
  pid.value = comment.id
  commentValue.value = ''
  commentInputRef.value.focus()
}
//关注和取消关注
const onToggleFollow = async (isFollow: boolean) => {
  userInteract.isFollow = !isFollow/*切换关注状态*/
  userInfo.value.fansCount = !isFollow ? userInfo.value.fansCount + 1 : userInfo.value.fansCount - 1/*粉丝数变更*/
  //已经关注，取消关注
  if (isFollow) {
    await unFollowUser(userInfo.value.userId)
  } else {
    //如果未关注，执行关注
    await followUser(userInfo.value.userId)
  }
}
//新建收藏夹
const onNewCollect = () => {
  newCollectFormRef.value
      .validate()
      .then(() => {
        Https.action(API.COLLECT.save, newCollectForm).then(res => {
          loadCollects()
          openNewCollectModel.value = false
        })
      })
}
/*-------------------------------------其他函数-------------------------------------------*/
//如果用户已经登陆，而且是自己的文章，那么可以去编辑
const onToEditEditor = (id: string) => {
  router.push({path: '/editor', query: {id: id}});
}
const collectPagination = {
  onChange: (page: number) => {
    loadCollects(page, collectPagination.pageSize)
  },
  total: 0,
  pageSize: 6,
};
const onOpenCollect = () => {
  if (!useUser.isLogin()) {
    openLoginModal.value = true
    return
  }
  openCollectModel.value = true
  loadCollects(1, collectPagination.pageSize)
}
</script>

<template>

  <a-flex v-if="needVisitPass" align="center" justify="center" style="width: 100%;margin-top: 8%">
    <a-flex vertical align="center" :gap="15">
      <a-image src="/suo.svg" style="width: 100px;height: 100px;" :preview="false"/>
      <div style="font-size: 18px;color: gray">请输入访问密码</div>
      <a-flex vertical>
        <a-input style="width: 220px" v-model:value="visitPass" placeholder="请输入密码"></a-input>
        <a-button @click="needVisitPass=false" style="width: 220px;margin-top: 10px">确定</a-button>
      </a-flex>
    </a-flex>
  </a-flex>
  <a-row :gutter="20">
    <a-col :span="6">
      <a-card>
        <UserInfoCard :isLoading="isLoading" @toggleFollow="onToggleFollow" :user-info="userInfo"
                      :is-follow="userInteract.isFollow"/>
      </a-card>
      <a-affix offset-bottom="bottom" :offset-top="45">
        <a-card title="相关推荐" style="height: 260px; margin-top: 8px">

        </a-card>
        <a-card title="精选内容" style="height: 260px; margin-top: 8px">

        </a-card>
        <a-card style="height: 120px; margin-top: 8px;background-color: #3eaabd">广告位置</a-card>
      </a-affix>
    </a-col>
    <a-col :span="18" style=" float: left">
      <a-card style="border: none">
        <h1 class="article-title">{{ articleInfo.title }}</h1>
        <a-flex justify="space-between" align="center" style="white-space: nowrap;margin-top: 8px;margin-bottom: 15px">
          <a-flex gap="middle" style="white-space: nowrap;color: #8a919f;font-size: 15px">
            <div @click="CommonUtil.openNewPage(`/author/${userInfo.userId}`)" style="color: #515767;cursor: pointer">
              {{ userInfo.nickname }}
            </div>
            <div>{{ articleInfo.publishTime }}</div>
            <div>12566</div>
            <div>字数 {{ NumberUtils.formatNumber(articleInfo.contentCount) }}</div>
            <div>领域: {{ category.categoryName }}</div>
          </a-flex>
          <a-button type="link" @click="onToEditEditor(articleInfo.id)"
                    v-if="useUser.isLogin()&&userInfo.userId==useUser.userinfo.userId">编辑
          </a-button>
        </a-flex>
        <!--    文章Markdown内容    -->
        <Markdown v-if="!isLoading" :md-id="userInfo.userId" :code-theme="userInfo.editorCodeTheme"
                  :main-theme="userInfo.editorMainTheme"
                  :anchor-style="userInfo.editorAnchorStyle" :preview="false" :value="articleInfo.content"/>
        <a-flex justify="start" align="center" style="margin-top: 20px">
          <span>标签：</span>
          <a-flex justify="start" align="center" gap="small">
            <a-tag v-for="tag in tags" :key="tag.id" :bordered="false" style="box-shadow: none">{{ tag.name }}</a-tag>
          </a-flex>
        </a-flex>
      </a-card>
      <a-card style="border: none;margin-top: 15px">
        <a-collapse :bordered="false" v-model:activeKey="activeColumnKey">
          <a-collapse-panel key="1" header="共收录到3个专栏">
            <a-empty/>
          </a-collapse-panel>
        </a-collapse>
      </a-card>
      <a-card style="border: none;margin-top: 15px">
        <h3>评论 {{ comments.length }}</h3>
        <a-comment>
          <template #avatar>
            <a-avatar v-if="useUser.isLogin()" :src="ImageUtils.getImgUrl(useUser.userinfo.avatar)" alt="Han Solo"/>
          </template>
          <template #content>
            <CommentInput class="comment-container" placeholder="说点什么吧" ref="commentInputRef"
                          :disabled="commentValue==''" v-model:value="commentValue"
                          @onClick="onAddComment"/>
          </template>
        </a-comment>

        <!-- 遍历顶级评论 -->
        <a-comment
            v-if="useUser.isLogin()"
            v-for="comment in comments"
            :key="comment.id"
            class="comment-item"
        >
          <template #actions>
            <span @click="toApply(comment)">回复</span>
            <span :style="{color:comment.liked?'#1171ee':'#8a919f'}" @click="onDiggComment(comment)">点赞</span>
            <a-popconfirm
                title="您确定删除该条评论?"
                ok-text="确定"
                cancel-text="取消"
                @confirm="onDeleteComment(comment.id)">
              <span v-if="useUser.userinfo.userId==comment.userId">删除</span>
            </a-popconfirm>
          </template>
          <template #author>
            <a>{{ comment.user.nickname }}</a>
          </template>
          <template #avatar>
            <a-avatar :src="ImageUtils.getImgUrl(comment.user.avatar)" alt="Avatar"/>
          </template>
          <template #content>
            <p>{{ comment.content }}</p>
            <CommentInput v-if="false" class="comment-container" placeholder="说点什么吧" ref="commentInputRef"
                          :disabled="commentValue==''" v-model:value="commentValue"
                          @onClick="onAddComment"/>
          </template>
          <template #datetime>
            <span>{{ comment.createdAt }}</span>
          </template>

          <!-- 遍历二级评论 -->
          <a-comment
              v-for="reply in comment.replies"
              :key="reply.id"
              class="reply-item">
            <template #actions>
              <span :style="{color:reply.liked?'#1171ee':'#8a919f'}" @click="onDiggComment(reply)">点赞</span>
              <span @click="toApply(reply)">回复</span>
              <a-popconfirm
                  title="您确定删除该条评论?"
                  ok-text="确定"
                  cancel-text="取消"
                  @confirm="onDeleteComment(reply.id)">
                <span v-if="useUser.userinfo.userId==reply.userId">删除</span>
              </a-popconfirm>
            </template>
            <template #author>
              <a>{{ reply.user.nickname }} 回复 {{ reply.toUser ? reply.toUser.nickname : '' }}</a>
            </template>
            <template #avatar>
              <a-avatar :src="ImageUtils.getImgUrl(reply.user.avatar)" alt="Avatar"/>
            </template>
            <template #content>
              <p>{{ reply.content }}</p>
              <CommentInput v-if="false" class="comment-container" placeholder="说点什么吧" ref="commentInputRef"
                            :disabled="commentValue==''" v-model:value="commentValue"
                            @onClick="onAddComment"/>
            </template>
            <template #datetime>
              <span>{{ comment.createdAt }}</span>
            </template>
          </a-comment>
        </a-comment>
      </a-card>
      <a-card style="border: none;margin-top: 15px">

      </a-card>
    </a-col>
  </a-row>
  <a-float-button-group v-if="!needVisitPass&&!isLoading" shape="circle"
                        style="position: fixed; right: 6%; bottom: 43%;">
    <a-float-button @click="onDiggOrunDigg" shape="circle"
                    :badge="{ count: articleInfo.likeCount, color: 'rgb(194, 200, 209)' }">
      <template #icon>
        <svg :fill="userInteract.isLike?'#1e80ff':'#8a919f'" viewBox="64 64 896 896" focusable="false" data-icon="like"
             width="1em" height="1em"
             aria-hidden="true">
          <path
              d="M885.9 533.7c16.8-22.2 26.1-49.4 26.1-77.7 0-44.9-25.1-87.4-65.5-111.1a67.67 67.67 0 00-34.3-9.3H572.4l6-122.9c1.4-29.7-9.1-57.9-29.5-79.4A106.62 106.62 0 00471 99.9c-52 0-98 35-111.8 85.1l-85.9 311h-.3v428h472.3c9.2 0 18.2-1.8 26.5-5.4 47.6-20.3 78.3-66.8 78.3-118.4 0-12.6-1.8-25-5.4-37 16.8-22.2 26.1-49.4 26.1-77.7 0-12.6-1.8-25-5.4-37 16.8-22.2 26.1-49.4 26.1-77.7-.2-12.6-2-25.1-5.6-37.1zM112 528v364c0 17.7 14.3 32 32 32h65V496h-65c-17.7 0-32 14.3-32 32z"></path>
        </svg>
      </template>
    </a-float-button>
    <a-float-button @click="onOpenCollect" :style="{marginTop: '25px'}"
                    :badge="{ count: articleInfo.collectCount, color: 'rgb(194, 200, 209)' }">
      <template #icon>
        <svg t="1739882636550" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="14147" width="20" height="20">
          <path
              d="M554.8 99.6l104.9 212.5c7 14.1 20.4 23.9 36 26.1l234.6 34.1c39.2 5.7 54.8 53.8 26.5 81.5L787 619.2a47.57 47.57 0 0 0-13.7 42.3l40.1 233.6c6.7 39-34.3 68.8-69.3 50.4L534.2 835.2c-13.9-7.3-30.5-7.3-44.5 0L280 945.5c-35 18.4-76-11.3-69.3-50.4l40.1-233.6c2.7-15.5-2.5-31.3-13.7-42.3L67.3 453.8c-28.4-27.6-12.7-75.8 26.5-81.5l234.6-34.1c15.6-2.3 29-12 36-26.1L469.2 99.6c17.5-35.5 68.1-35.5 85.6 0z"
              p-id="14148" :fill="userInteract.isCollect?'#1e80ff':'#8a919f'"></path>
        </svg>
      </template>
    </a-float-button>
    <a-float-button @click="openCommentDrawer=true" :style="{marginTop: '25px'}"
                    :badge="{ count: articleInfo.commentCount, color: 'rgb(194, 200, 209)' }">
      <template #icon>
        <svg viewBox="64 64 896 896" focusable="false" data-icon="message" width="1em"
             height="1em" fill="#8a919f" aria-hidden="true">
          <path
              d="M924.3 338.4a447.57 447.57 0 00-96.1-143.3 443.09 443.09 0 00-143-96.3A443.91 443.91 0 00512 64h-2c-60.5.3-119 12.3-174.1 35.9a444.08 444.08 0 00-141.7 96.5 445 445 0 00-95 142.8A449.89 449.89 0 0065 514.1c.3 69.4 16.9 138.3 47.9 199.9v152c0 25.4 20.6 46 45.9 46h151.8a447.72 447.72 0 00199.5 48h2.1c59.8 0 117.7-11.6 172.3-34.3A443.2 443.2 0 00827 830.5c41.2-40.9 73.6-88.7 96.3-142 23.5-55.2 35.5-113.9 35.8-174.5.2-60.9-11.6-120-34.8-175.6zM312.4 560c-26.4 0-47.9-21.5-47.9-48s21.5-48 47.9-48 47.9 21.5 47.9 48-21.4 48-47.9 48zm199.6 0c-26.4 0-47.9-21.5-47.9-48s21.5-48 47.9-48 47.9 21.5 47.9 48-21.5 48-47.9 48zm199.6 0c-26.4 0-47.9-21.5-47.9-48s21.5-48 47.9-48 47.9 21.5 47.9 48-21.5 48-47.9 48z"></path>
        </svg>
      </template>
    </a-float-button>
    <a-float-button @click="openReportModel=true" :style="{marginTop: '25px'}">
      <template #icon>
        <svg t="1739882171114" class="icon" viewBox="0 0 1127 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="7991" width="20" height="20">
          <path
              d="M1108.468296 824.890547C1159.055032 910.219597 1097.227863 1024 990.429373 1024L130.432879 1024C29.258031 1024-32.574625 910.219597 18.012112 824.890547L450.825613 68.266574C473.306472 22.754136 518.276424 0 563.240888 0 608.209469 0 653.173934 22.754136 675.660283 68.266574L1108.468296 824.890547 1108.468296 824.890547 1108.468296 824.890547 1108.468296 824.890547ZM1020.384123 877.110641 1019.583053 875.735153 586.77504 119.111177 583.854223 113.62523C580.333998 106.500274 573.244216 102.4 563.240888 102.4 553.240806 102.4 546.151071 106.500212 542.636068 113.61633L539.710577 119.111663 106.096287 877.110641C95.301134 895.319767 109.937021 921.6 130.432879 921.6L990.429373 921.6C1016.30634 921.6 1031.298263 895.520476 1020.384123 877.110641L1020.384123 877.110641 1020.384123 877.110641 1020.384123 877.110641ZM558.08319 307.2C532.482248 307.2 512 322.819385 512 342.344809L512 579.251379C512 598.776801 532.482248 614.4 558.08319 614.4L568.321812 614.4C593.922749 614.4 614.4 598.776801 614.4 579.251379L614.4 342.344809C614.4 322.819385 593.922749 307.2 568.321812 307.2L558.08319 307.2 558.08319 307.2 558.08319 307.2 558.08319 307.2ZM512 766.885176C512 780.001705 517.522432 793.032632 526.999818 802.305669 536.477199 811.577487 549.797038 816.975247 563.200625 816.975247 576.602962 816.975247 589.927798 811.577487 599.405184 802.305669 608.882565 793.032632 614.4 780.001705 614.4 766.885176 614.4 753.772319 608.882565 740.741391 599.405184 731.469573 589.927798 722.19776 576.602962 716.8 563.200625 716.8 549.797038 716.8 536.477199 722.19776 526.999818 731.469573 517.522432 740.741391 512 753.772319 512 766.885176L512 766.885176 512 766.885176 512 766.885176Z"
              fill="#979797" p-id="7992"></path>
        </svg>
      </template>
    </a-float-button>
    <a-float-button :style="{marginTop: '25px'}">
      <template #icon>
        <svg viewBox="64 64 896 896" focusable="false" data-icon="share-alt" width="1em"
             height="1em" fill="#8a919f" aria-hidden="true">
          <path
              d="M752 664c-28.5 0-54.8 10-75.4 26.7L469.4 540.8a160.68 160.68 0 000-57.6l207.2-149.9C697.2 350 723.5 360 752 360c66.2 0 120-53.8 120-120s-53.8-120-120-120-120 53.8-120 120c0 11.6 1.6 22.7 4.7 33.3L439.9 415.8C410.7 377.1 364.3 352 312 352c-88.4 0-160 71.6-160 160s71.6 160 160 160c52.3 0 98.7-25.1 127.9-63.8l196.8 142.5c-3.1 10.6-4.7 21.8-4.7 33.3 0 66.2 53.8 120 120 120s120-53.8 120-120-53.8-120-120-120zm0-476c28.7 0 52 23.3 52 52s-23.3 52-52 52-52-23.3-52-52 23.3-52 52-52zM312 600c-48.5 0-88-39.5-88-88s39.5-88 88-88 88 39.5 88 88-39.5 88-88 88zm440 236c-28.7 0-52-23.3-52-52s23.3-52 52-52 52 23.3 52 52-23.3 52-52 52z"></path>
        </svg>
      </template>
    </a-float-button>
  </a-float-button-group>

  <a-float-button-group shape="circle">
    <a-float-button>
      <template #icon>
        <QuestionCircleOutlined/>
      </template>
    </a-float-button>

    <a-float-button
        shape="circle"
        type="primary"
        :style="{right: '94px'}">
      <template #icon>
        <CustomerServiceOutlined/>
      </template>
    </a-float-button>
    <a-back-top/>
  </a-float-button-group>
  <a-modal width="40%" :footer="null" v-model:open="openLoginModal" title="登陆StackOak畅享更多权益">
    <Login/>
  </a-modal>
  <a-drawer :width="500" title="评论9999" placement="right" :open="openCommentDrawer" @close="openCommentDrawer=false">
    <a-comment>
      <template #avatar>
        <a-avatar v-if="useUser.isLogin()" :src="ImageUtils.getImgUrl(useUser.userinfo.avatar)" alt="Han Solo"/>
      </template>
      <template #content>
        <CommentInput class="comment-container" placeholder="说点什么吧" ref="commentInputRef"
                      :disabled="commentValue==''" v-model:value="commentValue"
                      @onClick="onAddComment"/>
      </template>
    </a-comment>
  </a-drawer>
  <!-- 收藏夹模态框 -->
  <a-modal :footer='null' v-model:open="openCollectModel">
    <a-card size="small" title="选择收藏夹" :bordered="false" style="box-shadow: none">
      <a-list
          :loading="collectLoading"
          size="small"
          :split="false"
          :bordered="false"
          :pagination="collectPagination"
          item-layout="horizontal"
          :data-source="collectList">
        <template #renderItem="{ item }">

          <a-list-item>
            <template #actions>
              <a-button @click="onSaveArticleToCollect(item)" :type="item.isCollect?'primary':'default'" size="small">
                {{ item.isCollect ? '取消收藏' : '收藏' }}
              </a-button>
            </template>
            <a-skeleton :title="false" :loading="collectLoading" active>
              <a-list-item-meta
                  :description="item.description">
                <template #title>
                  <div>{{ item.name }}</div>
                </template>
              </a-list-item-meta>
            </a-skeleton>
          </a-list-item>
        </template>
      </a-list>
      <a-flex justify="space-between" style="margin-top: 10px" align="center">
        <a-button @click="openNewCollectModel=true" type="primary" size="small">新建收藏夹</a-button>
      </a-flex>
    </a-card>
  </a-modal>
  <!-- 举报模态框 -->
  <a-modal title="举报反馈" :footer='null' v-model:open="openReportModel">

  </a-modal>
  <!-- 创建收藏夹模态框 -->
  <a-modal cancel-text="取消" ok-text="保存" v-model:open="openNewCollectModel" title="创建收藏夹" @ok="onNewCollect">
    <a-form
        v-bind="{labelCol: { span: 4 },wrapperCol: { span: 20 }}"
        ref="newCollectFormRef"
        labelAlign="right"
        :model="newCollectForm"
        :rules="newCollectRules"
    >
      <a-form-item ref="name" label="名字" name="name">
        <a-input v-model:value="newCollectForm.name"/>
      </a-form-item>
      <a-form-item label="描述" name="description">
        <a-textarea v-model:value="newCollectForm.description"/>
      </a-form-item>
      <a-form-item label="状态" name="status" required>
        <a-radio-group v-model:value="newCollectForm.status">
          <a-radio :value="1">公开</a-radio>
          <a-radio :value="2">私有</a-radio>
        </a-radio-group>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>
:deep(.ant-float-btn .ant-badge .ant-badge-count ) {
  transform: translate(0, 0);
  transform-origin: center;
  top: -10px;
  inset-inline-end: -13px;
}

/* 卡片修改*/
:deep(.ant-card .ant-card-head ) {
  min-height: 40px;
  padding: 0 8px;
}

/*评论组件样式修改*/
:deep(.ant-comment .ant-comment-inner) {
  display: flex;
  padding: 5px 0;
}

:deep(.cherry-previewer) {
  border-left: none;
  padding: 0 15px 0 15px;
}

.comment-container {
  margin-top: 15px;
}

.article-title {
  word-break: break-all;
}
</style>
