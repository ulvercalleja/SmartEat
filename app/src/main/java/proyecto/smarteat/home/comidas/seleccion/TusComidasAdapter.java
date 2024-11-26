package proyecto.smarteat.home.comidas.seleccion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import proyecto.smarteat.R;

public class TusComidasAdapter extends RecyclerView.Adapter<TusComidasAdapter.ViewHolder> {
    private List<PojoTusComidas> listaComidas;
    private Context context;

    public TusComidasAdapter(List<PojoTusComidas> listaComidas, Context context) {
        this.listaComidas = listaComidas;
        this.context = context;
    }

    @NonNull
    @Override
    public TusComidasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tus_comidas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PojoTusComidas comida = listaComidas.get(position);

        holder.nombreComida.setText(comida.getNombre());
        holder.caloriasComida.setText(comida.getValorCalorico() + " Kcal");

        // Decodificar Base64 a byte[] y luego a Bitmap
        String base64Image = comida.getImagen();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imagenComida.setImageBitmap(bitmap);
        } else {
            // Placeholder si no hay imagen
            holder.imagenComida.setImageResource(R.drawable.comida_almuerzo);
        }


    }

    @Override
    public int getItemCount() {
        return listaComidas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenComida;
        TextView nombreComida, caloriasComida;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenComida = itemView.findViewById(R.id.rtcivImagenComida);
            nombreComida = itemView.findViewById(R.id.rtctvNombreComida);
            caloriasComida = itemView.findViewById(R.id.rtctvCaloriasComida);
        }
    }
}
