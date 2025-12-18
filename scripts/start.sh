#!/bin/bash
cd /home/ec2-user/app

# ... (기존 종료 로직 유지) ...

# [수정된 부분] 폴더 구조가 유지되므로 build/libs/ 안까지 깊게 찾도록 변경
JAR_NAME=$(find . -name "*.jar" ! -name "*plain.jar" | tail -n 1)

echo "> 새 애플리케이션 배포: $JAR_NAME"

# [기존 수정사항 유지] 표준 입력 닫기 적용
nohup java -jar $JAR_NAME > /home/ec2-user/app/nohup.out 2>&1 < /dev/null &