package proyecto.smarteat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;


public class MenuPantalla extends AppCompatActivity {

    ImageButton btHome, btCalendario, btComida, btBuscar, btPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pantalla);

        btHome = findViewById(R.id.dspibBotonHome);

        btHome.setOnClickListener(v -> {
            cargarFragmentoDiasSemana();
        });
    }

    private void cargarFragmentoDiasSemana() {
        // Crear una instancia del fragmento
        DiasSemanaFragment fragment = new DiasSemanaFragment();

        // Obtener el FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Iniciar una transacción de fragmentos
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplazar el contenido del contenedor con el fragmento
        transaction.replace(R.id.mpfrFragmentListas, fragment);

        // Confirmar la transacción
        transaction.commit();
    }
}
