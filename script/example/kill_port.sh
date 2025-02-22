#!/bin/bash

# 检查是否提供了端口号
if [ -z "$1" ]; then
    echo "Usage: $0 <port>"
    exit 1
fi

# 获取端口号
PORT=$1

# 查找占用该端口的进程
PID=$(lsof -i :$PORT -t)

if [ -z "$PID" ]; then
    echo "No process is using port $PORT."
    exit 0
else
    echo "Process ID using port $PORT: $PID"
    echo "Killing process $PID..."
    kill -9 $PID
    echo "Process $PID has been terminated."
fi
