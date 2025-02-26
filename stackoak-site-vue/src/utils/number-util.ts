export class NumberUtils {
    static formatNumber = (num: number) => {
        if (isNaN(num) || num == null || num < 0) {
            return '';
        }
        if (num < 1000) {
            return num.toString();
        } else if (num < 10000) {
            return Math.floor(num / 1000) + 'k';
        } else if (num < 100000000) {
            return Math.floor(num / 10000) + 'w';
        } else {
            return Math.floor(num / 100000000) + '亿';
        }
    }
}

