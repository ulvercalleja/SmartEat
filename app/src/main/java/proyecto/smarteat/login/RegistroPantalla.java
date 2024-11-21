package proyecto.smarteat.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import proyecto.smarteat.R;

public class RegistroPantalla extends AppCompatActivity {

    EditText etNombre, etPass, etPassRep, etEmail;
    TextView tvError;
    Button btCrear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_pantalla);

        etNombre = findViewById(R.id.ispetUsuario);
        etPass = findViewById(R.id.ispetContraseña);
        etPassRep = findViewById(R.id.pretContraseñaRep);
        etEmail = findViewById(R.id.pretCorreo);
        tvError = findViewById(R.id.prtvTextoError);
        btCrear = findViewById(R.id.ispbtCrear);

        btCrear.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String pass = etPass.getText().toString();
            String passRep = etPassRep.getText().toString();
            String email = etEmail.getText().toString();

            if (checkErrors(nombre, pass, passRep, email)) {
                registerUser(nombre, email, pass);
            }
        });
    }

    private Boolean checkErrors(String nombre, String pass, String passRep, String email) {
        if (nombre.isEmpty() || pass.isEmpty() || passRep.isEmpty() || email.isEmpty()) {
            tvError.setText("Todos los campos son requeridos");
            return false;
        }

        if (!pass.equals(passRep)) {
            tvError.setText("Las contraseñas no coinciden");
            return false;
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            tvError.setText("Correo electrónico inválido");
            return false;
        }

        tvError.setText("");
        return true;
    }

    private void registerUser(String nombre, String email, String password) {
        String url = "http://10.0.2.2:8080/usuarios/addOne";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombreUsuario", nombre);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue.add(request);
    }


}
