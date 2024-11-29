package proyecto.smarteat.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageButton;
import android.content.Intent;

import proyecto.smarteat.ConstantUtils;
import proyecto.smarteat.R;
import proyecto.smarteat.crear.CrearFragment;
import proyecto.smarteat.calendario.CalendarioFragment;
import proyecto.smarteat.auth.login.LoginPantalla;
import proyecto.smarteat.home.comidas.ComidasFragment;
import proyecto.smarteat.perfil.ActividadPerfil;


public class MenuPantalla extends AppCompatActivity {

    ImageButton btHome, btCalendario, btCrear, btPerfil;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pantalla);

        btHome = findViewById(R.id.dspibBotonHome);
        btCalendario = findViewById(R.id.dspibBotonCalendario);
        btCrear = findViewById(R.id.dspibBotonCrear);
        btPerfil = findViewById(R.id.dspibBotonPerfil);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(ConstantUtils.LOGIN_ID_USUARIO)) {
            userId = intent.getIntExtra(ConstantUtils.LOGIN_ID_USUARIO, -1);
        }

        btHome.setOnClickListener(v -> {
            cargarFragmentoDiasSemana();
        });

        btCalendario.setOnClickListener(v -> {
            cargarFragmentoCalendario();
        });

        btCrear.setOnClickListener(v -> {
            cargarFragmentoCrear();
        });

        btPerfil.setOnClickListener(v -> {
            cargarActividadPerfil();
        });

    }

    private void cargarFragmentoDiasSemana() {
        // Crear una instancia del fragmento
        ComidasFragment fragmentDiasSemana = new ComidasFragment();

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

    private void cargarFragmentoCrear() {
        CrearFragment fragmentBuscar = new CrearFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mpfrFragmentListas, fragmentBuscar);
        transaction.commit();
    }

    private void cargarActividadPerfil() {
        Intent intent = new Intent(this, ActividadPerfil.class);
        intent.putExtra(ConstantUtils.LOGIN_ID_USUARIO, userId); // Pasar el ID del usuario
        startActivity(intent);
    }

    public int getUserId() {
        return userId;
    }
}
