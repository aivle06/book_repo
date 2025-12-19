#!/bin/bash
set -e

APP_DIR="/home/ec2-user/app"
LOG_FILE="$APP_DIR/nohup.out"

cd "$APP_DIR"

echo "> 현재 실행 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f "java -jar" || true)

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> 실행 중인 애플리케이션 종료 (PID: $CURRENT_PID)"
    kill -15 "$CURRENT_PID"
    sleep 5
fi

echo "> 배포할 JAR 파일 탐색"

JAR_NAME=$(find "$APP_DIR" -type f -name "*.jar" ! -name "*plain.jar" -printf "%T@ %p\n" \
    | sort -nr \
    | head -n 1 \
    | cut -d' ' -f2-)

if [ -z "$JAR_NAME" ]; then
    echo "> JAR 파일을 찾지 못했습니다. 배포 중단"
    exit 1
fi

echo "> 새 애플리케이션 배포: $JAR_NAME"

chmod +x "$JAR_NAME"

nohup java -jar "$JAR_NAME" > "$LOG_FILE" 2>&1 < /dev/null &
