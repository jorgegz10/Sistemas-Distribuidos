# 📚 Library RMI — Sistema de préstamos de libros (Java RMI + PostgreSQL)

Proyecto académico que implementa un **servicio remoto** de biblioteca usando **Java RMI** en Java 21.
Permite **préstamos**, **consultas** y **devoluciones** de libros persistidos en **PostgreSQL**.
Está pensado para ejecutarse en **dos computadores**: uno con **Servidor RMI + Base de Datos** y otro(s) con **Clientes**.

---

## ✨ ¿Qué hace?

Objeto remoto `LibraryService` con operaciones:

- **Préstamo por ISBN**: `loanByIsbn(isbn, userId)` → autoriza o rechaza; si autoriza, retorna **fecha de entrega** (hoy + 1 semana).
- **Préstamo por título**: `loanByTitle(title, userId)` → igual que por ISBN pero buscando por título.
- **Consulta por ISBN**: `queryByIsbn(isbn)` → si existe y cuántos ejemplares **disponibles** hay.
- **Devolución**: `returnByIsbn(isbn, userId?)` → registra devolución (si `userId` es nulo, devuelve el préstamo **más antiguo** del ISBN).

**Concurrencia**: el servidor usa transacciones y bloqueos `SELECT ... FOR UPDATE` para evitar carreras cuando hay múltiples clientes.

---

## 🧱 Estructura
``` 
├── pom.xml
├── README.md
├── scripts
│   ├── ejecucion-cliente.sh
│   └── ejecucion-servidor.sh
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── puj
│   │   │           ├── client
│   │   │           │   └── client.java
│   │   │           ├── dto
│   │   │           │   ├── Peticiones.java
│   │   │           │   ├── Respuesta.java
│   │   │           │   └── RespuestaPrestamo.java
│   │   │           ├── server
│   │   │           │   └── server.java
│   │   │           └── services
│   │   │               ├── ServicioBibliotecaImpl.java
│   │   │               └── ServicioBiblioteca.java
│   │   └── resources
│   │       └── schema.sql
│   └── test
│       └── java

``` 
---

## 🐧 Requisitos (Linux)

- **Java 21+ (JDK)**
- **Maven 3.8+**
- **PostgreSQL 13+** (o compatible)
- Abrir el puerto **TCP 1099** (RMI Registry)

---
## 🏗️ Compilación (Linux)

```
chmod +x scripts/ejecucion-servidor.sh scripts/ejecucion-cliente.sh
mvn -q clean package
```
## ▶️ Ejecución (Linux)

- <h2>Servidor</h2>

```
# 1) Exporta variables (ver sección Configuración)

export RMI_HOST="10.10.10.5"
export RMI_PORT="1099"
export BIND_NAME="ServicioBiblioteca"
export DB_URL='jdbc:postgresql://127.0.0.1:5432/library'
export DB_USER='postgres'
export DB_PASS='postgres'

# 2) Abre el puerto del RMI Registry (Ubuntu con UFW)

sudo ufw allow 1099/tcp

# 3) Compila y arranca

mvn -q clean package
./scripts/ejecucion-servidor.sh
```

- <h2>Cliente</h2>

```
export SERVER_HOST="10.10.10.5"   # misma IP que RMI_HOST del servidor
export RMI_PORT="1099"
export BIND_NAME="ServicioBiblioteca"

./scripts/ejecucion-cliente.sh  

```
