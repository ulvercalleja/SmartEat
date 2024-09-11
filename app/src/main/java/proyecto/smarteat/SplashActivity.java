package proyecto.smarteat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import proyecto.smarteat.login.RegistroPantalla;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Mantener la splash screen por unos segundos (ej. 3 segundos)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Iniciar la siguiente actividad
                Intent intent = new Intent(SplashActivity.this, RegistroPantalla.class);
                startActivity(intent);
                finish(); // Cierra la SplashActivity para que no se vuelva al pulsar atrás
            }
        }, 3000); // 3000 milisegundos = 3 segundos
    }
}
