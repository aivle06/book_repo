#!/bin/bash
cd /home/ec2-user/app

echo "> 현재 실행 중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f .jar)

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> 실행 중인 애플리케이션 종료 (PID: $CURRENT_PID)"
  kill -15 $CURRENT_PID
  sleep 5
fi

# 기존 로직: 가장 최신 JAR 파일 찾기
# (폴더 구조가 바뀌었으니 build/libs 까지 내려가서 찾도록 유지)
JAR_NAME=$(find . -name "*.jar" ! -name "*plain.jar" | tail -n 1)

echo "> 새 애플리케이션 배포: $JAR_NAME"

chmod +x $JAR_NAME

# nohup으로 실행
nohup java -jar $JAR_NAME > /home/ec2-user/app/nohup.out 2>&1 < /dev/null &