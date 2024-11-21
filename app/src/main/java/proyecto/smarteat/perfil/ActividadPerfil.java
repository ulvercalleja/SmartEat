package proyecto.smarteat.perfil;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import proyecto.smarteat.R;
import proyecto.smarteat.home.MenuPantalla;
import proyecto.smarteat.login.LoginPantalla;

public class ActividadPerfil extends AppCompatActivity {

    Button btCerrarSesion, btEditarPerfil;
    TextView tvUsername, tvEmail;
    EditText etNombreUsuario, etEmailUsuario,etContrasena,etNuevaContrasena;
    int userId;
    PerfilViewModel perfilViewModel;
    String contrasena;
    boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_perfil);

        btEditarPerfil = findViewById(R.id.fpbtEditarPerfil);
        btCerrarSesion = findViewById(R.id.fpbtCerrarSesion);
        etNombreUsuario = findViewById(R.id.fpetUsername);
        etEmailUsuario = findViewById(R.id.fpetEmail);
        etContrasena = findViewById(R.id.fpetContrasena);
        etNuevaContrasena = findViewById(R.id.fpetContrasenaNueva);

        // Obtener userId del Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(LoginPantalla.ID_USUARIO)) {
            userId = intent.getIntExtra(LoginPantalla.ID_USUARIO, -1);
        }

        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        perfilViewModel.getPerfil(userId).observe(this, perfil -> {
            etNombreUsuario.setText(perfil.getNombreUsuario());
            etEmailUsuario.setText(perfil.getEmail());
            contrasena = perfil.getPassword();
        });

        perfilViewModel.getSuccessMessage().observe(this, success -> {
            if (success != null && success) {
                new AlertDialog.Builder(this)
                        .setTitle("Éxito")
                        .setMessage("El cambio se ha efectuado correctamente")
                        .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                        .show();

                perfilViewModel.resetSuccessMessage();
            }
        });

        btEditarPerfil.setOnClickListener((vie) -> {
            if (!isEditing) {
                // Activar modo edición
                isEditing = true;
                btEditarPerfil.setText("Guardar");

                // Mostrar EditText y ocultar TextView

                etNombreUsuario.setVisibility(EditText.VISIBLE);
                etEmailUsuario.setVisibility(EditText.VISIBLE);
                etContrasena.setVisibility(EditText.VISIBLE);
                etNuevaContrasena.setVisibility(EditText.VISIBLE);
                etNombreUsuario.setEnabled(true);
                etEmailUsuario.setEnabled(true);
                etContrasena.setEnabled(true);
                etNuevaContrasena.setEnabled(true);
            } else {
                // Guardar cambios
                isEditing = false;
                btEditarPerfil.setText("Editar Perfil");

                // Validar los datos antes de guardarlos
                String nombreUsuario = etNombreUsuario.getText().toString();
                String email = etEmailUsuario.getText().toString();
                String contrasenaActual = etContrasena.getText().toString();
                String nuevaContrasena = etNuevaContrasena.getText().toString();

                etNombreUsuario.setEnabled(false);
                etEmailUsuario.setEnabled(false);
                etContrasena.setEnabled(false);
                etNuevaContrasena.setEnabled(false);
                etContrasena.setVisibility(EditText.GONE);
                etNuevaContrasena.setVisibility(EditText.GONE);

                // Validar campos
                if (!TextUtils.isEmpty(nombreUsuario) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(contrasenaActual)) {


                        if (contrasenaActual.equals(etContrasena.getText().toString())) {
                            // Si la contraseña es válida y hay una nueva contraseña
                            if (!TextUtils.isEmpty(nuevaContrasena)) {
                                PojoUsuario usuario = new PojoUsuario(userId, nombreUsuario, email, nuevaContrasena);
                                perfilViewModel.editarPerfil(usuario);
                            } else {
                                PojoUsuario usuario = new PojoUsuario(userId, nombreUsuario, email, contrasena);
                                perfilViewModel.editarPerfil(usuario);
                            }
                        } else {
                            new AlertDialog.Builder(this)
                                    .setTitle("Error")
                                    .setMessage("La contraseña actual es incorrecta.")
                                    .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                                    .show();
                        }


                    etEmailUsuario.setText(nombreUsuario);
                    etEmailUsuario.setText(email);

                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("Los campos no pueden estar vacíos.")
                            .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                            .show();
                }
            }
        });
    }
}