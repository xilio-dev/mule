#!/bin/bash

# 端口号直接写死
PORT=9856

# 查找占用该端口的进程
PID=$(lsof -i :$PORT -t)

if [ -z "$PID" ]; then
    echo "No process is using port $PORT."
else
    echo "Process ID using port $PORT: $PID"
    echo "Killing process $PID..."
    kill -9 $PID
    echo "Process $PID has been terminated."
fi
