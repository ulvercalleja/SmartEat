package proyecto.smarteat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class ComidasFragment extends Fragment {
    private RecyclerView recyclerView;
    private ComidasFragmentAdapter adapter;
    private List<PojoTipoComida> listaFragmentComida;
    public ComidasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comidas, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFragmentComida);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize your list of Comidas
        listaFragmentComida = new ArrayList<>();
        // Add sample data for testing
        listaFragmentComida.add(new PojoTipoComida(0,"Croissant"));
        listaFragmentComida.add(new PojoTipoComida(0,"Napolitana"));
        listaFragmentComida.add(new PojoTipoComida(0,"Pizza"));
        listaFragmentComida.add(new PojoTipoComida(0,"Hamburguesa"));
        listaFragmentComida.add(new PojoTipoComida(0,"Filete ternera"));
        listaFragmentComida.add(new PojoTipoComida(0,"Patatas"));
        listaFragmentComida.add(new PojoTipoComida(0,"Zanahorias"));
        listaFragmentComida.add(new PojoTipoComida(0,"Burritos"));

        // Initialize and set the adapter
        adapter = new ComidasFragmentAdapter(listaFragmentComida, getActivity());
        recyclerView.setAdapter(adapter);
    }
}