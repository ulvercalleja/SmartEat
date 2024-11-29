package proyecto.smarteat.auth.register;
import proyecto.smarteat.ConstantUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import org.mindrot.jbcrypt.BCrypt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import proyecto.smarteat.R;
import proyecto.smarteat.auth.AuthViewModel;
import proyecto.smarteat.auth.login.LoginPantalla;
import proyecto.smarteat.perfil.PojoUsuario;

public class RegistroPantalla extends AppCompatActivity {

    EditText etNombreUsuario, etPass, etPassRep, etEmail;
    TextView tvError;
    Button btCrear;
    AuthViewModel inicioViewModel;
    PojoUsuario nuevoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_pantalla);

        etNombreUsuario = findViewById(R.id.ispetUsuario);
        etPass = findViewById(R.id.ispetContraseña);
        etPassRep = findViewById(R.id.pretContraseñaRep);
        etEmail = findViewById(R.id.pretCorreo);
        tvError = findViewById(R.id.prtvTextoError);
        btCrear = findViewById(R.id.ispbtCrear);

        inicioViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        btCrear.setOnClickListener(v -> {
            String nombre = etNombreUsuario.getText().toString();
            String password = etPass.getText().toString();
            String passRep = etPassRep.getText().toString();
            String email = etEmail.getText().toString();

            if (checkErrors(nombre, password, passRep, email)) {
                registerUser(nombre, email, password);
            }
        });
    }

    private Boolean checkErrors(String nombre, String pass, String passRep, String email) {
        if (nombre.isEmpty() || pass.isEmpty() || passRep.isEmpty() || email.isEmpty()) {
            tvError.setText(ConstantUtils.REGISTER_MSG_CAMPOS_REQUERIDOS);
            tvError.setTextColor(Color.RED);
            return false;
        }

        if (!pass.equals(passRep)) {
            tvError.setText(ConstantUtils.REGISTER_MSG_CONTRASENAS_NO_COINCIDEN);
            tvError.setTextColor(Color.RED);
            return false;
        }

        String emailPattern = ConstantUtils.REGISTER_EMAIL_PATTERN;
        if (!email.matches(emailPattern)) {
            tvError.setText(ConstantUtils.REGISTER_MSG_EMAIL_INVALIDO);
            tvError.setTextColor(Color.RED);
            return false;
        }

        tvError.setText("");
        return true;
    }

    private void registerUser(String nombre, String email, String password) {
        // Cifrar la contraseña antes de crear el objeto PojoUsuario
        String passwordHash = hashPassword(password);
        System.out.println(passwordHash);
        // Crear el objeto PojoUsuario
        nuevoUsuario = new PojoUsuario(nombre, email, passwordHash);

        // Llamar al ViewModel para crear el usuario
        inicioViewModel.crearUsuario(nuevoUsuario);

        // Observar el resultado
        inicioViewModel.getSuccessMessage().observe(this, success -> {
            if (success != null) {
                if (success) {
                    tvError.setText(ConstantUtils.REGISTER_MSG_USUARIO_CREADO);
                    tvError.setTextColor(Color.GREEN);
                    limpiarCampos();
                    Intent i = new Intent(this, LoginPantalla.class);
                    startActivity(i);
                    finish();
                } else {
                    tvError.setText(ConstantUtils.REGISTER_MSG_ERROR_CREAR_USUARIO);
                    tvError.setTextColor(Color.RED);
                }
                // Restablecer el estado para evitar múltiples mensajes
                inicioViewModel.resetSuccessMessage();
            }
        });
    }

    private void limpiarCampos() {
        etNombreUsuario.setText("");
        etPass.setText("");
        etPassRep.setText("");
        etEmail.setText("");
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // "12" es el factor de trabajo de la sal
    }

}
