#!/bin/bash
set -e

APP_DIR="/home/ec2-user/app"
LOG_FILE="$APP_DIR/nohup.out"

cd "$APP_DIR"

echo "> 현재 실행 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f "java -jar" || true)

if [ -n "$CURRENT_PID" ]; then
    echo "> 실행 중인 애플리케이션 즉시 종료 (PID: $CURRENT_PID)"
    kill -9 $CURRENT_PID
fi

echo "> 최신 JAR 파일 탐색 (가장 최근 것 1개)"

JAR_NAME=$(ls -t *.jar 2>/dev/null | grep -v plain | head -n 1)

if [ -z "$JAR_NAME" ]; then
    echo "> JAR 파일을 찾지 못했습니다. 배포 중단"
    exit 1
fi

echo "> 새 애플리케이션 즉시 실행: $JAR_NAME"

nohup java -jar "$JAR_NAME" > "$LOG_FILE" 2>&1 &
