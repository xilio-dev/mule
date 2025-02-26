<script setup lang="ts">
import {
  TwitterOutlined,
  YoutubeOutlined,
  FacebookOutlined,
  LinkedinOutlined,
} from '@ant-design/icons-vue';
import avatarImage from '@/assets/avatar.jpeg';
import ArticleList from "@/components/ArticleList.vue";
import ColumnList from "@/components/ColumnList.vue";
import {ref} from "vue";


const activeKey = ref('1')

</script>

<template>
  <a-row  class="background" style="width: 100%" >
    <div style="width: 100%;">
      <div class="user-header">
        <div class="toutu user-header-toutu">
          <img
              style="height: 200px; width: 100%;z-index: 99"
              src="@/assets/author_back.jpg"/>
        </div>
        <div class="header-upinfo-bg-shadow">
          <div class="top"></div>
          <div class="bottom"></div>
        </div>
        <div class="header-upinfo">
          <div class="upinfo__main">
            <div class="upinfo-avatar">
              <a-avatar
                  :size="110"
                  shape="circle"
                  :src="avatarImage"
              />
            </div>
            <div class="upinfo-intro">
              <h2 class="intro-title">天才程序员</h2>
              <p class="intro-data">
                602.5万获赞 118.5万粉丝 901原创
              </p>
              <p class="intro-text">
                两年后端开发经验，具有丰富的电商系统开发经验，曾获得奥林匹克数据竞赛金牌。曾获得奥林匹克数据竞赛金牌。
              </p>
            </div>

          </div>
          <div class="operations" v-if="true">
            <div class="interactions">
              <a-button type="primary" style="margin-right: 15px">已关注</a-button>
              <a-button type="default">私信</a-button>
            </div>
          </div>
        </div>
      </div>
    </div>

  </a-row>
  <a-row style=" width: 100%;  padding-top: 55px;  ">
    <a-col :span="6" style="padding-right: 15px">
      <a-card :bordered=false title="个人成就" style=" box-shadow: none">
        <h3>文章被阅读 1,118,166 </h3>
        <h3>文章被点赞 13,034</h3>
        <h3>内容获得 183 次评论</h3>
        <h3>获得 26321 次收藏</h3>
        <h3>收获 14563 粉丝</h3>
      </a-card>
      <a-card :bordered=false style=" box-shadow: none;margin-top: 15px">
        <a-flex wrap="wrap" gap="small">
          <a-tag color="#55acee">
            <template #icon>
              <twitter-outlined/>
            </template>
            Twitter
          </a-tag>
          <a-tag color="#cd201f">
            <template #icon>
              <youtube-outlined/>
            </template>
            Youtube
          </a-tag>
          <a-tag color="#3b5999">
            <template #icon>
              <facebook-outlined/>
            </template>
            Facebook
          </a-tag>
          <a-tag color="#55acee">
            <template #icon>
              <linkedin-outlined/>
            </template>
            LinkedIn
          </a-tag>
        </a-flex>

      </a-card>
      <a-card :bordered=false title="阅读榜单" style=" box-shadow: none;margin-top: 15px;min-height: 150px">

      </a-card>
    </a-col>
    <a-col :span="18" style="background-color: white">
      <div style="margin-left: 15px">
        <a-tabs v-model:activeKey="activeKey">
          <a-tab-pane key="1" tab="文章" force-render>
            <ArticleList/>
          </a-tab-pane>
          <a-tab-pane key="2" tab="合集" force-render>
            <ColumnList/>
          </a-tab-pane>
          <a-tab-pane key="3" tab="粉丝">Content of Tab Pane 3</a-tab-pane>
          <a-tab-pane key="4" tab="关注的人">Content of Tab Pane 3</a-tab-pane>
        </a-tabs>
      </div>
    </a-col>

  </a-row>

</template>

<style scoped>
.user-header {
  height: 160px;
  position: relative;
  display: flex;
  align-items: flex-end;
  justify-content: center;
}

.user-header .user-header-toutu {
  pointer-events: none;
  z-index: -1;
  position: absolute;
  left: 0;
  top: 0;
}

.toutu {
  width: 100%;
  height: 100%;

}

.user-header .header-upinfo-bg-shadow {
  pointer-events: none;
  z-index: -1;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;

}

.user-header .header-upinfo-bg-shadow .top {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  height: 100px;
  background: linear-gradient(180deg, rgba(0, 0, 0, 0.7) 0%, rgba(0, 0, 0, 0) 100%);
}

.user-header .header-upinfo-bg-shadow .bottom {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  height: 80px;
  background: linear-gradient(180deg, rgba(0, 0, 0, 0.0001) 0%, rgba(0, 0, 0, 0.5) 100%);

}

.user-header .header-upinfo {
  margin: 0 auto;
  padding-left: 15px;
  width: 100%;
  height: fit-content;
  display: flex;
  align-items: flex-end;
}

.upinfo__main {
  flex: 1;
  display: flex;
  align-items: flex-end;
}

.upinfo-avatar {
  flex-shrink: 0;
  margin-right: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  top: 20px; /* 调整头像位置，使其中心与背景图底部对齐 */
}

.upinfo-intro {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  color: #fff;
  position: relative;
  top: 10px; /* 调整介绍信息位置 */
  flex: 1;
}

.upinfo-intro .intro-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.upinfo-intro .intro-text {
  font-size: 14px;
  line-height: 1.5;
}

.upinfo-intro .intro-data {
  font-size: 18px;
  line-height: 1.5;
}

.operations {
  padding: 0 30px;
}

.upinfo .operations {
  align-self: flex-end;
  display: flex;
  align-items: center;

}

.upinfo .operations .interactions {
  display: flex;
  align-items: center;
}

/* 卡片修改*/
:deep(.ant-card .ant-card-head ) {
  min-height: 40px;
  padding: 0 8px;
}

/*修改所有卡片样式*/
a-card {
  border: none;
  box-shadow: none;
}
</style>
