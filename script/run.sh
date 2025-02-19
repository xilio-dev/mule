#!/bin/bash

# 杀死占用端口 9856 的进程
echo "正在查找并杀死占用端口 9856 的进程..."
PID=$(lsof -t -i :9856)
if [ -n "$PID" ]; then
    echo "找到进程 ID: $PID，正在终止该进程..."
    kill -9 $PID
    echo "进程已终止。"
else
    echo "没有找到占用端口 9856 的进程。"
fi

# 切换到项目目录
echo "切换到项目目录：/home/xilio1024/xilio-server"
cd /home/xilio1024/xilio-server

# 删除旧项目文件夹
echo "删除旧项目文件夹..."
rm -rf xilio-server

# 克隆最新的项目代码
echo "从 GitHub 克隆最新的项目代码..."
git clone git@github.com:xilio1024/xilio-server.git

# 进入项目目录
echo "进入项目目录：/home/xilio1024/xilio-server"
cd xilio-server

# 使用 Maven 打包项目
echo "使用 Maven 打包项目..."
mvn clean package

# 删除旧的 JAR 文件
echo "删除旧的 JAR 文件..."
rm -rf /home/xilio1024/test/project-1.0.0.jar

# 将新的 JAR 文件复制到目标目录
echo "将新的 JAR 文件复制到目标目录..."
cp ./target/project-1.0.0.jar /home/xilio1024/test/

# 切换到目标目录
echo "切换到目标目录：/home/xilio1024/test"
cd /home/xilio1024/test/

# 后台运行 Java 程序，不保存日志
echo "后台运行 Java 程序..."
nohup java -jar project-1.0.0.jar > /dev/null 2>&1 &

echo "部署完成，程序已在后台运行。"
