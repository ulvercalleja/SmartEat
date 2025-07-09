package proyecto.smarteat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import proyecto.smarteat.home.comidas.seleccion.ApiAlimentos;
import proyecto.smarteat.home.comidas.seleccion.PojoAlimentos;
import proyecto.smarteat.home.comidas.seleccion.RepoAlimentos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_DELAY_MS = 40 * 1000; // 40 segundos

    private List<PojoAlimentos> listaComidas = new ArrayList<>();
    private static final String TAG = "SplashScreen";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Llamada a la API para pre-cargar alimentos
        RepoAlimentos apiService = ApiAlimentos.getInstancia().create(RepoAlimentos.class);
        Call<List<PojoAlimentos>> call = apiService.getAlimentos();
        call.enqueue(new Callback<List<PojoAlimentos>>() {
            @Override
            public void onResponse(Call<List<PojoAlimentos>> call, Response<List<PojoAlimentos>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaComidas = response.body();
                    Log.d(TAG, "Alimentos recibidos: " + listaComidas.size());
                } else {
                    Log.e(TAG, "Error API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PojoAlimentos>> call, Throwable t) {
                Log.e(TAG, "Fallo en llamada API", t);
            }
        });

        // Retraso antes de arrancar InicioPantalla
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, InicioPantalla.class));
                finish();
            }
        }, SPLASH_DELAY_MS);


    }


}