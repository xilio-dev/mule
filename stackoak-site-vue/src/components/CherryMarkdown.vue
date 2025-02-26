<template>
  <div :id="mdId" :style="{ height: height + 'vh' }"></div>
</template>

<script>
import { ref, watch, onMounted, onBeforeUnmount } from "vue";
import Cherry from "cherry-markdown";
import "cherry-markdown/dist/cherry-markdown.min.css";

export default {
  name: "MarkdownEditor",
  props: {
    preview: {
      type: Boolean,
      default: false,
    },
    height: {
      type: Number,
      default: 50,
    },
    modelValue: {
      type: String,
      default: "",
    },
    theme: {
      type: String,
      default: "light",
    },
    toolbarConfig: {
      type: Object,
      default: () => ({}),
    },
    syntaxConfig: {
      type: Object,
      default: () => ({}),
    },
    previewerConfig: {
      type: Object,
      default: () => ({}),
    },
    editorConfig: {
      type: Object,
      default: () => ({}),
    },
  },
  emits: ["update:modelValue", "init", "change", "destroy"],
  setup(props, { emit }) {
    const cherryInstance = ref(null);
    const mdId = ref(generateRandomId()); // 自动生成随机 ID

    const initCherryMD = () => {
      const basicConfig = {
        forceAppend: false,
        id: mdId.value, // 使用随机生成的 ID
        themeSettings: {
          mainTheme: props.theme,
        },
        isPreviewOnly: props.preview,
        engine: {
          syntax: {
            ...props.syntaxConfig,
          },
        },
        toolbars: {
          ...props.toolbarConfig,
        },
        previewer: {
          ...props.previewerConfig,
        },
        editor: {
          ...props.editorConfig,
        },
        callback: {
          onClickPreview: (event) => {
            console.log("onClickPreview", event);
          },
        },
      };

      cherryInstance.value = new Cherry(basicConfig);

      // 监听内容变化并更新 v-model
      cherryInstance.value.on("change", (value) => {
        emit("update:modelValue", value);
        emit("change", value);
      });

      // 触发初始化事件
      emit("init", cherryInstance.value);
    };

    // 监听 modelValue 的变化并更新编辑器内容
    watch(
        () => props.modelValue,
        (newVal) => {
          if (cherryInstance.value) {
            cherryInstance.value.setValue(newVal);
          }
        }
    );

    // 初始化 Cherry Markdown
    onMounted(initCherryMD);

    // 销毁实例
    onBeforeUnmount(() => {
      if (cherryInstance.value) {
        cherryInstance.value.destroy();
        emit("destroy");
      }
    });

    // 生成随机 ID 的函数
    function generateRandomId() {
      return `markdown-container-${Math.random().toString(36).substr(2, 9)}`;
    }

    return {
      cherryInstance,
      mdId, // 暴露给模板
    };
  },
};
</script>

<style scoped>

</style>
