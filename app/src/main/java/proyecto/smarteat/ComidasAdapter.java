package proyecto.smarteat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComidasAdapter extends RecyclerView.Adapter<ComidasAdapter.ViewHolder> {
    private List<String> listaComida;

    public ComidasAdapter(List<String> listaComida) {
        this.listaComida = listaComida;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comidas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombreComida = listaComida.get(position);
        holder.nombreComida.setText(nombreComida);
    }

    @Override
    public int getItemCount() {
        return listaComida.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreComida;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreComida = itemView.findViewById(R.id.rctvNombreComida);
        }
    }
}

