package proyecto.smarteat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class SeleccionComidasFragment extends Fragment {

    private RecyclerView rvSeleccionComidas;
    private SeleccionComidasAdapter seleccionComidasAdapter;
    private List<PojoTipoComida> listaComidas = Arrays.asList(
            new PojoTipoComida(0, "Croissant"),
            new PojoTipoComida(0, "Napolitana"),
            new PojoTipoComida(0, "Pizza"),
            new PojoTipoComida(0, "Hamburguesa"),
            new PojoTipoComida(0, "Galletas"),
            new PojoTipoComida(0, "Ensalada de pollo"),
            new PojoTipoComida(0, "Filete de ternera"),
            new PojoTipoComida(0, "Filete de pollo")
    );

    public SeleccionComidasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_comidas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSeleccionComidas = view.findViewById(R.id.fscrvSeleccionComidas);
        rvSeleccionComidas.setLayoutManager(new LinearLayoutManager(getContext()));
        seleccionComidasAdapter = new SeleccionComidasAdapter(listaComidas, getContext());
        rvSeleccionComidas.setAdapter(seleccionComidasAdapter);
    }

}