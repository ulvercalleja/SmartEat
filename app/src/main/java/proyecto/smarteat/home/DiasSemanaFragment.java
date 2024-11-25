package proyecto.smarteat.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import proyecto.smarteat.R;

public class DiasSemanaFragment extends Fragment {

    private RecyclerView recyclerView;
    private DiasSemanaAdapter diasemanaAdapter;
    private List<DiasSemana> listaDiaSemana = Arrays.asList(
            new DiasSemana("Lunes", R.drawable.comida_lunes),
            new DiasSemana("Martes", R.drawable.comida_martes),
            new DiasSemana("Miércoles", R.drawable.comida_miercoles),
            new DiasSemana("Jueves", R.drawable.comida_jueves),
            new DiasSemana("Viernes", R.drawable.comida_viernes),
            new DiasSemana("Sábado", R.drawable.comida_sabado),
            new DiasSemana("Domingo", R.drawable.comida_domingo)
    );

    public DiasSemanaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dias_semana, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewDias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        diasemanaAdapter = new DiasSemanaAdapter(listaDiaSemana, getContext());
        recyclerView.setAdapter(diasemanaAdapter);
    }

}
