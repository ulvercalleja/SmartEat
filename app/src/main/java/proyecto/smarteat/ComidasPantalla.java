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
    private List<String> comidasLista = Arrays.asList("Desayuno", "Merienda", "Comida", "Almuerzo", "Cena");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comidas_pantalla);

        String dayName = getIntent().getStringExtra(NOMBRE_DIA_INFO);
        getSupportActionBar().setTitle(dayName); // Establece el título de la actividad al día seleccionado.

        recyclerViewComidas = findViewById(R.id.recyclerViewComidas);
        recyclerViewComidas.setLayoutManager(new LinearLayoutManager(this));
        comidaAdapter = new ComidasAdapter(comidasLista);
        recyclerViewComidas.setAdapter(comidaAdapter);
    }
}