package proyecto.smarteat.perfil;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import proyecto.smarteat.R;
import proyecto.smarteat.login.InicioPantalla;

public class PerfilFragment extends Fragment {

    private Button btCerrarSesion;
    private TextView tvUsername, tvEmail;

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

        //tv de username
        tvUsername = view.findViewById(R.id.fptvUsername);
        //tv de email
        tvEmail = view.findViewById(R.id.fptvEmail);

        // Obtener los datos del usuario desde SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSession", getContext().MODE_PRIVATE);
        String userName = sharedPreferences.getString("user_name", "Unknown User");
        String userEmail = sharedPreferences.getString("user_email", "Undefined Email");

        // Establece los datos en las vistas
        tvUsername.setText(userName);
        tvEmail.setText(userEmail);


        return view;
    }
}