package proyecto.smarteat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import proyecto.smarteat.auth.login.LoginPantalla;
import proyecto.smarteat.auth.register.RegistroPantalla;
import proyecto.smarteat.home.MenuPantalla;

public class InicioPantalla extends AppCompatActivity {

    ImageButton btRegistroCorreo, btInicioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_pantalla);

        btRegistroCorreo = findViewById(R.id.ipibBotonCorreo);
        btInicioSesion = findViewById(R.id.ipibIniciarSesion);

        // Verificar si el usuario ya está logueado
        if (isUserLoggedIn()) {
            redirectToMenu();
            return; // Evitar seguir ejecutando el resto del código
        }

        btInicioSesion.setOnClickListener(v -> {
            // Abre la pantalla de inicio de sesión
            Intent intent = new Intent(InicioPantalla.this, LoginPantalla.class);
            startActivity(intent);
        });

        btRegistroCorreo.setOnClickListener(v -> {
            // Abre la pantalla de registro
            Intent intent = new Intent(InicioPantalla.this, RegistroPantalla.class);
            startActivity(intent);
        });
    }


    // Comprueba si el usuario está logueado.

    private boolean isUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(ConstantUtils.LOGIN_SHARED_PREFS, MODE_PRIVATE);
        return prefs.contains(ConstantUtils.LOGIN_KEY_USER_ID); // Devuelve true si el usuario tiene un ID guardado
    }


     // Redirige al usuario a la pantalla principal.

    private void redirectToMenu() {
        SharedPreferences prefs = getSharedPreferences(ConstantUtils.LOGIN_SHARED_PREFS, MODE_PRIVATE);
        int userId = prefs.getInt(ConstantUtils.LOGIN_KEY_USER_ID, -1);

        Intent intent = new Intent(this, MenuPantalla.class);
        intent.putExtra(ConstantUtils.LOGIN_ID_USUARIO, userId); // Pasa el ID del usuario a MenuPantalla
        startActivity(intent);
        finish(); // Finaliza la actividad actual para evitar volver aquí
    }

}