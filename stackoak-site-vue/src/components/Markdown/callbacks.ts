import {uploadImage} from "@/api/material.ts";

export const Callbacks = (cherryInstance: any, emit: any) => ({
    urlProcessor: (url: string, srcType: any) => url,
    fileUpload(file: File, callback: (url: string, params?: any) => void) {
        if (/image/i.test(file.type)) {
            const formData = new FormData();
            formData.append("file", file);

            uploadImage(formData).then((res) => {
                //@ts-ignore
                const imageUrl = res.imgUrl;
                callback(imageUrl, {
                    name: file.name.replace(/\.[^.]+$/, ""),
                    isShadow: true,
                    width: "60%",
                    height: "auto",
                });
            })
                .catch((error) => {
                    console.error("Error uploading image:", error);
                    callback("images/demo-error.png"); // 回显错误图片
                });
        } else {
            callback("images/demo-dog.png");
        }
    },
    //内容改变的时回调
    afterChange: (text: any, html: any) => {
        if (emit) {
            emit("markdown-change", {content: text});
        }
    },
    beforeImageMounted: (srcProp: any, src: any) => ({srcProp, src}),
});
