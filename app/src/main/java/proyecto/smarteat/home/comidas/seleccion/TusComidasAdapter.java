package proyecto.smarteat.home.comidas.seleccion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import proyecto.smarteat.R;

public class TusComidasAdapter extends RecyclerView.Adapter<TusComidasAdapter.ViewHolder> {
    private ArrayList<PojoTipoComida> listaComidas;

    public TusComidasAdapter(ArrayList<PojoTipoComida> listaComidas) {
        this.listaComidas = listaComidas;
    }

    @NonNull
    @Override
    public TusComidasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tus_comidas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PojoTipoComida comida = listaComidas.get(position);
        holder.nombreComida.setText(comida.getNombreComida());
        holder.numeroComidas.setText(String.valueOf(comida.getNumeroComidas()));
    }

    @Override
    public int getItemCount() {
        return listaComidas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreComida;
        TextView numeroComidas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreComida = itemView.findViewById(R.id.nombreComida);
            numeroComidas = itemView.findViewById(R.id.numeroComidas);
        }
    }
}
