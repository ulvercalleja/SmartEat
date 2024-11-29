package proyecto.smarteat.perfil;

import proyecto.smarteat.ConstantUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiPerfil {

    private static ApiPerfil instancia;
    private static RepoPerfil repo;

    private ApiPerfil() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        repo = retrofit.create(RepoPerfil.class);
    }
    public static RepoPerfil getRepo(){
        return repo;
    }

    public static ApiPerfil getInstancia() {
        if(instancia == null){
            instancia =  new ApiPerfil();
        }
        return instancia;
    }
}
