<script setup lang="ts">
import {onMounted, reactive, ref, watch} from "vue";
import Markdown from "@/components/Markdown/index.vue";
import {API} from "@/api/ApiConfig.ts";
import {Https} from "@/utils/request/https.ts";

/*------------------------------------变量定义------------------------------------------*/
const testCode = ref(`
\`\`\`java
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}
\`\`\`
`);

// 代码主题选项
const codeStyleOptions = ref([
  {value: 'dark', label: 'Dark'},
  {value: 'one-light', label: 'One Light'},
  {value: 'one-dark', label: 'One Dark'},
  {value: 'vs', label: 'VS Light'},
  {value: 'vs-dark', label: 'VS Dark'},
  {value: 'solarized-light', label: 'Solarized Light'},
  {value: 'tomorrow-night', label: 'Tomorrow Night'},
  {value: 'okaidia', label: 'Okaidia'},
  {value: 'twilight', label: 'Twilight'},
  {value: 'coy', label: 'Coy'},
]);

// 内容主题选项
const themeOptions = ref([
  {value: 'default', label: '默认'},
  {value: 'dark', label: '暗黑'},
  {value: 'light', label: '明亮'},
  {value: 'green', label: '清新'},
  {value: 'red', label: '热情'},
  {value: 'violet', label: '淡雅'},
  {value: 'blue', label: '清幽'},
]);
const anchorStyleOptions = ref([
  {value: 'default', label: '带锚点'},
  {value: 'autonumber', label: '自增序号'},
  {value: 'none', label: '无锚点'},
]);

// 用于强制刷新 Markdown 组件的 key
const markdownKey = ref(0);
const userInfo = reactive({
  editorCodeTheme: 'dark', // 默认值
  editorMainTheme: 'default',
  editorFloatToolEnable: false,
  editorAnchorStyle: 'none',
});

/*------------------------------------生命周期-------------------------------------------*/
onMounted(() => {
  loadUser();
});

/*------------------------------------监听--------------------------------------------*/
// 监听 userInfo.editorCodeTheme 变化
watch(() => userInfo.editorCodeTheme,
    (newTheme) => {
      markdownKey.value += 1; // 改变 key，触发组件重新渲染
    }
);

// 监听 userInfo 变化并保存到后端
watch(userInfo,
    async () => {
      await Https.action(API.USER.updateProfile, userInfo);
    },
    {deep: true} // 深层监听 reactive 对象
);

/*------------------------------------数据加载--------------------------------------------*/
const loadUser = async () => {
  const res = await Https.action(API.USER.getUserProfile);
  if (res) {
    Object.assign(userInfo, res);
  }
};
</script>

<template>
  <a-card title="博客设置" :bordered="false" style="box-shadow: none">
    <a-flex :gap="20" vertical>
      <!-- 内容主题设置 -->
      <a-flex :gap="12">
        <div style="width: 100px">文章主题：</div>
        <a-flex vertical :gap="4">
          <a-radio-group v-model:value="userInfo.editorMainTheme">
            <a-radio v-for="item in themeOptions" :key="item.value" :value="item.value">
              {{ item.label }}
            </a-radio>
          </a-radio-group>
        </a-flex>
      </a-flex>

      <a-flex :gap="12">
        <div style="width: 100px">开启悬浮工具：</div>
        <a-flex vertical :gap="4">
          <a-radio-group v-model:value="userInfo.editorFloatToolEnable">
            <a-radio :value="true">开启</a-radio>
            <a-radio :value="false">关闭</a-radio>
          </a-radio-group>
        </a-flex>
      </a-flex>

      <a-flex :gap="12">
        <div style="width: 100px">目录样式：</div>
        <a-flex vertical :gap="4">
          <a-radio-group v-model:value="userInfo.editorAnchorStyle">
            <a-radio v-for="item in anchorStyleOptions" :key="item.value" :value="item.value">
              {{ item.label }}
            </a-radio>
          </a-radio-group>
        </a-flex>
      </a-flex>

      <!-- 代码主题设置 -->
      <a-flex :gap="12">
        <div style="width: 100px">代码片样式：</div>
        <a-flex vertical :gap="4">
          <a-select
              v-model:value="userInfo.editorCodeTheme"
              size="small"
              style="width: 150px"
              :options="codeStyleOptions"
              placeholder="请选择代码主题"
          />
          <div>
            <Markdown
                :key="markdownKey"
                md-id="code-style-setting"
                :code-theme="userInfo.editorCodeTheme"
                main-theme="default"
                anchor-style="none"
                :preview="false"
                :value="testCode"
            />
          </div>
        </a-flex>
      </a-flex>
    </a-flex>
  </a-card>
</template>

<style scoped>
:deep(.cherry-previewer) {
  border-left: none;
  padding: 0;
}

.ant-flex > div:first-child {
  font-weight: 500;
  color: #555;
}
</style>
