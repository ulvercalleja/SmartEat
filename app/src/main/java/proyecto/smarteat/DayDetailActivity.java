package proyecto.smarteat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class DayDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMeals;
    private MealAdapter mealAdapter;
    private List<String> mealsList = Arrays.asList("Desayuno", "Merienda", "Comida", "Almuerzo", "Cena");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);

        String dayName = getIntent().getStringExtra("dayName");
        getSupportActionBar().setTitle(dayName); // Establece el título de la actividad al día seleccionado.

        recyclerViewMeals = findViewById(R.id.recyclerViewMeals);
        recyclerViewMeals.setLayoutManager(new LinearLayoutManager(this));
        mealAdapter = new MealAdapter(mealsList);
        recyclerViewMeals.setAdapter(mealAdapter);
    }
}