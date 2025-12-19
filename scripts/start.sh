#!/usr/bin/env bash
set -e

# Ensure user exists
id -u bookapp >/dev/null 2>&1 || useradd -r -s /sbin/nologin bookapp

# Ensure systemd unit exists
if [ ! -f /etc/systemd/system/bookapp.service ]; then
  cat >/etc/systemd/system/bookapp.service <<'EOF'
[Unit]
Description=Book Repo Spring Boot API
After=network.target

[Service]
User=bookapp
WorkingDirectory=/opt/book_repo
ExecStart=/usr/bin/java -jar /opt/book_repo/app.jar
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
EOF
  systemctl daemon-reload
  systemctl enable bookapp.service
fi

# Find newest jar excluding app.jar
JAR="$(find /opt/book_repo -type f -name "*.jar" ! -name "app.jar" -printf "%T@ %p\n" 2>/dev/null \
  | sort -nr | head -n 1 | cut -d' ' -f2- || true)"

if [ -z "$JAR" ]; then
  echo "Jar not found under /opt/book_repo (excluding app.jar)"
  ls -R /opt/book_repo | head -n 200
  exit 1
fi

cp -f "$JAR" /opt/book_repo/app.jar
chown bookapp:bookapp /opt/book_repo/app.jar

systemctl restart bookapp.service
systemctl status bookapp.service --no-pager -l || true
