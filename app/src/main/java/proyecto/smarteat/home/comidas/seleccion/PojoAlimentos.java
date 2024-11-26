package proyecto.smarteat.home.comidas.seleccion;

import java.io.Serializable;

public class PojoAlimentos implements Serializable {
    private int id;
    private String nombre;
    private int valorCalorico;
    private String imagen;
    private int grasas;
    private int proteinas;
    private int hidratos;

    public PojoAlimentos(int valorCalorico, String nombre, String imagen, int grasas, int proteinas, int hidratos) {
        this.nombre = nombre;
        this.valorCalorico = valorCalorico;
        this.imagen = imagen;
        this.grasas = grasas;
        this.proteinas = proteinas;
        this.hidratos = hidratos;
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

    public int getGrasas() {
        return grasas;
    }


    public int getProteinas() {
        return proteinas;
    }

    public int getHidratos() {
        return hidratos;
    }

    public int getId() {
        return id;
    }

}
