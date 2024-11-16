package proyecto.smarteat.home.comidas.seleccion;

import java.io.Serializable;

public class PojoAlimentos implements Serializable {
    private int id;
    private String nombre;
    private int valorCalorico;
    private String imagen;

    public PojoAlimentos(int valorCalorico, String nombre, String imagen) {
        this.nombre = nombre;
        this.valorCalorico = valorCalorico;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public int getValorCalorico() {
        return valorCalorico;
    }

    public int getId() {
        return id;
    }

}
