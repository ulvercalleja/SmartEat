package proyecto.smarteat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class ComidasFragment extends Fragment {
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

    private ImageButton btHome;

    public ComidasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        return inflater.inflate(R.layout.fragment_comidas, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        btHome = view.findViewById(R.id.dspibBotonHome);
        recyclerViewComidas = view.findViewById(R.id.recyclerViewComidas);
        recyclerViewComidas.setLayoutManager(new LinearLayoutManager(getActivity()));
        comidaAdapter = new ComidasAdapter(comidasLista, getActivity());
        recyclerViewComidas.setAdapter(comidaAdapter);
    }
}