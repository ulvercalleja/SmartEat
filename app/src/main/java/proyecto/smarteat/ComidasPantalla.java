package proyecto.smarteat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class ComidasPantalla extends AppCompatActivity {
    private final String NOMBRE_DIA_INFO = "nombreDia";
    private RecyclerView recyclerViewComidas;
    private ComidasAdapter comidaAdapter;
    private List<Comidas> comidasLista = Arrays.asList(
            new Comidas("Desayuno", R.drawable.comida_desayuno),
            new Comidas("Almuerzo", R.drawable.comida_almuerzo),
            new Comidas("Comida", R.drawable.comida_comida),
            new Comidas("Merienda", R.drawable.comida_merienda),
            new Comidas("Cena", R.drawable.comida_cena)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comidas_pantalla);

        String dayName = getIntent().getStringExtra(NOMBRE_DIA_INFO);

        recyclerViewComidas = findViewById(R.id.recyclerViewComidas);
        recyclerViewComidas.setLayoutManager(new LinearLayoutManager(this));
        comidaAdapter = new ComidasAdapter(comidasLista, this);
        recyclerViewComidas.setAdapter(comidaAdapter);
    }
}