package proyecto.smarteat.home.comidas.seleccion;

import java.io.Serializable;

public class PojoAlimentos implements Serializable {
    private int id;
    private String nombre;
    private int valorCalorico;

    public PojoAlimentos(int valorCalorico,String nombre) {
        this.nombre = nombre;
        this.valorCalorico = valorCalorico;
    }

    public String getNombre() {
        return nombre;
    }
    public int getValorCalorico() {
        return valorCalorico;
    }
    public int getId() {
        return id;
    }
}
