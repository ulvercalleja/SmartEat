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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_seleccion_comidas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PojoAlimentos nombreComida = listaComida.get(position);
        holder.nombreComida.setText(nombreComida.getNombre());
    }

    @Override
    public int getItemCount() { return listaComida.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreComida;
        public ViewHolder(View itemView) {
            super(itemView);
            nombreComida = itemView.findViewById(R.id.rsctvNombreComida);
        }
    }
}
