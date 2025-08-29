package com.puj.server;

import com.puj.services.ServicioBiblioteca;
import com.puj.services.ServicioBibliotecaImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class server {
    public static void main(String[] args) throws Exception {
        String hostProp = System.getProperty("java.rmi.server.hostname"); // DEBE ser la IP pública del servidor
        String dbUrl  = System.getProperty("DB_URL",  "jdbc:postgresql://localhost:5432/library");
        String dbUser = System.getProperty("DB_USER", "postgres");
        String dbPass = System.getProperty("DB_PASS", "postgres");
        int port      = Integer.parseInt(System.getProperty("RMI_PORT", "1099"));
        String bind   = System.getProperty("BIND_NAME", "LibraryService");

        // Levantar Registry embebido
        Registry registry = LocateRegistry.createRegistry(port);

        // Publicar servicio
        ServicioBiblioteca service = new ServicioBibliotecaImpl(dbUrl, dbUser, dbPass) {
        };
        registry.rebind(bind, service);

        System.out.println("[RMI] Server listo en puerto " + port + " (" + bind + "), host=" + hostProp);
    }
}
