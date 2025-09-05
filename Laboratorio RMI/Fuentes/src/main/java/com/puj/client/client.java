package com.puj.client;

import com.puj.dto.Peticiones;
import com.puj.dto.Respuesta;
import com.puj.dto.RespuestaPrestamo;
import com.puj.services.ServicioBiblioteca;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws Exception {
        // Obtiene configuración desde variables del sistema
        String server = System.getProperty("SERVER_HOST", "127.0.0.1"); // Dirección IP del servidor.
        int port      = Integer.parseInt(System.getProperty("RMI_PORT", "1099")); // Puerto RMI
        String bind   = System.getProperty("BIND_NAME", "LibraryService"); // Nombre del servicio en el registro

        // Conecta al registro RMI del servidor y obtiene la referencia al servicio remoto
        Registry reg = LocateRegistry.getRegistry(server, port);
        ServicioBiblioteca svc = (ServicioBiblioteca) reg.lookup(bind);

        // Lee datos desde la consola
        Scanner sc = new Scanner(System.in);
        System.out.println("Cliente conectado a " + server + ":" + port);
    
        while (true) {
            // Menú de opciones para interactuar con el servicio remoto
            System.out.println("""
        --- Menú ---
        1) Préstamo por ISBN
        2) Préstamo por Título
        3) Consulta por ISBN
        4) Devolución por ISBN
        0) Salir
        """);
            System.out.print("Opción: ");
            int op = Integer.parseInt(sc.nextLine().trim());
            if (op == 0) break; // Si es 0, se termina el cliente

            switch (op) {
                case 1 -> { // Préstamo por ISBN
                    System.out.print("ISBN: "); String isbn = sc.nextLine();
                    System.out.print("Usuario: "); String user = sc.nextLine();
                    // Invoca al método remoto y obtiene la respuesta
                    RespuestaPrestamo r = svc.prestamoByIsbn(isbn, user);
                    System.out.println((r.prestado ? "OK" : "ERR") + " - " + r.mensaje +
                            (r.vencimiento != null ? " | Fecha devolución: " + r.vencimiento: ""));
                }
                case 2 -> { // Préstamo por título
                    System.out.print("Título: "); String title = sc.nextLine();
                    System.out.print("Usuario: "); String user = sc.nextLine();
                    RespuestaPrestamo r = svc.prestamoByTitle(title, user);
                    System.out.println((r.prestado ? "OK" : "ERR") + " - " + r.mensaje +
                            (r.vencimiento!= null ? " | Fecha devolución: " + r.vencimiento : ""));
                }
                case 3 -> {  // Consulta por ISBN
                    System.out.print("ISBN: "); String isbn = sc.nextLine();
                    // Llama al servicio para consultar
                    Peticiones q = svc.queryByIsbn(isbn); 
                    if (!q.existe) System.out.println("No existe ese ISBN");
                    else System.out.println("Título: " + q.nombre + " | Disponibles: " + q.disponibilidad);
                }
                case 4 -> {  // Devolución de libro por ISBN
                    System.out.print("ISBN: "); String isbn = sc.nextLine();
                    System.out.print("Usuario (opcional, Enter para omitir): ");
                    String user = sc.nextLine();
                    // Si el usuario no se digita, se pasa como null
                    if (user.isBlank()) user = null;
                    // Llama al servicio remoto
                    Respuesta r = svc.returnByIsbn(isbn, user);
                    System.out.println((r.success ? "OK" : "ERR") + " - " + r.mensaje);
                }
                default -> System.out.println("Opción inválida");
            }
        }
        System.out.println("Cliente finalizado.");
    }
}

