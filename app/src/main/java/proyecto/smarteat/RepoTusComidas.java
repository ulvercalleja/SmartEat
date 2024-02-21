package proyecto.smarteat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RepoTusComidas {
    @GET("{id}")
    Call<PojoTipoComida> getComida(@Path("id") int id);
}
