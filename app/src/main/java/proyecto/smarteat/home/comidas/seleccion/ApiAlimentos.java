package proyecto.smarteat.home.comidas.seleccion;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAlimentos {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.0.14:8080/";

    public static Retrofit getInstancia() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
