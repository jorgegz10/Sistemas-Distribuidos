package com.puj.dto;

import java.io.Serializable;

// Representar una respuesta del sistema
public class Respuesta  implements Serializable {
    public final boolean success; // Indica si la operación fue exitosa
    public final String mensaje;  // Mensaje asociado a la operación

    public Respuesta(boolean success, String mensaje) {
        this.success = success;
        this.mensaje = mensaje;
    }

}

