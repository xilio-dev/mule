
// file.ts
export class ImageUtils {
    static getImgUrl(imgUrl: string): string {
        return import.meta.env.VITE_APP_BASE_API + imgUrl;
    }
    static removeSuffixPath(imageUrl: string): string {
        let base=import.meta.env.VITE_APP_BASE_API
        if (imageUrl.startsWith(base)) {
            return imageUrl.slice(base.length);
        }
        return imageUrl; // 如果不包含前缀，直接返回原 URL
    }
}
