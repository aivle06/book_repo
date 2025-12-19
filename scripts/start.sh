#!/bin/bash
set -e

# 배포된 jar 파일의 소유권을 확실하게 변경 (이중 안전장치)
chown bookapp:bookapp /opt/bookapp/app.jar

# systemd를 통해 서비스 재시작
systemctl restart bookapp.service