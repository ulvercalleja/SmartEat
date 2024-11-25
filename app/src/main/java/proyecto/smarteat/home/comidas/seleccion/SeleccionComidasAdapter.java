package proyecto.smarteat.home.comidas.seleccion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import proyecto.smarteat.R;
import proyecto.smarteat.home.MenuPantalla;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeleccionComidasAdapter extends RecyclerView.Adapter<SeleccionComidasAdapter.ViewHolder> {

    private List<PojoAlimentos> listaComida;
    private Context context;

    public SeleccionComidasAdapter(List<PojoAlimentos> listaComida, Context context) {
        this.listaComida = listaComida;
        this.context = context;
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
        holder.caloriasComida.setText(String.valueOf(alimento.getValorCalorico()) + " Kcal");

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
                        Log.d("API respuesta", "Comida guardada correctamente");
                    } else {
                        Log.e("API respuesta", "Error en la respuesta de la API: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("API respuesta", "Error al realizar la solicitud a la API", t);
                }
            });
        });

    }

    @Override
    public int getItemCount() { return listaComida.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenComida;
        TextView nombreComida, caloriasComida;

        public ViewHolder(View itemView) {
            super(itemView);
            imagenComida = itemView.findViewById(R.id.rscivImagenComida);
            nombreComida = itemView.findViewById(R.id.rsctvNombreComida);
            caloriasComida = itemView.findViewById(R.id.rsctvCaloriasComida);
        }

    }
}
