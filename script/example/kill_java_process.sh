#!/bin/bash

# 要查找并杀死的进程名称
PROCESS_NAME="project-1.0.0.jar"

# 使用jps查找进程ID
PID=$(jps | grep "$PROCESS_NAME" | awk '{print $1}')

if [ -z "$PID" ]; then
    echo "No process found running $PROCESS_NAME."
else
    echo "Process ID of $PROCESS_NAME: $PID"
    echo "Killing process $PID..."
    kill -9 $PID
    echo "Process $PID has been terminated."
fi
