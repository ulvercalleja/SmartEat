package proyecto.smarteat.auth;
import proyecto.smarteat.ConstantUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthApi {

    private static AuthApi instancia;
    private static AuthRepo repo;

    private AuthApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtils.BASE_URL)
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
