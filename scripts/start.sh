#!/usr/bin/env bash
set -e

JAR="$(ls -1t /opt/book_repo/build/libs/*.jar 2>/dev/null | head -n 1 || true)"
if [ -z "$JAR" ]; then
  echo "Jar not found under /opt/book_repo/build/libs/*.jar"
  echo "Contents of /opt/book_repo:"
  ls -R /opt/book_repo | head -n 200
  exit 1
fi

cp -f "$JAR" /opt/book_repo/app.jar
chown bookapp:bookapp /opt/book_repo/app.jar

systemctl restart bookapp.service
systemctl status bookapp.service --no-pager -l || true