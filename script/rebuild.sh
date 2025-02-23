
## 定义远程服务器的 IP 地址或主机名
#REMOTE_HOST="124.221.53.234"
#
## 定义远程服务器的登录用户名
#REMOTE_USER="root"
#
## 定义远程脚本的路径
#REMOTE_SCRIPT_PATH="/home/xilio1024/run.sh"
#
## 执行远程脚本
#ssh ${REMOTE_USER}@${REMOTE_HOST} "${REMOTE_SCRIPT_PATH}"


#sshpass -p 'Y0su!@$SX%' ssh root@124.221.53.234



REMOTE_HOST="124.221.53.234"
REMOTE_USER="root"
PASSWORD="Y0su!@$SX%"
REMOTE_SCRIPT="/home/xilio1024/run.sh"

sshpass -p "$PASSWORD" ssh "$REMOTE_USER@$REMOTE_HOST" "$REMOTE_SCRIPT"
