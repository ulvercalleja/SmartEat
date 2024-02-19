package proyecto.smarteat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiasSemanaAdapter extends RecyclerView.Adapter<DiasSemanaAdapter.ViewHolder> {
    private final String NOMBRE_DIA_INFO = "nombreDia";
    private List<DiasSemana> listaDias;
    private Context context; // Agregado para tener contexto para iniciar la nueva actividad.

    public DiasSemanaAdapter(List<DiasSemana> listaDias, Context context) {
        this.listaDias = listaDias;
        this.context = context; // Inicializa el contexto.
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dias, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiasSemana diaSemana = listaDias.get(position);
        holder.weekDayName.setText(diaSemana.getNombre());
        holder.weekDayImage.setImageResource(diaSemana.getImagenComida());

        // Agregado manejo de clics para cada elemento del RecyclerView.
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ComidasPantalla.class);
            intent.putExtra(NOMBRE_DIA_INFO, diaSemana.getNombre());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaDias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView weekDayName;
        ImageView weekDayImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weekDayName = itemView.findViewById(R.id.rdtvNombreDia);
            weekDayImage = itemView.findViewById(R.id.rdivComida);
        }
    }
}

