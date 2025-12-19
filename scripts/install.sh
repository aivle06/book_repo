#!/bin/bash
set -e

# 1. 사용자 생성 (이미 있으면 패스)
id -u bookapp >/dev/null 2>&1 || useradd -r -s /sbin/nologin bookapp

# 2. 디렉토리 생성 및 소유권 보장
mkdir -p /opt/bookapp
# 혹시 모를 권한 문제 방지 (빈 폴더일 때)
chown bookapp:bookapp /opt/bookapp

# 3. Java 설치 확인 (없으면 설치)
if ! command -v java >/dev/null 2>&1; then
    yum install -y java-17-amazon-corretto-headless
fi

# 4. systemd 서비스 파일 생성
cat >/etc/systemd/system/bookapp.service <<'EOF'
[Unit]
Description=BookApp Spring Boot Service
After=network.target

[Service]
User=bookapp
WorkingDirectory=/opt/bookapp
ExecStart=/usr/bin/java -jar /opt/bookapp/app.jar
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
EOF

# 5. 데몬 리로드 및 부팅 시 자동 실행 등록
systemctl daemon-reload
systemctl enable bookapp.service