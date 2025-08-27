# Laboratorio Cliente-Servidor

El programa implementa un modelo básico de arquitectura cliente-servidor en Java, donde un servidor escucha peticiones en un puerto y un cliente se conecta a dicho servidor para intercambiar mensajes.
La comunicación se realiza mediante sockets TCP, que permiten la transmisión confiable de datos entre procesos.

---
### 1. Documentación del Código

#### 1.1 `MultithreadedSocketServer.java`
Crea un servidor en un puerto (en este caso, 5000, ya que se ejecutó una primera vez con el puerto 8080, se rompió la conexión y se decidió cambiar de puerto) y acepta múltiples clientes simultáneamente.  
El usuario indica el tamaño de la matriz y el número de hilos a utilizar.  

- Crea el socket servidor en el puerto 5000.
- Espera a que un cliente se conecte.
- A cada cliente le asigna un hilo independiente, permitiendo que el servidor siga aceptando nuevas conexiones mientras atiende a los otros clientes en paralelo.

---

#### 1.2 `ServerClientThread.java`
Cada cliente tiene un canal de comunicación independiente con el servidor.

- El servidor responde a los mensajes que envía el cliente con una confirmacion que incluye el número de cliente.
- La conexión se mantiene activa hasta que el cliente envía la palabra "bye".

---

#### 1.3 `TCPClient.java`

Se conecta al servidor y permite al usuario enviar mensajes desde consola. Es el programa que permite probar el servidor multihilo.

- Se conecta al servidor en una dirección IP y puerto definidos (en donde se ejecutó el servidor).
- Permite al usuario escribir mensajes por consola y enviarlos al servidor.
- Se mantiene enviando y recibiendo mensajes hasta que el usuario escribe "bye" en la consola.
- Cada mensaje enviado recibe una respuesta inmediata del servidor.
  
---

### 2. Diseño del Experimento

Se decidió utilizar la máquina con dirección IP 10.195.22.245 como servidor. Es importante señalar que en el código MultithreadedSocketServer.java el puerto fue configurado en 5000, debido a que inicialmente se realizó una prueba en el puerto 8080, pero al intentar ejecutarlo nuevamente presentó fallas, lo que motivó el cambio de puerto.

Por otro lado, dos máquinas se conectaron como clientes a este servidor. Para que fuera posible, en cada una de ellas fue necesario modificar en el código `TCPClient.java` el socket que establece el canal de comunicación con el servidor, especificando tanto la dirección IP de la máquina en la que está corriendo el servidor, como el puerto en el que estaba escuchando las conexiones.

<div align="center">
<img width="700" height="50" alt="image" src="https://github.com/user-attachments/assets/44afef34-0606-44ea-82ef-31f56d3a0bb4" />
</div>

