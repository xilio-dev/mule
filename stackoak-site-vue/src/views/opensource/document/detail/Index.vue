<script setup lang="ts">

import Navbar from "@/components/Navbar.vue";
import {ref} from "vue";
// 树形菜单数据
const menuData = ref([
  {
    id: 1,
    label: "操作指南",
    isExpanded: false,
    children: [
      {id: 11, label: "快速开始"},
      {id: 12, label: "遥控器按键说明"},
    ],
  },
  {
    id: 2,
    label: "数据结构",
    isExpanded: false,
    children: [
      {id: 21, label: "API 文档"},
      {id: 22, label: "常见问题"},
    ],
  },
  {
    id: 3,
    label: "人工智能",
    isExpanded: false,
    children: [
      {id: 11, label: "快速开始"},
      {id: 12, label: "遥控器按键说明"},
    ],
  },
  {
    id: 4,
    label: "网络安全",
    isExpanded: false,
    children: [
      {id: 11, label: "快速开始"},
      {id: 12, label: "遥控器按键说明"},
    ],
  },
]);
const toggleSubMenu = (item: any) => {
  item.isExpanded = !item.isExpanded;
};

const docHeader=import('@/assets/doc-header.png')
</script>

<template>
  <img src="@/assets/doc-header.png" style="height: 60px;width: 100%">
  <a-row>
    <a-col :span="5" style="background-color: rgb(232, 234, 239);height: 100vh">
      <div style="text-align: left;margin: 30px 30px 15px;font-size: 20px">
        H1 SDK 开发指南
      </div>
      <div style="text-align: center;margin: 5px 30px">
        <a-input/>
      </div>

      <div class="MuiBox-root css-ia5ott"
           :key="item.id"

           @click="toggleSubMenu(item)"
           v-for="item in menuData">
        <div class="MuiBox-root css-1xl3dma">
          <div class="MuiStack-root css-xsx2bb"><img
              src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAeCAYAAAAl+Z4RAAAAAXNSR0IArs4c6QAAAlFJREFUSEudlUto1FAUhv+T6Ix9IFKogo4wYJ1ksmibLAQ3CgW1LkTFB4KI6Erp0sdW6NbHUnEhiHSj4AtEi4IydaFdOMFFZ5LMgJusFFy0ndaWzj2SjDNNpp0k9i7vPee7/7n3PAiArChDpwTJ3Yvz6aeu+2UR/7FIyev3Abrq+TDwlUTtsG3bc0kZlFN1l4h2tRyYC7X5X0dd102khBTVmADhfOhGxqQs/TleKpWW45RQv6b19vGW9wD2B42Z8cKximcB1KMg5B1ms8PbUl3SRwL0NuMJu1y8CEB0gvgAbw0M6P3yZioAyIfD4Ye2ZV6JBXgGiqLvZImmCNgTDofvOpZ5fT1IS0HzUFWHswL0mYgybZBxxzJvtUPWABpKBhWW5AKBdgQdBNONivXtTnBvXUADYgxC4k8A9a06MENgzLbNB829jgDPIJcb2keS/AGErU0HZhYgXHLK5hNvLxLg/46qH5SJ3gLoDkhfEcznKpb5PBbwL5xRSPwKoHQgnGVi6VgigB9O3jhJwDMAmwJKKokBqmocYeLXIRXMPxIB9ub1AxLoXfs7gMTpWEDcT0QCOuYCMGaXG7kQkUgdslHgZsUu3o5MJL8eSPKKandcPaxREFGR9xzLvBZZTBvpCS0FG+1KPiCiL750rOKZqL5ImUymq6d3+xsQRsKtDJP1ldkT1Wp1qVM7879RyRuPAFwOvTYwtTD3czTJbPDmggtCa7AwME2idijpdAopYMb3BXlpxJ2Z+R0lO9TSNE1L1Tl9AQI99XrqcbU6PZvU2bP7C8KJ83KK4ngSAAAAAElFTkSuQmCC"
              class="css-1vtizm"></div>
          <span class="css-in3yi3">{{ item.label }}</span>
          <div class="MuiBox-root css-1broalt">{{ item.id }}</div>
        </div>
        <div class="MuiBox-root css-1te4doi" v-if="item.isExpanded">
          <div class="MuiBox-root css-1tpenxl">
            <div class="MuiBox-root css-xxukux">
              <div class="MuiStack-root css-xsx2bb">
                <div class="MuiBox-root css-145pdjw"></div>
              </div>
              <span class="css-pelz90">快速开始</span></div>
          </div>
          <div class="MuiBox-root css-1tpenxl">
            <div class="MuiBox-root css-1r6drrq">
              <div class="MuiStack-root css-xsx2bb">
                <div class="MuiBox-root css-145pdjw"></div>
              </div>
              <span class="css-pelz90">遥控器按键说明</span></div>
          </div>
        </div>
      </div>

    </a-col>
    <a-col :span="15" style="background-color: white;width: 100%">
      <div>

        ddsdsdsddddddddd========== =============================================
      </div>
    </a-col>
    <a-col :span="4">
      dddd
    </a-col>
  </a-row>
</template>

<style scoped>
.css-1xl3dma {
  width: 100%;
  height: 50px;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  user-select: none;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding-left: 40px;
  color: rgb(39, 41, 50);
}

.css-xsx2bb {
  display: flex;
  flex-direction: row;
  width: 25px;
  padding-right: 10px;
  box-sizing: border-box;
  -webkit-box-pack: center;
  justify-content: center;
  height: 100%;
  -webkit-box-align: center;
  align-items: center;
}

.css-1xl3dma {
  width: 100%;
  height: 50px;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  user-select: none;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding-left: 40px;
  color: rgb(39, 41, 50);
}

.css-1broalt {
  background: rgb(159, 161, 176);
  border-radius: 5px;
  padding: 3px 7px;
  font-size: 12px;
  opacity: 0.4;
  margin-left: 10px;
  color: black;
}

img {
  overflow-clip-margin: content-box;
  overflow: clip;
}

.css-in3yi3 {
  font-weight: bold;
}

.css-ia5ott {
  width: 100%;
  transition: 0.3s;
}

.css-1te4doi {
  height: 80px;
  transition: 0.3s;
  overflow: hidden;
}

.css-xxukux {
  width: 100%;
  height: 40px;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  font-size: 14px;
  cursor: pointer;
  user-select: none;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding-left: 40px;
  color: rgb(74, 125, 255);
}

.css-1tpenxl {
  width: 100%;
  transition: 0.3s;
  background: none;
}

.css-1r6drrq {
  width: 100%;
  height: 40px;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  font-size: 14px;
  cursor: pointer;
  user-select: none;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding-left: 40px;
  color: rgb(39, 41, 50);
}

.css-xsx2bb {
  display: flex;
  flex-direction: row;
  width: 25px;
  padding-right: 10px;
  box-sizing: border-box;
  -webkit-box-pack: center;
  justify-content: center;
  height: 100%;
  -webkit-box-align: center;
  align-items: center;
}

.css-145pdjw {
  width: 8px;
  height: 8px;
  border-radius: 8px;
  background: rgb(210, 213, 223);
  position: relative;
}

.css-145pdjw::after {
  content: "";
  background: rgb(210, 213, 223);
  width: 1px;
  height: 40px;
  position: absolute;
  inset: 0px;
  margin: auto;
}

.css-b2nzl7 {
  width: 8px;
  height: 15px;
  transform: rotate(0deg);
  transition: 0.3s;
}

.css-xsx2bb {
  display: flex;
  flex-direction: row;
  width: 25px;
  padding-right: 10px;
  box-sizing: border-box;
  -webkit-box-pack: center;
  justify-content: center;
  height: 100%;
  -webkit-box-align: center;
  align-items: center;
}

.css-b2nzl7 {
  width: 8px;
  height: 15px;
  transform: rotate(0deg);
  transition: 0.3s;
}

.css-1vtizm {
  width: 8px;
  height: 15px;
  transform: rotate(90deg);
  transition: 0.3s;
}

css-b2nzl7 {
  width: 8px;
  height: 15px;
  transform: rotate(0deg);
  transition: 0.3s;
}

.css-1xl3dma {
  width: 100%;
  height: 50px;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  user-select: none;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding-left: 40px;
  color: rgb(39, 41, 50);
}

.css-1xl3dma:hover {
  color: rgb(74, 125, 255);
}
</style>
