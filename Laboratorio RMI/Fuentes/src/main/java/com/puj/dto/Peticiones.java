package com.puj.dto;
import java.io.Serializable;

// Representa el resultado de una consulta de libro por ISBN
public class Peticiones implements Serializable {
    public final boolean existe;       // Indica si el ISBN consultado existe en la base de datos
    public final String nombre;        // Nombre o título del libro
    public final int disponibilidad;   // Número de ejemplares disponibles

    public Peticiones(boolean existe, String nombre, int disponibilidad) {
        this.existe = existe;
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
    }
}

