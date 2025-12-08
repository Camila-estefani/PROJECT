package camila.quispe.model;

public class TipoEquipo {
    private int idTipo;
    private String nombre;

    public TipoEquipo() {}

    public TipoEquipo(int idTipo, String nombre) {
        this.idTipo = idTipo;
        this.nombre = nombre;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // toString para fácil impresión
    @Override
    public String toString() {
        return nombre; // Útil para JComboBox
    }
}