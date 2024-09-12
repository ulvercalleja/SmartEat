package proyecto.smarteat.perfil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import proyecto.smarteat.R;
import proyecto.smarteat.login.InicioPantalla;

public class PerfilFragment extends Fragment {

    private Button btCerrarSesion;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Encuentra el botón de cerrar sesión en el layout
        btCerrarSesion = view.findViewById(R.id.fpbtCerrarSesion);

        // Configura el listener para el botón de cerrar sesión
        btCerrarSesion.setOnClickListener(v -> {
            // Cerrar sesión con Firebase
            FirebaseAuth.getInstance().signOut();

            // Redirigir a la pantalla de login después de cerrar sesión
            Intent intent = new Intent(getActivity(), InicioPantalla.class);
            startActivity(intent);

            // Finaliza la actividad actual si es necesario
            getActivity().finish();
        });

        return view;
    }
}