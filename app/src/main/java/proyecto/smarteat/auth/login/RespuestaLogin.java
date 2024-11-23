package proyecto.smarteat.auth.login;

public class RespuestaLogin {

    boolean datosCorrectos;

    Long userId;

    public RespuestaLogin(boolean datosCorrectos, Long userId) {
        this.datosCorrectos = datosCorrectos;
        this.userId = userId;
    }

    public RespuestaLogin(boolean datosCorrectos) {
        this.datosCorrectos = datosCorrectos;
        this.userId = null; // O algún valor por defecto para indicar que el usuario no está disponible
    }

    public boolean isDatosCorrectos() {
        return datosCorrectos;
    }

    public Long getUserId() {
        return userId;
    }

}
