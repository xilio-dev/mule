<template>
  <div :id="mdId" :style="{ height: height + 'vh' }"></div>
</template>

<script>
import { ref, onMounted, onUnmounted, watch } from "vue";
import Cherry from "cherry-markdown";
import "cherry-markdown/dist/cherry-markdown.min.css";

export default {
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
    mdId: {
      type: String,
      default: "markdown-container",
    },
    isPreviewOnly: {
      type: Boolean,
      default: false,
    },
  },
  setup(props, { emit }) {
    const cherryInstance = ref(null);

    const initCherryMD = () => {
      const basicConfig = {
        forceAppend: false,
        id: props.mdId,
        externals: {
          echarts: window.echarts,
          MathJax: window.MathJax,
        },
        isPreviewOnly: props.isPreviewOnly,
        engine: {
          syntax: {
            autoLink: {
              target: "",
              rel: "",
              enableShortLink: true,
              shortLinkLength: 20,
            },
            codeBlock: {
              theme: "dark",
              lineNumber: true,
              expandCode: true,
              copyCode: true,
              editCode: true,
              changeLang: true,
            },
            table: {
              enableChart: true,
            },
            fontEmphasis: {
              allowWhitespace: false,
            },
            strikethrough: {
              needWhitespace: false,
            },
            mathBlock: {
              engine: "MathJax",
              src: "https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-svg.js",
            },
            inlineMath: {
              engine: "MathJax",
            },
            emoji: {
              useUnicode: true,
              customResourceURL:
                  "https://github.githubassets.com/images/icons/emoji/unicode/${code}.png?v8",
              upperCase: false,
            },
          },
        },
        multipleFileSelection: {
          video: true,
          audio: false,
          image: true,
          word: false,
          pdf: true,
          file: true,
        },
        toolbars: {
          toolbar: [
            "bold",
            "italic",
            {
              strikethrough: [
                "strikethrough",
                "underline",
                "sub",
                "sup",
                "ruby",
              ],
            },
            "size",
            "|",
            "color",
            "header",
            "|",
            "drawIo",
            "|",
            "ol",
            "ul",
            "checklist",
            "panel",
            "justify",
            "detail",
            "|",
            "formula",
            {
              insert: [
                "image",
                "audio",
                "video",
                "link",
                "hr",
                "br",
                "code",
                "inlineCode",
                "formula",
                "toc",
                "table",
                "pdf",
                "word",
                "file",
              ],
            },
            "graph",
            "togglePreview",
            "codeTheme",
            "search",
            "shortcutKey",
          ],
          toolbarRight: ["fullScreen", "|", "export", "changeLocale", "wordCount"],
          bubble: [
            "bold",
            "italic",
            "underline",
            "strikethrough",
            "sub",
            "sup",
            "quote",
            "ruby",
            "|",
            "size",
            "color",
          ],
          sidebar: ["mobilePreview", "copy", "theme"],
          toc: {
            updateLocationHash: false,
            defaultModel: "full",
          },
          shortcutKeySettings: {
            isReplace: false,
            shortcutKeyMap: {
              "Alt-Digit1": {
                hookName: "header",
                aliasName: "标题",
              },
              "Control-Shift-KeyX": {
                hookName: "bold",
                aliasName: "加粗",
              },
            },
          },
        },
        previewer: {
          floatWhenClosePreviewer: true,
        },
        keydown: [],
        callback: {
          onClickPreview: (event) => {
            console.log("onClickPreview", event);
          },
        },
        editor: {
          id: "cherry-text",
          name: "cherry-text",
          autoSave2Textarea: true,
          defaultModel: "edit&preview",
          showFullWidthMark: true,
          showSuggestList: true,
        },
        autoScrollByHashAfterInit: true,
        themeSettings: {
          mainTheme: "light",
        },
      };

      cherryInstance.value = new Cherry(basicConfig);

      // 监听内容变化并更新 v-model
      cherryInstance.value.on("change", (value) => {
        emit("update:modelValue", value);
      });
    };

    onMounted(() => {
      initCherryMD();
    });

    onUnmounted(() => {
      if (cherryInstance.value) {
        cherryInstance.value.destroy();
      }
    });

    watch(
        () => props.modelValue,
        (newVal) => {
          if (cherryInstance.value) {
            cherryInstance.value.setValue(newVal);
          }
        }
    );

    watch(
        () => props.isPreviewOnly,
        (newVal) => {
          if (cherryInstance.value) {
            cherryInstance.value.setPreviewOnly(newVal);
          }
        }
    );

    return {
      cherryInstance,
      initCherryMD,
    };
  },
};
</script>
