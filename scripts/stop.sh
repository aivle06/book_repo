#!/bin/bash
set -e

# 서비스 중지 (서비스가 없거나 꺼져 있어도 성공으로 처리)
systemctl stop bookapp.service || true