package proyecto.smarteat.perfil;

import java.io.Serializable;

public class PojoUsuario implements Serializable {
    long id;
    String nombreUsuario;

    String email;

    String password;

    public PojoUsuario(long id, String nombreUsuario, String email, String password) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }
    public PojoUsuario(String nombreUsuario, String email, String password){
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }
}