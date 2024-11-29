package proyecto.smarteat.home.comidas.seleccion;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;

import proyecto.smarteat.ConstantUtils;
import proyecto.smarteat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeleccionComidasFragment extends Fragment implements SearchView.OnQueryTextListener {
    private SearchView svBuscar;
    private ImageView ivVolver;
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

        svBuscar = view.findViewById(R.id.fscsvBarraBusqueda);
        rvSeleccionComidas = view.findViewById(R.id.fscrvSeleccionComidas);
        rvSeleccionComidas.setLayoutManager(new GridLayoutManager(getContext(),
                ConstantUtils.SELECCION_COMIDAS_GRID_SPAN_COUNT)); // Columnas en el GridLayoutº
        ivVolver = view.findViewById(R.id.fscbtVolver);

        svBuscar.setOnQueryTextListener(this);

        // Configurar el clic del botón "Volver"
        ivVolver.setOnClickListener(v -> {
            // Usar FragmentManager para regresar al fragmento anterior
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack(); // Regresa al fragmento anterior en la pila
            }
        });

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
                    Log.e(ConstantUtils.SELECCION_COMIDAS_TAG, ConstantUtils.SELECCION_COMIDAS_TAG_ERROR_API + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PojoAlimentos>> call, Throwable t) {
                Log.e(ConstantUtils.SELECCION_COMIDAS_TAG, ConstantUtils.SELECCION_COMIDAS_TAG_ERROR_API, t);
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String texto) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String texto) {
        if (texto.isEmpty()) {
            // Restaurar la lista original si el texto está vacío
            seleccionComidasAdapter.restaurarListaOriginal();
        } else {
            // Filtrar la lista según el texto ingresado
            seleccionComidasAdapter.filtrar(texto);
        }
        return true;
    }
}