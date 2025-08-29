package com.puj.dto;
import java.io.Serializable;

public class Peticiones implements Serializable {
    public final boolean existe;
    public final String nombre;
    public final int disponibilidad;

    public Peticiones(boolean existe, String nombre, int disponibilidad) {
        this.existe = existe;
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
    }
}
