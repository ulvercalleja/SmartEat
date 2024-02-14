package proyecto.smarteat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegistroPantalla extends AppCompatActivity {

    EditText etNombre, etPass, etPassRep, etEmail;
    Button btCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_registro);

        etNombre = findViewById(R.id.pretUsuario);
        etPass = findViewById(R.id.pretContraseña);
        etPassRep = findViewById(R.id.pretContraseñaRep);
        etEmail = findViewById(R.id.pretCorreo);
        btCrear = findViewById(R.id.prbtCrear);

        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String pass = etPass.getText().toString();
                String email = etEmail.getText().toString();

                // Insertar usuario en la base de datos
                ManejadorBaseDatos.insertarUsuario(nombre, pass, email);
            }
        });
    }
}