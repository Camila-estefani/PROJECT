package camila.quispe.model;

import java.time.LocalDate;

public class Computadora {
    private int codigo;
    private TipoEquipo tipoEquipo; // Objeto TipoEquipo para la relación
    private String marca;
    private String modelo;
    private String sistemaOperativo;
    private int ramGB;
    private int almacenamientoGB;
    private LocalDate fechaMantenimiento;
    private LocalDate fechaRegistro;
    private boolean estadoActivo;

    // Constructor vacío
    public Computadora() {
        this.tipoEquipo = new TipoEquipo();
    }

    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public int getRamGB() {
        return ramGB;
    }

    public void setRamGB(int ramGB) {
        this.ramGB = ramGB;
    }

    public int getAlmacenamientoGB() {
        return almacenamientoGB;
    }

    public void setAlmacenamientoGB(int almacenamientoGB) {
        this.almacenamientoGB = almacenamientoGB;
    }

    public LocalDate getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public void setFechaMantenimiento(LocalDate fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    // toString para depuración
    @Override
    public String toString() {
        return "Computadora [Código=" + codigo + ", Tipo=" + tipoEquipo.getNombre() + ", Marca=" + marca
                + ", Modelo=" + modelo + ", RAM=" + ramGB + "GB, Almacenamiento=" + almacenamientoGB + "GB"
                + ", Estado Activo=" + (estadoActivo ? "Sí" : "No") + "]";
    }
}