package proyecto.smarteat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.ViewHolder> {

    private List<WeekDay> weekDayList;
    private Context context; // Agregado para tener contexto para iniciar la nueva actividad.

    public WeekDayAdapter(List<WeekDay> weekDayList, Context context) {
        this.weekDayList = weekDayList;
        this.context = context; // Inicializa el contexto.
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeekDay weekDay = weekDayList.get(position);
        holder.weekDayName.setText(weekDay.getName());

        // Agregado manejo de clics para cada elemento del RecyclerView.
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DayDetailActivity.class);
            intent.putExtra("dayName", weekDay.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return weekDayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView weekDayName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weekDayName = itemView.findViewById(R.id.tvDayName);
        }
    }
}

