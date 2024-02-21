package proyecto.smarteat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiSeleccionComidas {

    private static ApiSeleccionComidas seleccionComidas;
    private static RepoTusComidas repoComida;

    private ApiSeleccionComidas() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        repoComida = retrofit.create(RepoTusComidas.class);
    }
    public static RepoTusComidas getRepo(){
        return repoComida;
    }

    public static ApiSeleccionComidas getInstancia() {
        if(seleccionComidas == null){
            seleccionComidas =  new ApiSeleccionComidas();
        }
        return seleccionComidas;
    }

}
