package proyecto.smarteat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComidasFragmentAdapter extends RecyclerView.Adapter<ComidasFragmentAdapter.ViewHolder> {
    private List<PojoTipoComida> listaComida;
    private Context context;
    public ComidasFragmentAdapter(List<PojoTipoComida> listaComida, Context context) {
        this.listaComida = listaComida;
        this.context = context;
    }

    @NonNull
    @Override
    public ComidasFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fragment_comidas, parent, false);
        return new ComidasFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComidasFragmentAdapter.ViewHolder holder, int position) {
        PojoTipoComida nombreComida = listaComida.get(position);
        holder.nombreComida.setText(nombreComida.getNombreComida());
    }

    @Override
    public int getItemCount() {
        return listaComida.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreComida;
        public ViewHolder(View itemView) {
            super(itemView);
            nombreComida = itemView.findViewById(R.id.rfctvNombreComida);
        }
    }
}
