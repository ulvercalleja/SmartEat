package proyecto.smarteat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;


public class DiasSemanaPantalla extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dias_semana_pantalla);

        recyclerView = findViewById(R.id.recyclerViewDias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diasemanaAdapter = new DiasSemanaAdapter(listaDiaSemana, this);
        recyclerView.setAdapter(diasemanaAdapter);
    }
}
