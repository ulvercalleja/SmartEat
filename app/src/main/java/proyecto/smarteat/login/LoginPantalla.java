package proyecto.smarteat.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import proyecto.smarteat.R;
import proyecto.smarteat.home.MenuPantalla;

public class LoginPantalla extends AppCompatActivity {
    public static final String DATOS_INCORRECTOS="El correo o la contraseña son incorrectos";
    private static final String ERROR_VACIO = "El campo está vacío:";
    public static final String ID_USUARIO = "id del usuario" ;
    EditText etCorreo, etContraseña;
    Button btIniciar;
    LoginViewModel inicioViewModel;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion_pantalla);

        builder = new AlertDialog.Builder(this);
        etCorreo = findViewById(R.id.ispetCorreo);
        etContraseña = findViewById(R.id.ispetContraseña);
        btIniciar = findViewById(R.id.ispbtCrear);
        inicioViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // Inicializa ApiUsuario antes de usar
        ApiUsuario.getInstancia();

        btIniciar.setOnClickListener(v -> {
            String email = etCorreo.getText().toString();
            String password = etContraseña.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, ERROR_VACIO + " Correo", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, ERROR_VACIO + " Contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            inicioViewModel.login(email, password);

            inicioViewModel.getRespuestaLogin().observe(this, respuestaLogin -> {
                if (respuestaLogin.isDatosCorrectos()) {
                    Intent i = new Intent(this, MenuPantalla.class);
                    i.putExtra(ID_USUARIO, respuestaLogin.getUserId().intValue());
                    startActivity(i);
                    finish();
                } else {
                    builder.setMessage(DATOS_INCORRECTOS)
                            .setPositiveButton("Entendido", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        });

    }

}
