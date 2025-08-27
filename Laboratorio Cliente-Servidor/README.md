# Laboratorio Cliente-Servidor

El programa implementa un modelo básico de arquitectura cliente-servidor en Java, donde un servidor escucha peticiones en un puerto y un cliente se conecta a dicho servidor para intercambiar mensajes.
La comunicación se realiza mediante sockets TCP, que permiten la transmisión confiable de datos entre procesos.

---

#### 1.1 `MultithreadedSocketServer.java`
Levanta un servidor en un puerto (en este caso, 5000, ya que se ejecutó una primera vez con el puerto 8080, se rompió la conexión y se decidió cambiar de puerto) y acepta múltiples clientes simultáneamente.  
El usuario indica el tamaño de la matriz y el número de hilos a utilizar.  

- Crea el socket servidor en el puerto 5000
- Espera a que un cliente se conecte.

---

#### 1.2 `ServerClientThread.java`
Cada cliente tiene un canal de comunicación independiente con el servidor.

- El servidor responde a los mensajes que envía el cliente con una respuesta personalizada.
- La conexión se mantiene activa hasta que el cliente envía la palabra "bye".

---

#### 1.3 `TCPClient.java`

Se conecta al servidor y permite al usuario enviar mensajes desde consola.

- Se conecta al servidor en una dirección IP y puerto definidos.
- Permite al usuario escribir mensajes por consola y enviarlos al servidor
  
---

### 3. Diseño del Experimento

