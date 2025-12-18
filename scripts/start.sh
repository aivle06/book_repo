#!/usr/bin/env bash
set -e

# Ensure user exists (idempotent)
if ! id -u bookapp >/dev/null 2>&1; then
  useradd -r -s /sbin/nologin bookapp
fi

# Ensure systemd unit exists (idempotent)
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

# Find jar (more flexible than only build/libs)
JAR="$(ls -1t /opt/book_repo/**/*.jar /opt/book_repo/*.jar 2>/dev/null | head -n 1 || true)"
if [ -z "$JAR" ]; then
  echo "Jar not found under /opt/book_repo"
  echo "Contents of /opt/book_repo:"
  ls -R /opt/book_repo | head -n 200
  exit 1
fi

cp -f "$JAR" /opt/book_repo/app.jar
chown bookapp:bookapp /opt/book_repo/app.jar

systemctl restart bookapp.service
systemctl status bookapp.service --no-pager -l || true
