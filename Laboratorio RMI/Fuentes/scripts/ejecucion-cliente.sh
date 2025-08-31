#!/usr/bin/env bash
set -euo pipefail

# === Configuracion (puedes sobreescribir con variables de entorno) ===

# Direccion IP o nombre del servidor RMI. Si no se define, usa "127.0.0.1" (localhost)
SERVER_HOST="${SERVER_HOST:-127.0.0.1}"   
# Puerto donde escucha el registro RMI
RMI_PORT="${RMI_PORT:-1099}"
# Nombre con el que está registrado el servicio remoto
BIND_NAME="${BIND_NAME:-LibraryService}"

# === Ubicación del JAR ===

# Obtiene la ruta raíz del proyecto
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
# Si no hay JAR compilado, muestra error
if ! JAR="$(ls "$ROOT_DIR"/target/*.jar 2>/dev/null | head -n1)"; then
  echo "No se encontró el JAR. Compila primero: mvn -q clean package"
  exit 1
fi

echo "[INFO] Conectando a $SERVER_HOST:$RMI_PORT ('$BIND_NAME')"
exec java \
  # Pasa la IP del servidor como propiedad del sistema
  -DSERVER_HOST="$SERVER_HOST" \
  # Pasa el puerto de RMI
  -DRMI_PORT="$RMI_PORT" \
  # Pasa el nombre del servicio
  -DBIND_NAME="$BIND_NAME" \
  # Ejecuta la clase principal del cliente con el JAR compilado
  -cp "$JAR" com.puj.client.client

