package proyecto.smarteat.auth.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import proyecto.smarteat.ConstantUtils;
import proyecto.smarteat.R;
import proyecto.smarteat.auth.AuthApi;
import proyecto.smarteat.auth.AuthViewModel;
import proyecto.smarteat.home.MenuPantalla;

public class LoginPantalla extends AppCompatActivity {

    EditText etCorreo, etContraseña;
    Button btIniciar;
    AuthViewModel inicioViewModel;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion_pantalla);

        etCorreo = findViewById(R.id.ispetCorreo);
        etContraseña = findViewById(R.id.ispetContraseña);
        btIniciar = findViewById(R.id.ispbtCrear);
        tvError = findViewById(R.id.isptvError);

        inicioViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        // Inicializa ApiUsuario antes de usar
        AuthApi.getInstancia();

        btIniciar.setOnClickListener(v -> {
            String email = etCorreo.getText().toString();
            String password = etContraseña.getText().toString();

            if (TextUtils.isEmpty(email)) {
                tvError.setText(ConstantUtils.LOGIN_ERROR_VACIO + " Correo");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                tvError.setText(ConstantUtils.LOGIN_ERROR_VACIO + " Contraseña");
                return;
            }

            inicioViewModel.login(email, password);

            inicioViewModel.getRespuestaLogin().observe(this, respuestaLogin -> {
                if (respuestaLogin.isDatosCorrectos()) {
                    // Guardar ID de usuario en SharedPreferences
                    SharedPreferences.Editor editor = getSharedPreferences(ConstantUtils.LOGIN_SHARED_PREFS, MODE_PRIVATE).edit();
                    editor.putInt(ConstantUtils.LOGIN_KEY_USER_ID, respuestaLogin.getUserId().intValue());
                    editor.apply();

                    Intent i = new Intent(this, MenuPantalla.class);
                    i.putExtra(ConstantUtils.LOGIN_ID_USUARIO, respuestaLogin.getUserId().intValue());
                    startActivity(i);
                    finish();
                } else {
                    tvError.setText(ConstantUtils.LOGIN_DATOS_INCORRECTOS);
                }
            });
        });

    }

}
