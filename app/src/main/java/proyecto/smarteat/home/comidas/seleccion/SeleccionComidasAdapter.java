package proyecto.smarteat.home.comidas.seleccion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import proyecto.smarteat.ConstantUtils;
import proyecto.smarteat.R;
import proyecto.smarteat.home.MenuPantalla;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeleccionComidasAdapter extends RecyclerView.Adapter<SeleccionComidasAdapter.ViewHolder> {

    private List<PojoAlimentos> listaComida;
    private List<PojoAlimentos> listaComidaOriginal;
    private Context context;

    public SeleccionComidasAdapter(List<PojoAlimentos> listaComida, Context context) {
        this.listaComida = listaComida;
        this.context = context;
        this.listaComidaOriginal = new ArrayList<>(listaComida);
    }

    public void restaurarListaOriginal() {
        listaComida = new ArrayList<>(listaComidaOriginal);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_seleccion_comidas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PojoAlimentos alimento = listaComida.get(position);

        // Obtén el userId desde la actividad asociada
        int idUsuario; // Valor predeterminado
        if (context instanceof MenuPantalla) { // Asegúrate de que el contexto sea de la clase correcta
            idUsuario = ((MenuPantalla) context).getUserId();
        } else {
            idUsuario = -1;
        }
        System.out.println("User ID obtenido: " + idUsuario);

        // Mostrar el nombre
        holder.nombreComida.setText(alimento.getNombre());
        holder.caloriasComida.setText(alimento.getValorCalorico() + " Kcal");
        holder.pesoGrasas.setText(alimento.getGrasas() + "gr");
        holder.pesoHidratos.setText(alimento.getHidratos() + "gr");
        holder.pesoProteinas.setText(alimento.getProteinas() + "gr");
        // Decodificar Base64 a byte[] y luego a Bitmap
        String base64Image = alimento.getImagen();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imagenComida.setImageBitmap(bitmap);
        } else {
            // Placeholder si no hay imagen
            holder.imagenComida.setImageResource(R.drawable.comida_almuerzo);
        }

        // Actualizar los gráficos de donut con los valores de los macronutrientes
        updateDonutChart(holder.pieChartGrasas, "Grasas", alimento.getGrasas());
        updateDonutChart(holder.pieChartProteinas, "Proteínas", alimento.getProteinas());
        updateDonutChart(holder.pieChartHidratos, "Hidratos", alimento.getHidratos());

        // Configurar clic en el elemento
        holder.itemView.setOnClickListener(v -> {

            // Inicializa Retrofit
            RepoAlimentos apiService = ApiAlimentos.getInstancia().create(RepoAlimentos.class);

            // Llamada a la API para obtener la lista de alimentos
            Call<Void> call = apiService.addComida(idUsuario, alimento);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        ConstantUtils.customSnackBarExito(
                                context,
                                holder.itemView,
                                R.layout.csb_operacion_exitosa // El layout que quieres usar
                        );
                    } else {
                        ConstantUtils.customSnackBarError(
                                context,
                                holder.itemView,
                                R.layout.csb_operacion_error // El layout que quieres usar
                        );
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    ConstantUtils.customSnackBarError(
                            context,
                            holder.itemView,
                            R.layout.csb_operacion_error // El layout que quieres usar
                    );
                }
            });
        });

    }

    @Override
    public int getItemCount() { return listaComida.size(); }

    public void filtrar(String texto) {
        List<PojoAlimentos> listaFiltrada = new ArrayList<>();
        for (PojoAlimentos alimento : listaComida) {
            if (alimento.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(alimento);
            }
        }
        // Actualizar la lista en el adaptador con la lista filtrada
        listaComida = listaFiltrada;
        notifyDataSetChanged();
    }

    private void updateDonutChart(PieChart pieChart, String label, float value) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(value, label));
        entries.add(new PieEntry(100 - value, "Otros"));

        PieDataSet dataSet = new PieDataSet(entries, label);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS); // Usar colores de Material Design
        dataSet.setSliceSpace(3f); // Espacio entre las "rebanadas"
        dataSet.setValueTextSize(0f); // Tamaño del texto de los valores
        // Colores para los segmentos: el primero es para el valor real, el segundo para el resto
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FF9800"));  // Naranja (para el valor real)
        colors.add(Color.parseColor("#B0BEC5"));  // Gris (para el resto)

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // Refrescar el gráfico

        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenComida;
        TextView nombreComida, caloriasComida, pesoGrasas, pesoHidratos, pesoProteinas;
        PieChart pieChartGrasas, pieChartHidratos, pieChartProteinas;

        public ViewHolder(View itemView) {
            super(itemView);
            imagenComida = itemView.findViewById(R.id.rscivImagenComida);
            nombreComida = itemView.findViewById(R.id.rsctvNombreComida);
            caloriasComida = itemView.findViewById(R.id.rsctvCaloriasComida);
            pesoGrasas = itemView.findViewById(R.id.rsctvGrasasGramos);
            pesoHidratos = itemView.findViewById(R.id.rsctvHidratosGramos);
            pesoProteinas = itemView.findViewById(R.id.rsctvProteinasGramos);
            pieChartGrasas = itemView.findViewById(R.id.rscpcGrasas);
            pieChartHidratos = itemView.findViewById(R.id.rscpcHidratos);
            pieChartProteinas = itemView.findViewById(R.id.rscpcProteinas);

            // Configurar los gráficos tipo donut
            configureDonutChart(pieChartGrasas);
            configureDonutChart(pieChartHidratos);
            configureDonutChart(pieChartProteinas);
        }

        private void configureDonutChart(PieChart pieChart) {
            pieChart.setDrawHoleEnabled(true); // Habilita el agujero en el centro
            pieChart.setHoleColor(Color.WHITE); // Color del agujero
            pieChart.setTransparentCircleRadius(61f); // Radio del círculo transparente
            pieChart.setHoleRadius(58f); // Tamaño del agujero
            pieChart.setDrawCenterText(true); // Mostrar texto en el centro
            pieChart.setCenterTextColor(Color.BLACK); // Color del texto en el centro
            pieChart.setCenterTextSize(16f); // Tamaño del texto
            pieChart.setDrawSlicesUnderHole(true); // Hacer que las "rebanadas" estén por debajo del agujero
            pieChart.setRotationAngle(0); // Inicia sin rotar el gráfico
            pieChart.setRotationEnabled(true); // Habilitar la rotación manual del gráfico
            pieChart.setUsePercentValues(false); // Mostrar los valores en porcentajes
            pieChart.getLegend().setEnabled(false); // Desactiva la leyenda si no es necesaria
        }

    }

}
