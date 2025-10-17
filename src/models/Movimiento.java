package models;

import java.util.Date;

public class Movimiento {
    private int idMov;
    private Date fechaMov;
    private String tipoMov;
    private int cantidad;
    private int idProducto;
    private int idUsuario;
    private int idArea;
    
    // Campos adicionales para mostrar información
    private String nombreProducto;
    private String nombreUsuario;
    private String nombreArea;

    // Constructores
    public Movimiento() {}

    public Movimiento(Date fechaMov, String tipoMov, int cantidad, int idProducto, int idUsuario, int idArea) {
        this.fechaMov = fechaMov;
        this.tipoMov = tipoMov;
        this.cantidad = cantidad;
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
        this.idArea = idArea;
    }

    // Getters y Setters
    public int getIdMov() {
        return idMov;
    }

    public void setIdMov(int idMov) {
        this.idMov = idMov;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
}