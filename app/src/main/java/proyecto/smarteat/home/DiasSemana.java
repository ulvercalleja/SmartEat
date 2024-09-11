package proyecto.smarteat.home;

public class DiasSemana {
    private String nombreDia;
    private int imagenComida;

    public DiasSemana(String nombreDia, int imagenComida) {
        this.nombreDia = nombreDia;
        this.imagenComida = imagenComida;
    }

    public String getNombre() {
        return nombreDia;
    }

    public int getImagenComida() {
        return imagenComida;
    }
}