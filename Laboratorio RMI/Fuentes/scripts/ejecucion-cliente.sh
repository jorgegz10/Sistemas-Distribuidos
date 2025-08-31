#!/usr/bin/env bash
set -euo pipefail

# === Configuracion (puedes sobreescribir con variables de entorno) ===

SERVER_HOST="${SERVER_HOST:-127.0.0.1}"   # Direccion IP o nombre del servidor RMI. Si no se define, usa "127.0.0.1" (localhost)
RMI_PORT="${RMI_PORT:-1099}"              # Puerto donde escucha el registro RMI
BIND_NAME="${BIND_NAME:-LibraryService}"  # Nombre con el que está registrado el servicio remoto

# === Ubicación del JAR ===

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"   # Obtiene la ruta raíz del proyecto (sube un nivel desde /scripts)
if ! JAR="$(ls "$ROOT_DIR"/target/*.jar 2>/dev/null | head -n1)"; then
  echo "No se encontró el JAR. Compila primero: mvn -q clean package"  # Si no hay JAR compilado, muestra error
  exit 1
fi

echo "[INFO] Conectando a $SERVER_HOST:$RMI_PORT ('$BIND_NAME')"

# === Ejecuta el cliente Java ===

exec java \
  -DSERVER_HOST="$SERVER_HOST" \   # Pasa la IP del servidor como propiedad del sistema
  -DRMI_PORT="$RMI_PORT" \         # Pasa el puerto de RMI
  -DBIND_NAME="$BIND_NAME" \       # Pasa el nombre del servicio
  -cp "$JAR" com.puj.client.client # Ejecuta la clase principal del cliente con el JAR compilado

