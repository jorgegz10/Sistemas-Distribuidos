#!/usr/bin/env bash
set -euo pipefail

# === Configuración (puedes sobreescribir con variables de entorno) ===
SERVER_HOST="${SERVER_HOST:-127.0.0.1}"   # IP o FQDN del servidor RMI
RMI_PORT="${RMI_PORT:-1099}"
BIND_NAME="${BIND_NAME:-LibraryService}"

# === Ubicación del JAR ===
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
if ! JAR="$(ls "$ROOT_DIR"/target/*.jar 2>/dev/null | head -n1)"; then
  echo "No se encontró el JAR. Compila primero: mvn -q clean package"
  exit 1
fi

echo "[INFO] Conectando a $SERVER_HOST:$RMI_PORT ('$BIND_NAME')"
exec java \
  -DSERVER_HOST="$SERVER_HOST" \
  -DRMI_PORT="$RMI_PORT" \
  -DBIND_NAME="$BIND_NAME" \
  -cp "$JAR" com.puj.client.client
