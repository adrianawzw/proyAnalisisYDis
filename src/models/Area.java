package models;

public class Area {
    private int idArea;
    private String nombreArea;
    private String responsable;

    // Constructores
    public Area() {}

    public Area(int idArea, String nombreArea, String responsable) {
        this.idArea = idArea;
        this.nombreArea = nombreArea;
        this.responsable = responsable;
    }

    // Getters y Setters
    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    @Override
    public String toString() {
        return nombreArea != null ? nombreArea : "Sin nombre";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Area area = (Area) obj;
        return idArea == area.idArea;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idArea);
    }
}