package proyecto.smarteat.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

import proyecto.smarteat.R;


public class RegistroPantalla extends AppCompatActivity {

    EditText etNombre, etPass, etPassRep, etEmail;
    TextView tvError;
    Button btCrear;

    // Firebase Auth instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_pantalla);

        etNombre = findViewById(R.id.ispetUsuario);
        etPass = findViewById(R.id.ispetContraseña);
        etPassRep = findViewById(R.id.pretContraseñaRep);
        etEmail = findViewById(R.id.pretCorreo);
        tvError =  findViewById(R.id.prtvTextoError);
        btCrear = findViewById(R.id.ispbtCrear);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btCrear.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String pass = etPass.getText().toString();
            String passRep = etPassRep.getText().toString();
            String email = etEmail.getText().toString();

            // Validar que ningún campo esté en blanco
            if (nombre.isEmpty() || pass.isEmpty() || passRep.isEmpty() || email.isEmpty()) {
                tvError.setText("Todos los campos son requeridos");
                return;
            }

            // Validar que las contraseñas coincidan
            if (!pass.equals(passRep)) {
                tvError.setText("Las contraseñas no coinciden");
                return;
            }

            // Validar el formato del correo electrónico usando una expresión regular (regex)
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (!email.matches(emailPattern)) {
                tvError.setText("Correo electrónico inválido");
                return;
            }

            // Si llega hasta aquí, no hay errores
            tvError.setText(""); // Limpiar el mensaje de error

            registrarUser(email, pass);
        });
    }

    private void registrarUser(String email, String password) {
        // Registro en Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registro exitoso
                        Toast.makeText(RegistroPantalla.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        // Aquí podrías redirigir al usuario a otra pantalla o cerrar esta actividad
                        finish();
                    } else {
                        // Error en el registro
                        tvError.setText("Error al registrar: " + task.getException().getMessage());
                    }
                });
    }
}