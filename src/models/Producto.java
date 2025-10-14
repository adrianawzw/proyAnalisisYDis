package models;

import javafx.scene.image.Image;

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private String unidad;
    private int stockMax;
    private int stockMin;
    private int stockActual;
    private byte[] imagenData; // Datos binarios de la imagen
    private Image imagenFX; // Imagen para JavaFX (no se guarda en BD)

    // Constructores
    public Producto() {}

    public Producto(String nombre, String descripcion, String unidad, int stockMax, int stockMin, int stockActual, byte[] imagenData) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.stockActual = stockActual;
        this.imagenData = imagenData;
    }

    // Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    
    public int getStockMax() { return stockMax; }
    public void setStockMax(int stockMax) { this.stockMax = stockMax; }
    
    public int getStockMin() { return stockMin; }
    public void setStockMin(int stockMin) { this.stockMin = stockMin; }
    
    public int getStockActual() { return stockActual; }
    public void setStockActual(int stockActual) { this.stockActual = stockActual; }
    
    public byte[] getImagenData() { return imagenData; }
    public void setImagenData(byte[] imagenData) { this.imagenData = imagenData; }
    
    public Image getImagenFX() { return imagenFX; }
    public void setImagenFX(Image imagenFX) { this.imagenFX = imagenFX; }

    @Override
    public String toString() {
        return nombre + " (" + unidad + ")";
    }
}