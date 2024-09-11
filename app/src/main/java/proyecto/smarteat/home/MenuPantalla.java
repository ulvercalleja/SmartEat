package proyecto.smarteat.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageButton;

import proyecto.smarteat.R;
import proyecto.smarteat.buscar.BuscarFragment;
import proyecto.smarteat.calendario.CalendarioFragment;
import proyecto.smarteat.perfil.PerfilFragment;


public class MenuPantalla extends AppCompatActivity {

    ImageButton btHome, btCalendario, btComida, btBuscar, btPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pantalla);

        btHome = findViewById(R.id.dspibBotonHome);
        btCalendario = findViewById(R.id.dspibBotonCalendario);
        btComida = findViewById(R.id.dspibBotonComida);
        btBuscar = findViewById(R.id.dspibBotonBuscar);
        btPerfil = findViewById(R.id.dspibBotonPerfil);

        btHome.setOnClickListener(v -> {
            cargarFragmentoDiasSemana();
        });

        btCalendario.setOnClickListener(v -> {
            cargarFragmentoCalendario();
        });

        btBuscar.setOnClickListener(v -> {
            cargarFragmentoBuscar();
        });

        btPerfil.setOnClickListener(v -> {
            cargarFragmentoPerfil();
        });

    }

    private void cargarFragmentoDiasSemana() {
        // Crear una instancia del fragmento
        DiasSemanaFragment fragmentDiasSemana = new DiasSemanaFragment();

        // Obtener el FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Iniciar una transacción de fragmentos
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplazar el contenido del contenedor con el fragmento
        transaction.replace(R.id.mpfrFragmentListas, fragmentDiasSemana);

        // Confirmar la transacción
        transaction.commit();
    }

    private void cargarFragmentoCalendario() {
        CalendarioFragment fragmentCalendario = new CalendarioFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mpfrFragmentListas, fragmentCalendario);
        transaction.commit();
    }

    private void cargarFragmentoBuscar() {
        BuscarFragment fragmentBuscar = new BuscarFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mpfrFragmentListas, fragmentBuscar);
        transaction.commit();
    }

    private void cargarFragmentoPerfil() {
        PerfilFragment fragmentPerfil = new PerfilFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mpfrFragmentListas, fragmentPerfil);
        transaction.commit();
    }
}
