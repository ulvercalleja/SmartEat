package proyecto.smarteat;

import java.io.Serializable;

public class PojoAlimentos implements Serializable {
    private String nombre;
    private int valorCalorico;

    public PojoAlimentos(int valorCalorico,String nombre) {
        this.nombre = nombre;
        this.valorCalorico = valorCalorico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValorCalorico() {
        return valorCalorico;
    }

    public void setValorCalorico(int valorCalorico) {
        this.valorCalorico = valorCalorico;
    }
}
