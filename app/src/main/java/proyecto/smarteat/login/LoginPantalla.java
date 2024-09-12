package proyecto.smarteat.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Start the next activity
                        Intent intent = new Intent(LoginPantalla.this, MenuPantalla.class);
                        startActivity(intent);
                        finish(); // Close the login activity
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginPantalla.this, "Error al iniciar sesión: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}