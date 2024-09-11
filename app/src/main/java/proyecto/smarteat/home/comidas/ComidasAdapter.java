package proyecto.smarteat.home.comidas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import proyecto.smarteat.R;
import proyecto.smarteat.home.comidas.seleccion.TusComidasFragment;

public class ComidasAdapter extends RecyclerView.Adapter<ComidasAdapter.ViewHolder> {
    private List<Comidas> listaComida;
    private Context context;
    public ComidasAdapter(List<Comidas> listaComida, Context context) {
        this.listaComida = listaComida;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comidas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comidas nombreComida = listaComida.get(position);
        holder.nombreComida.setText(nombreComida.getNombre());
        holder.imagenComida.setImageResource(nombreComida.getImagenComida());

        // Agregado manejo de clics para cada elemento del RecyclerView.
        holder.itemView.setOnClickListener(v -> {
            TusComidasFragment tusComidas = new TusComidasFragment();

            // Reemplaza el fragmento actual con ComidasFragment
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mpfrFragmentListas, tusComidas)
                    .addToBackStack(null)  // Opcional: permite volver al fragmento anterior
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return listaComida.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreComida;
        ImageView imagenComida;
        public ViewHolder(View itemView) {
            super(itemView);
            nombreComida = itemView.findViewById(R.id.rfctvNombreComida);
            imagenComida = itemView.findViewById(R.id.rcivComida);
        }
    }
}

