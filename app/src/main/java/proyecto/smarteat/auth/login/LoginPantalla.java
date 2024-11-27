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
import proyecto.smarteat.R;
import proyecto.smarteat.auth.AuthApi;
import proyecto.smarteat.auth.AuthViewModel;
import proyecto.smarteat.home.MenuPantalla;

public class LoginPantalla extends AppCompatActivity {

    private static final String SHARED_PREFS = "user_session";
    private static final String KEY_USER_ID = "user_id";
    public static final String DATOS_INCORRECTOS="El correo o la contraseña son incorrectos";
    private static final String ERROR_VACIO = "El campo está vacío:";
    public static final String ID_USUARIO = "id del usuario" ;

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
                tvError.setText(ERROR_VACIO + " Correo");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                tvError.setText(ERROR_VACIO + " Contraseña");
                return;
            }

            inicioViewModel.login(email, password);

            inicioViewModel.getRespuestaLogin().observe(this, respuestaLogin -> {
                if (respuestaLogin.isDatosCorrectos()) {
                    // Guardar ID de usuario en SharedPreferences
                    SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).edit();
                    editor.putInt(KEY_USER_ID, respuestaLogin.getUserId().intValue());
                    editor.apply();

                    Intent i = new Intent(this, MenuPantalla.class);
                    i.putExtra(ID_USUARIO, respuestaLogin.getUserId().intValue());
                    startActivity(i);
                    finish();
                } else {
                    tvError.setText(DATOS_INCORRECTOS);
                }
            });
        });

    }

}
