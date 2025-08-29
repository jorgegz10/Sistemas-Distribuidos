package com.puj.services;

import com.puj.dto.Peticiones;
import com.puj.dto.Respuesta;
import com.puj.dto.RespuestaPrestamo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioBiblioteca extends Remote {
    RespuestaPrestamo prestamoByIsbn(String isbn, String userId) throws RemoteException;
    RespuestaPrestamo prestamoByTitle(String title, String userId) throws RemoteException;

    Peticiones queryByIsbn(String isbn) throws RemoteException;

    Respuesta returnByIsbn(String isbn, String userId) throws RemoteException;
}
