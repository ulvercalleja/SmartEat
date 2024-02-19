package proyecto.smarteat;

public class Comidas {
    private String nombreComida;
    private int imagenComida;

    public Comidas(String nombreComida, int imagenComida) {
        this.nombreComida = nombreComida;
        this.imagenComida = imagenComida;
    }

    public String getNombre() {
        return nombreComida;
    }

    public int getImagenComida() {
        return imagenComida;
    }
}
