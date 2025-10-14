package models;

public class Proveedor {
    private int idProveedor;
    private String nombreProv;
    private String ruc;
    private String telefono;
    private String direccion;
    private int idProducto;
    private String nombreProducto; // Para mostrar en la tabla

    // Constructores
    public Proveedor() {}

    public Proveedor(String nombreProv, String ruc, String telefono, String direccion, int idProducto) {
        this.nombreProv = nombreProv;
        this.ruc = ruc;
        this.telefono = telefono;
        this.direccion = direccion;
        this.idProducto = idProducto;
    }

    // Getters y Setters
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    public String getNombreProv() { return nombreProv; }
    public void setNombreProv(String nombreProv) { this.nombreProv = nombreProv; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
}