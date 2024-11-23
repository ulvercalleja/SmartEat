package proyecto.smarteat.auth;

import proyecto.smarteat.auth.login.RespuestaLogin;
import proyecto.smarteat.perfil.PojoUsuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthRepo {
    @GET("/user/obtenerUsuario/{id}")
    Call<PojoUsuario> getUsuarioPorId(@Path("id") int id);

    @GET("/user/login/{email}/{password}")
    Call<RespuestaLogin> login(@Path("email") String email, @Path("password") String password);

    @POST("/user/crear")
    Call<PojoUsuario> crearUsuario(@Body PojoUsuario usuario);

}
