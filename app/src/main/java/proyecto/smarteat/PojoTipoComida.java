package proyecto.smarteat;

public class PojoTipoComida {
    private int numeroComidas;
    private String nombreComida;

    public PojoTipoComida(int numeroComidas, String nombreComida) {
        this.numeroComidas = numeroComidas;
        this.nombreComida = nombreComida;
    }

    public int getNumeroComidas() {
        return numeroComidas;
    }

    public void setNumeroComidas(int numeroComidas) {
        this.numeroComidas = numeroComidas;
    }

    public String getNombreComida() {
        return nombreComida;
    }

    public void setNombreComida(String nombreComida) {
        this.nombreComida = nombreComida;
    }
}
