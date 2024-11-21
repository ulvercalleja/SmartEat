package proyecto.smarteat.login;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUsuario {

    private static ApiUsuario instancia;
    private static RepoUsuarios repo;
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private ApiUsuario() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        repo = retrofit.create(RepoUsuarios.class);
    }
    public static ApiUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ApiUsuario();
        }
        return instancia;
    }

    public static RepoUsuarios getRepo() {
        return repo;
    }
}
