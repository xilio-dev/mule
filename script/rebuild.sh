#ssh root@124.221.53.234 "cd /www/wwwroot/xilio.cn/ && rm -rf *"
#scp -r /Users/liuxin/Desktop/xilio-site/dist/* root@124.221.53.234:/www/wwwroot/xilio.cn/
#ssh root@124.221.53.234 "cd /www/wwwroot/stackoak.com/ && rm -rf *"
#pnpm build

# 远程服务器信息
REMOTE_USER="root"
REMOTE_HOST="124.221.53.234"
REMOTE_DIR="/www/wwwroot/stackoak.com/"
PASSWORD="your_password"

# 本地文件路径
LOCAL_DIR="/Users/liuxin/Desktop/stakoak-server/stackoak-site-vue/dist/*"

# 清空远程目录
sshpass -p "$PASSWORD" ssh "$REMOTE_USER@$REMOTE_HOST" "cd $REMOTE_DIR && rm -rf *"

scp -r /Users/liuxin/Desktop/stakoak-server/stackoak-site-vue/dist/* root@124.221.53.234:/www/wwwroot/stackoak.com/
#服务端打包
ssh root@124.221.53.234
