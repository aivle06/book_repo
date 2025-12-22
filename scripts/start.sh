#!/bin/bash
cd /home/ec2-user/app

echo "> 기존 Spring Boot 프로세스 확인"

# 실행 중인 java -jar 프로세스 PID 찾기
CURRENT_PID=$(ps -ef | grep java | grep -v grep | awk '{print $2}')

if [ -z "$CURRENT_PID" ]; then
  echo "> 실행 중인 Spring Boot 없음"
else
  echo "> 실행 중인 Spring Boot 종료 (PID: $CURRENT_PID)"
  kill $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포 시작"

# build/libs 안까지 포함해서 jar 찾기
JAR_NAME=$(ls /home/ec2-user/app/build/libs/*.jar | grep -v "plain" | tail -n 1)

echo "> 실행할 JAR: $JAR_NAME"

nohup java -jar $JAR_NAME > /dev/null 2> /dev/null < /dev/null &
