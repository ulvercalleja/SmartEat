package proyecto.smarteat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAlimentos {

    private static ApiAlimentos seleccionComidas;
    private static RepoAlimentos repoAlimentos;

    private ApiAlimentos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        repoAlimentos = retrofit.create(RepoAlimentos.class);
    }
    public static RepoAlimentos getRepo(){
        return repoAlimentos;
    }

    public static ApiAlimentos getInstancia() {
        if(seleccionComidas == null){
            seleccionComidas =  new ApiAlimentos();
        }
        return seleccionComidas;
    }

}
