package proyecto.smarteat.home.comidas.seleccion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RepoAlimentos {
    @GET("/alimentos/getAll")
    Call<List<PojoAlimentos>> getAlimentos();

    @POST("/tuscomidas/addOne")
    Call<Void> addComida(@Body PojoAlimentos alimento);

    @GET("/tuscomidas/getAll/{usuario_id}")
    Call<List<PojoTusComidas>> getMisComidas(@Path("usuario_id") int usuarioId);
}
