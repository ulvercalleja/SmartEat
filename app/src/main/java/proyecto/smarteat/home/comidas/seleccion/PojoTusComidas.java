package proyecto.smarteat.home.comidas.seleccion;

import java.io.Serializable;

public class PojoTusComidas implements Serializable {
    private int id;
    private String nombre;
    private int valorCalorico;
    private String imagen;
    private Long usuario_id;

    public PojoTusComidas(int valorCalorico, String nombre, String imagen, Long usuario_id) {
        this.nombre = nombre;
        this.valorCalorico = valorCalorico;
        this.imagen = imagen;
        this.usuario_id = usuario_id;
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

    public Long getUsuario_id() {
        return usuario_id;
    }

    public int getId() {
        return id;
    }

}
