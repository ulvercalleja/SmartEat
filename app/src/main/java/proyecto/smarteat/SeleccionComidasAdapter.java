package proyecto.smarteat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeleccionComidasAdapter extends RecyclerView.Adapter<SeleccionComidasAdapter.ViewHolder> {

    private List<PojoAlimentos> listaComida;
    private Context context;

    public SeleccionComidasAdapter(List<PojoAlimentos> listaComida, Context context) {
        this.listaComida = listaComida;
        this.context = context;
    }

    @NonNull
    @Override
    public SeleccionComidasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_seleccion_comidas, parent, false);
        return new SeleccionComidasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeleccionComidasAdapter.ViewHolder holder, int position) {
        PojoAlimentos alimento = listaComida.get(position);
        holder.alimento.setText(alimento.getNombre());
    }

    @Override
    public int getItemCount() { return listaComida.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView alimento;
        public ViewHolder(View itemView) {
            super(itemView);
            alimento = itemView.findViewById(R.id.rsctvNombreComida);
        }
    }
}
