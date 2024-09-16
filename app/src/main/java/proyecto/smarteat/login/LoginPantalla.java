package proyecto.smarteat.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import proyecto.smarteat.R;
import proyecto.smarteat.home.MenuPantalla;

public class LoginPantalla extends AppCompatActivity {

    private static final String ERROR_VACIO = "El campo está vacío:";
    EditText etUsuario, etContraseña;
    Button btIniciar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion_pantalla);

        etUsuario = findViewById(R.id.ispetUsuario);
        etContraseña = findViewById(R.id.ispetContraseña);
        btIniciar = findViewById(R.id.ispbtCrear);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btIniciar.setOnClickListener(v -> {
            String email = etUsuario.getText().toString();
            String password = etContraseña.getText().toString();

            checkErrors(email, password); //Comprueba errores, y si no hay ninguno ejecuta la funcion login
        });
    }

    private void checkErrors(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) {
                etUsuario.setError(ERROR_VACIO);
            } else {
                etUsuario.setError(null); // Borra cualquier mensaje de error existente
            }

            if (password.isEmpty()) {
                etContraseña.setError(ERROR_VACIO);
            } else {
                etContraseña.setError(null); // Borra cualquier mensaje de error existente
            }
        } else {
            loginUser(email, password);
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso, obtenemos la información del usuario
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userEmail = user.getEmail();
                            String userName = user.getDisplayName(); // El nombre puede estar vacío si no se configura

                            // Guarda los datos del usuario en SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_name", userName != null ? userName : "Usuario Anónimo");
                            editor.putString("user_email", userEmail);
                            editor.apply();

                            // Navega a la siguiente pantalla (Menú o Perfil)
                            Intent intent = new Intent(LoginPantalla.this, MenuPantalla.class);
                            startActivity(intent);
                            finish(); // Cierra la actividad de login
                        }
                    } else {
                        // Si falla el inicio de sesión, muestra un mensaje al usuario
                        Toast.makeText(LoginPantalla.this, "Error al iniciar sesión: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}