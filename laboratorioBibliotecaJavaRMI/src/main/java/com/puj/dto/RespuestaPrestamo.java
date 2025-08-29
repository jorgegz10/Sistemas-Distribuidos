package com.puj.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class RespuestaPrestamo implements Serializable {
    public final boolean prestado;
    public final String mensaje;
    public final LocalDate vencimiento;

    public RespuestaPrestamo(boolean prestado, String mensaje, LocalDate vencimiento) {
        this.prestado = prestado;
        this.mensaje= mensaje;
        this.vencimiento = vencimiento;
    }
}
