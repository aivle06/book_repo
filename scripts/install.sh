#!/usr/bin/env bash
set -e

id -u bookapp >/dev/null 2>&1 || useradd -r -s /sbin/nologin bookapp

mkdir -p /opt/book_repo
chown -R bookapp:bookapp /opt/book_repo || true

if ! command -v java >/dev/null 2>&1; then
  if command -v dnf >/dev/null 2>&1; then
    dnf install -y java-17-amazon-corretto-headless
  else
    yum install -y java-17-amazon-corretto-headless
  fi
fi

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