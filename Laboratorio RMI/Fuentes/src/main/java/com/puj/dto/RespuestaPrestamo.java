package com.puj.dto;

import java.io.Serializable;
import java.time.LocalDate;

// Representa la respuesta de un intento de préstamo de libro
public class RespuestaPrestamo implements Serializable {
    public final boolean prestado;    // Indica si el libro fue prestado con éxito
    public final String mensaje;      // Mensaje de confirmación o error
    public final LocalDate vencimiento; // Fecha límite para la devolución del libro

    public RespuestaPrestamo(boolean prestado, String mensaje, LocalDate vencimiento) {
        this.prestado = prestado;
        this.mensaje= mensaje;
        this.vencimiento = vencimiento;
    }
}

