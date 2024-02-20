package proyecto.smarteat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class IniciarSesionPantalla extends AppCompatActivity {

    private static final String ERROR_VACIO = "El campo está vacío:";
    EditText etUsuario, etContraseña;
    Button btIniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion_pantalla);

        etUsuario = findViewById(R.id.ispetUsuario);
        etContraseña = findViewById(R.id.ispetContraseña);
        btIniciar = findViewById(R.id.ispbtCrear);

        btIniciar.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String contraseña = etContraseña.getText().toString();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                if (usuario.isEmpty()) {
                    etUsuario.setError(ERROR_VACIO);
                } else {
                    etUsuario.setError(null); // Borra cualquier mensaje de error existente
                }

                if (contraseña.isEmpty()) {
                    etContraseña.setError(ERROR_VACIO);
                } else {
                    etContraseña.setError(null); // Borra cualquier mensaje de error existente
                }
            } else {
                Intent intent = new Intent(IniciarSesionPantalla.this, MenuPantalla.class);
                startActivity(intent);
                finish();
            }
        });
    }
}