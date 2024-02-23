package proyecto.smarteat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RepoAlimentos {
    @GET("/alimentos/getAll")
    Call<List<PojoAlimentos>> getAlimentos();
}
