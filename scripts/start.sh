#!/bin/bash
# 배포 디렉토리로 이동
cd /home/ec2-user/app

# 실행 중인 애플리케이션 종료 (기존 프로세스가 있다면)
CURRENT_PID=$(pgrep -f .jar)
if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

# 새 JAR 파일 찾기 및 실행
JAR_NAME=$(ls -tr *.jar | tail -n 1)
echo "> 새 애플리케이션 배포: $JAR_NAME"

# 로그를 남기며 백그라운드 실행
nohup java -jar $JAR_NAME > /home/ec2-user/app/nohup.out 2>&1 &
