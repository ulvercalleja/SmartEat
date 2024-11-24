package proyecto.smarteat.home;

import java.io.Serializable;
import java.util.ArrayList;

public class PojoTipoComida implements Serializable {
    private int numeroComidas;
    private String nombreComida;

    public PojoTipoComida(int numeroComidas, String nombreComida) {
        this.numeroComidas = numeroComidas;
        this.nombreComida = nombreComida;
    }

    public static ArrayList<PojoTipoComida> generador(ArrayList<PojoTipoComida> listaComidas){
        ArrayList<PojoTipoComida> listadoApiComidas = new ArrayList<PojoTipoComida>();

        if (listaComidas != null && !listaComidas.isEmpty()) {
            listadoApiComidas.addAll(listaComidas);
        }

        return listadoApiComidas;
    }
}
