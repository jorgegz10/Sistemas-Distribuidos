package com.puj.dto;

import java.io.Serializable;

public class Respuesta  implements Serializable {
    public final boolean success;
    public final String mensaje;

    public Respuesta(boolean success, String mensaje) {
        this.success = success;
        this.mensaje = mensaje;
    }

}
