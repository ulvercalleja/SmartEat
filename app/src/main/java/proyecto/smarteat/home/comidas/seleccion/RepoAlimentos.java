package proyecto.smarteat.home.comidas.seleccion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RepoAlimentos {
    @GET("/alimentos/getAll")
    Call<List<PojoAlimentos>> getAlimentos();

    @POST("/comida/addOne")
    Call<Void> addComida(@Body PojoAlimentos alimento);

    @GET("/comida/getAll")
    Call<List<PojoAlimentos>> getMisComidas();
}
