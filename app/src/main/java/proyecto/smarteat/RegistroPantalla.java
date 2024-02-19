package proyecto.smarteat;

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

import java.util.HashMap;
import java.util.Map;


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
        tvError =  findViewById(R.id.prtvTextoError);
        btCrear = findViewById(R.id.ispbtCrear);

        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                // Aquí puedes agregar tu lógica para registrar al usuario en la base de datos o en el sistema
                ejecutarServicio("../../../../../../insertarUsuario.php");
                // Si llega hasta aquí, no hay errores
                tvError.setText(""); // Limpiar el mensaje de error
            }
        });
    }
    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nombre", etNombre.getText().toString());
                parametros.put("password", etPass.getText().toString());
                parametros.put("email", etEmail.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}