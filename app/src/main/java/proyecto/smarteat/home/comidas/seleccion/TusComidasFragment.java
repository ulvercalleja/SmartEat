package proyecto.smarteat.home.comidas.seleccion;

import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.components.Legend;  // Importación de la clase Legend
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

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
    private ImageView ivVolver;

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
        ivVolver = view.findViewById(R.id.ftcivVolver);
        rvTusComidas.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Configurar el clic del botón "Volver"
        ivVolver.setOnClickListener(v -> {
            // Usar FragmentManager para regresar al fragmento anterior
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack(); // Regresa al fragmento anterior en la pila
            }
        });

        tusComidasAdapter = new TusComidasAdapter(listaTusComidas, getContext());
        noHayComida.setVisibility(View.GONE);

        cargarComidas();

        if (tusComidasAdapter.getItemCount() == 0){ //Si no hay ninguna comida
            noHayComida.setVisibility(View.VISIBLE);
        } else {
            noHayComida.setVisibility(View.GONE);
        }
        
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

                    // Calcular totales de nutrientes
                    int totalGrasas = 0, totalHidratos = 0, totalProteinas = 0;
                    for (PojoTusComidas comida : listaTusComidas) {
                        totalGrasas += comida.getGrasas();
                        totalHidratos += comida.getHidratos();
                        totalProteinas += comida.getProteinas();
                    }

                    // Configurar el PieChart
                    configurarPieChart(totalGrasas, totalHidratos, totalProteinas);

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

    private void configurarPieChart(int grasas, int hidratos, int proteinas) {
        PieChart pieChart = getView().findViewById(R.id.tcfpcTotalNutrientes);
        // Crear entradas para el gráfico
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(grasas, "Grasas"));
        entries.add(new PieEntry(hidratos, "Hidratos"));
        entries.add(new PieEntry(proteinas, "Proteínas"));

        // Crear los colores
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FFA726")); // Naranja para grasas
        colors.add(Color.parseColor("#66BB6A")); // Verde para hidratos
        colors.add(Color.parseColor("#29B6F6")); // Azul para proteínas

        // Configurar el dataset
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);
        // Configurar los datos
        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        // Configurar el estilo del PieChart
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setCenterText("Nutrientes Totales");
        pieChart.setCenterTextSize(14f);
        pieChart.setUsePercentValues(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); // Centra horizontalmente
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);    // Coloca en la parte inferior
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);            // Leyenda en fila horizontal
        legend.setDrawInside(false);                                                // Tamaño del texto
        legend.setTextColor(Color.BLACK);

        // Actualizar el gráfico
        pieChart.invalidate();
    }

}