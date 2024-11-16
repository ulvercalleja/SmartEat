package proyecto.smarteat.home.comidas.seleccion;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import proyecto.smarteat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeleccionComidasFragment extends Fragment {

    private RecyclerView rvSeleccionComidas;
    private SeleccionComidasAdapter seleccionComidasAdapter;
    private List<PojoAlimentos> listaComidas = new ArrayList<>();

    public SeleccionComidasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_comidas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSeleccionComidas = view.findViewById(R.id.fscrvSeleccionComidas);
        rvSeleccionComidas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa Retrofit
        RepoAlimentos apiService = ApiAlimentos.getInstancia().create(RepoAlimentos.class);

        // Llamada a la API para obtener la lista de alimentos
        Call<List<PojoAlimentos>> call = apiService.getAlimentos();
        call.enqueue(new Callback<List<PojoAlimentos>>() {
            @Override
            public void onResponse(Call<List<PojoAlimentos>> call, Response<List<PojoAlimentos>> response) {
                if (response.isSuccessful()) {
                    listaComidas = response.body();
                    // Actualizar el RecyclerView con los datos obtenidos
                    seleccionComidasAdapter = new SeleccionComidasAdapter(listaComidas, getContext());
                    rvSeleccionComidas.setAdapter(seleccionComidasAdapter);

                } else {
                    Log.e("API Error", "Error en la respuesta de la API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PojoAlimentos>> call, Throwable t) {
                Log.e("API Failure", "Error al realizar la solicitud a la API", t);
            }
        });

    }

}