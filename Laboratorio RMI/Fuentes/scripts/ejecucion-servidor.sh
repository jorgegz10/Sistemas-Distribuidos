#!/usr/bin/env bash
set -euo pipefail

# === Configuración (puedes sobreescribir con variables de entorno) ===

RMI_HOST="${RMI_HOST:-$(hostname -I | awk '{print $1}')}"   # Dirección IP que verán los clientes. Por defecto toma la primera IP de la máquina
RMI_PORT="${RMI_PORT:-1099}"                                # Puerto RMI
BIND_NAME="${BIND_NAME:-LibraryService}"                    # Nombre del servicio en el registro

DB_URL="${DB_URL:-jdbc:postgresql://127.0.0.1:5432/library}"  # URL JDBC de PostgreSQL
DB_USER="${DB_USER:-postgres}"                                # Usuario DB
DB_PASS="${DB_PASS:-postgres}"                                # Contraseña DB

# === Ubicación del JAR ===

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"   # Obtiene la ruta raíz del proyecto
if ! JAR="$(ls "$ROOT_DIR"/target/*.jar 2>/dev/null | head -n1)"; then
  echo "No se encontró el JAR. Compila primero: mvn -q clean package"  # Verifica que exista el JAR
  exit 1
fi

echo "[INFO] Usando JAR: $JAR"
echo "[INFO] Publicando RMI en $RMI_HOST:$RMI_PORT con nombre '$BIND_NAME'"
echo "[INFO] DB_URL=$DB_URL"

# === Ejecuta el servidor Java ===

exec java \
-Djava.rmi.server.hostname="$RMI_HOST" \  # Establece la IP del servidor RMI
-DRMI_PORT="$RMI_PORT" \                  # Puerto RMI
-DBIND_NAME="$BIND_NAME" \                # Nombre del servicio
-DDB_URL="$DB_URL" \                      # URL de conexión a PostgreSQL
-DDB_USER="$DB_USER" \                    # Usuario DB
-DDB_PASS="$DB_PASS" \                    # Contraseña DB

-cp "$JAR" com.puj.server.server          # Ejecuta la clase principal del servidor con el JAR compilado
