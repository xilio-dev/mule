
#lsof -i :9856
#jps
#PID=$(jps |awk  '/Setup/ {print $1}')
 #kill -9 $PID

# PID=$(lsof -i :9856|awk 'NR==2 {print $2,$3}')
# print $PID

#lsof -i :9856
#read PID USER < <(lsof -i :9856 | awk 'NR==2 {print $2, $3}')
#print $PID $USER


#if lsof -i :9856 | grep -q LISTEN; then
#    read PID USER < <(lsof -i :9856 | awk 'NR==2 {print $2, $3}')
#    echo "PID: $PID"
#    echo "USER: $USER"
#else
#    echo "No process listening on port 9856."
#fi


#if lsof -i :9856 | grep -q LISTEN; then
#    echo "端口 9856 正在被监听。"
#else
#    echo "端口 9856 没有被监听。"
#fi


#top -n 1 |awk 'NR==8  {print $1,$2 ,$3,$8}'

jps


