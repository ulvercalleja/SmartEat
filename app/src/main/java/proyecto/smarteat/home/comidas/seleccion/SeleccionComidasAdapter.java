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

import java.util.List;

import proyecto.smarteat.R;

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

        // Mostrar el nombre
        holder.nombreComida.setText(alimento.getNombre());

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
    }

    @Override
    public int getItemCount() { return listaComida.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreComida;
        ImageView imagenComida;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreComida = itemView.findViewById(R.id.rsctvNombreComida);
            imagenComida = itemView.findViewById(R.id.rsctvImagenComida);
        }

    }
}
