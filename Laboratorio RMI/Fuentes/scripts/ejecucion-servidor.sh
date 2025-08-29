#!/usr/bin/env bash
set -euo pipefail

# === Configuración (puedes sobreescribir con variables de entorno) ===
RMI_HOST="${RMI_HOST:-$(hostname -I | awk '{print $1}')}"     # IMPORTANTE: usa la IP alcanzable por los clientes
RMI_PORT="${RMI_PORT:-1099}"
BIND_NAME="${BIND_NAME:-LibraryService}"

DB_URL="${DB_URL:-jdbc:postgresql://127.0.0.1:5432/library}"   # Cambia a tu host/puerto/DB
DB_USER="${DB_USER:-postgres}"
DB_PASS="${DB_PASS:-postgres}"

# === Ubicación del JAR ===
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
if ! JAR="$(ls "$ROOT_DIR"/target/*.jar 2>/dev/null | head -n1)"; then
  echo "No se encontró el JAR. Compila primero: mvn -q clean package"
  exit 1
fi

echo "[INFO] Usando JAR: $JAR"
echo "[INFO] Publicando RMI en $RMI_HOST:$RMI_PORT con nombre '$BIND_NAME'"
echo "[INFO] DB_URL=$DB_URL"

exec java \
  -Djava.rmi.server.hostname="$RMI_HOST" \
  -DRMI_PORT="$RMI_PORT" \
  -DBIND_NAME="$BIND_NAME" \
  -DDB_URL="$DB_URL" \
  -DDB_USER="$DB_USER" \
  -DDB_PASS="$DB_PASS" \
  -cp "$JAR" com.puj.server.server
