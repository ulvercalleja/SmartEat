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

    private List<PojoTipoComida> listaSeleccionComida;
    private Context context;

    public SeleccionComidasAdapter(List<PojoTipoComida> listaSeleccionComida, Context context) {
        this.listaSeleccionComida = listaSeleccionComida;
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
        PojoTipoComida nombreComida = listaSeleccionComida.get(position);
        holder.nombreComida.setText(nombreComida.getNombreComida());
    }

    @Override
    public int getItemCount() { return listaSeleccionComida.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreComida;
        public ViewHolder(View itemView) {
            super(itemView);
            nombreComida = itemView.findViewById(R.id.rsctvNombreComida);
        }
    }
}
