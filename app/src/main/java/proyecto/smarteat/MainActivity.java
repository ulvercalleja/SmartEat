package proyecto.smarteat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WeekDayAdapter weekDayAdapter;
    private List<WeekDay> weekDayList = Arrays.asList(
            new WeekDay("Lunes"),
            new WeekDay("Martes"),
            new WeekDay("Miércoles"),
            new WeekDay("Jueves"),
            new WeekDay("Viernes"),
            new WeekDay("Sábado"),
            new WeekDay("Domingo")
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        weekDayAdapter = new WeekDayAdapter(weekDayList, this); // 'this' se refiere al contexto de MainActivity.
        recyclerView.setAdapter(weekDayAdapter);
    }
}
