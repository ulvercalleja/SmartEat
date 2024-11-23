package proyecto.smarteat.auth;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthApi {

    private static AuthApi instancia;
    private static AuthRepo repo;
    private static final String BASE_URL = "http://192.168.0.14:8080/";

    private AuthApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        repo = retrofit.create(AuthRepo.class);
    }
    public static AuthApi getInstancia() {
        if (instancia == null) {
            instancia = new AuthApi();
        }
        return instancia;
    }

    public static AuthRepo getRepo() {
        return repo;
    }
}
