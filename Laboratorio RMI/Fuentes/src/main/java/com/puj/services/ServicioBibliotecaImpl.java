package com.puj.services;

import com.puj.dto.Respuesta;
import com.puj.dto.Peticiones;
import com.puj.dto.RespuestaPrestamo;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.Objects;
import java.time.LocalDate;

public class ServicioBibliotecaImpl extends UnicastRemoteObject implements ServicioBiblioteca {
    private final String url, user, password;
    public ServicioBibliotecaImpl(String url, String user, String password) throws RemoteException {
        super(); // exporta el stub
        this.url = url; this.user = user; this.password = password;
    }
    @Override
    public RespuestaPrestamo prestamoByIsbn(String isbn, String userId) throws RemoteException {
        Objects.requireNonNull(isbn, "isbn");
        try (Connection c = getConn()) {
            c.setAutoCommit(false);
            try {
                // Verificar existencia y obtener total_copies
                int totalCopies;
                try (PreparedStatement ps = c.prepareStatement(
                        "SELECT total_copies FROM books WHERE isbn=?")) {
                    ps.setString(1, isbn);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            c.rollback();
                            return new RespuestaPrestamo(false, "ISBN no existe", null);
                        }
                        totalCopies = rs.getInt(1);
                    }
                }
                // Contar préstamos activos
                int activeLoans;
                try (PreparedStatement ps2 = c.prepareStatement(
                        "SELECT COUNT(*) FROM loans WHERE isbn=? AND returned=false")) {
                    ps2.setString(1, isbn);
                    try (ResultSet rs2 = ps2.executeQuery()) {
                        rs2.next();
                        activeLoans = rs2.getInt(1);
                    }
                }
                if (activeLoans >= totalCopies) {
                    c.rollback();
                    return new RespuestaPrestamo(false, "No hay copias disponibles", null);
                }
                // Insertar nuevo préstamo
                try (PreparedStatement ps3 = c.prepareStatement(
                        "INSERT INTO loans (isbn, user_id, loan_date, due_date, returned) " +
                                "VALUES (?, ?, CURRENT_DATE, CURRENT_DATE + INTERVAL '14 days', false)")) {
                    ps3.setString(1, isbn);
                    ps3.setString(2, userId);
                    ps3.executeUpdate();
                }
                c.commit();
                return new RespuestaPrestamo(true, "Préstamo exitoso", LocalDate.now().plusDays(14));
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RemoteException("Error en préstamo por ISBN", e);
        }
    }

    @Override
    public RespuestaPrestamo prestamoByTitle(String title, String userId) throws RemoteException {
        Objects.requireNonNull(title, "title");
        try (Connection c = getConn()) {
            c.setAutoCommit(false);
            try {
                // Buscar ISBN por título (case-insensitive)
                String isbn;
                try (PreparedStatement ps = c.prepareStatement(
                        "SELECT isbn FROM books WHERE LOWER(title)=LOWER(?)")) {
                    ps.setString(1, title);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            c.rollback();
                            return new RespuestaPrestamo(false, "Título no existe", null);
                        }
                        isbn = rs.getString(1);
                    }
                }
                c.commit();
                // Reusar la lógica de préstamo por ISBN
                return prestamoByIsbn(isbn, userId);
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RemoteException("Error en préstamo por título", e);
        }
    }


@Override
    public Peticiones queryByIsbn(String isbn) throws RemoteException {
        try (Connection c = getConn()) {
            String title;
            int total;
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT title,total_copies FROM books WHERE isbn=?")) {
                ps.setString(1, isbn);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return new Peticiones(false, null, 0);
                    }
                    title = rs.getString(1);
                    total = rs.getInt(2);
                }
            }
            int activos;
            try (PreparedStatement ps2 = c.prepareStatement(
                    "SELECT COUNT(*) FROM loans WHERE isbn=? AND returned=false")) {
                ps2.setString(1, isbn);
                try (ResultSet r2 = ps2.executeQuery()) {
                    r2.next();
                    activos = r2.getInt(1);
                }
            }
            return new Peticiones(true, title, Math.max(0, total - activos));
        } catch (SQLException e) {
            throw new RemoteException("Error en consulta", e);
        }
    }

    @Override
    public Respuesta returnByIsbn(String isbn, String userId) throws RemoteException {
        try (Connection c = getConn()) {
            c.setAutoCommit(false);
            try {
                // Seleccionar un préstamo activo (por usuario si viene, o el más antiguo)
                String sql =
                        "SELECT id FROM loans WHERE isbn=? AND returned=false " +
                                (userId != null ? "AND user_id=? " : "") +
                                "ORDER BY loan_date ASC LIMIT 1 FOR UPDATE";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, isbn);
                    if (userId != null) ps.setString(2, userId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            c.rollback();
                            return new Respuesta(false, "No hay préstamos activos para ese ISBN");
                        }
                        long id = rs.getLong(1);
                        try (PreparedStatement up = c.prepareStatement(
                                "UPDATE loans SET returned=true, return_date=CURRENT_DATE WHERE id=?")) {
                            up.setLong(1, id);
                            up.executeUpdate();
                        }
                    }
                }
                c.commit();
                return new Respuesta(true, "Devolución registrada");
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RemoteException("Error en devolución", e);
        }
    }
    private Connection getConn() throws SQLException {
        if (url.contains("user=") || url.contains("password=")) {
            return DriverManager.getConnection(url);
        } else {
            return DriverManager.getConnection(url, user, password);
        }
    }
}
