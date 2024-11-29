package proyecto.smarteat.perfil;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RepoPerfil {
    @GET("/user/obtenerUsuario/{id}")
    Call<PojoUsuario> getUsuarioPorId(@Path("id") int id);

    @POST("/user/actualizar")
    Call<PojoUsuario> actualizarUsuario(@Body PojoUsuario usuario);


}
