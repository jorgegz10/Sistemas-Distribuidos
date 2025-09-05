package com.puj.server;

import com.puj.services.ServicioBiblioteca;
import com.puj.services.ServicioBibliotecaImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Publica el servicio de la biblioteca para que los clientes puedan acceder a él de forma remota
public class server {
    public static void main(String[] args) throws Exception {
        // Dirección IP pública del servidor RMI 
        String hostProp = System.getProperty("java.rmi.server.hostname"); 

        // Parámetros de conexión a la base de datos PostgreSQL.
        String dbUrl  = System.getProperty("DB_URL",  "jdbc:postgresql://localhost:5432/library");
        String dbUser = System.getProperty("DB_USER", "postgres");
        String dbPass = System.getProperty("DB_PASS", "postgres");

        // Puerto donde se levantará el registro RMI
        int port = Integer.parseInt(System.getProperty("RMI_PORT", "1099"));
        // Nombre con el que se publicará el servicio en el registro
        String bind = System.getProperty("BIND_NAME", "LibraryService");
        // Levantar un registro RMI embebido en el puerto
        Registry registry = LocateRegistry.createRegistry(port);

        // Publicar el servicio en el registro
        ServicioBiblioteca service = new ServicioBibliotecaImpl(dbUrl, dbUser, dbPass) {
        };
        registry.rebind(bind, service);

        System.out.println("[RMI] Server listo en puerto " + port + " (" + bind + "), host=" + hostProp);
    }
}

