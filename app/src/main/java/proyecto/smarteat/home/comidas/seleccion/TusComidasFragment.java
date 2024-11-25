package proyecto.smarteat.home.comidas.seleccion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import proyecto.smarteat.R;
import proyecto.smarteat.home.MenuPantalla;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TusComidasFragment extends Fragment {
    public static final String ARG_USER_ID = "userId";
    private int userId;

    private Button btAñadir;
    private RecyclerView rvTusComidas;
    private TusComidasAdapter tusComidasAdapter;
    private List<PojoTusComidas> listaTusComidas = new ArrayList<>();
    private TextView noHayComida;

    public TusComidasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tus_comidas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Verifica que la actividad host sea de tipo MenuPantalla
        if (getActivity() instanceof MenuPantalla) {
            MenuPantalla menuPantalla = (MenuPantalla) getActivity();
            userId = menuPantalla.getUserId(); // Llama al método getUserId()

            // Ahora userId contiene el ID del usuario obtenido desde MenuPantalla
            // Puedes mostrarlo en un Toast si deseas
            System.out.println("User ID: " + userId);
        } else {
            Log.e("TusComidasFragment", "La actividad host no es MenuPantalla");
        }

        noHayComida = view.findViewById(R.id.ftctvNoHayComida);
        rvTusComidas = view.findViewById(R.id.ftcrvTusComidas);
        rvTusComidas.setLayoutManager(new GridLayoutManager(getContext(), 2));

        tusComidasAdapter = new TusComidasAdapter(listaTusComidas, getContext());

        if (tusComidasAdapter.getItemCount() == 0){ //Si no hay ninguna comida
            noHayComida.setVisibility(View.VISIBLE);
        } else {
            noHayComida.setVisibility(View.GONE);
        }

        // Inicializa Retrofit
        RepoAlimentos apiService = ApiAlimentos.getInstancia().create(RepoAlimentos.class);

        // Llamada a la API para obtener la lista de alimentos
        Call<List<PojoTusComidas>> call = apiService.getMisComidas(userId);
        call.enqueue(new Callback<List<PojoTusComidas>>() {
            @Override
            public void onResponse(Call<List<PojoTusComidas>> call, Response<List<PojoTusComidas>> response) {
                if (response.isSuccessful()) {
                    listaTusComidas = response.body();
                    // Actualizar el RecyclerView con los datos obtenidos

                    rvTusComidas.setAdapter(tusComidasAdapter);

                } else {
                    Log.e("API Error", "Error en la respuesta de la API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PojoTusComidas>> call, Throwable t) {
                Log.e("API Failure", "Error al realizar la solicitud a la API", t);
            }
        });

        btAñadir = view.findViewById(R.id.tcfbtAnadir);

        // Set click listener for the button
        btAñadir.setOnClickListener(v -> {
            SeleccionComidasFragment seleccionComidasFragment = new SeleccionComidasFragment();

            // Reemplaza el fragmento actual con ComidasFragment
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mpfrFragmentListas, seleccionComidasFragment)
                    .addToBackStack(null)  // Opcional: permite volver al fragmento anterior
                    .commit();
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        cargarComidas(); // Cargar datos al reanudar el fragmento
    }

    private void cargarComidas() {
        RepoAlimentos apiService = ApiAlimentos.getInstancia().create(RepoAlimentos.class);

        Call<List<PojoTusComidas>> call = apiService.getMisComidas(userId);
        call.enqueue(new Callback<List<PojoTusComidas>>() {
            @Override
            public void onResponse(Call<List<PojoTusComidas>> call, Response<List<PojoTusComidas>> response) {
                if (response.isSuccessful()) {
                    listaTusComidas = response.body();
                    tusComidasAdapter = new TusComidasAdapter(listaTusComidas, getContext());
                    rvTusComidas.setAdapter(tusComidasAdapter);

                    if (listaTusComidas.isEmpty()) {
                        noHayComida.setVisibility(View.VISIBLE);
                    } else {
                        noHayComida.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("API Error", "Error en la respuesta de la API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PojoTusComidas>> call, Throwable t) {
                Log.e("API Failure", "Error al realizar la solicitud a la API", t);
            }
        });
    }

}