package proyecto.smarteat.perfil;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import proyecto.smarteat.R;
import proyecto.smarteat.auth.InicioPantalla;
import proyecto.smarteat.auth.login.LoginPantalla;

public class ActividadPerfil extends AppCompatActivity {
    private static final String SHARED_PREFS = "user_session";
    private static final int PICK_IMAGE_REQUEST = 1;

    Button btCerrarSesion, btEditarPerfil;
    EditText etNombreUsuario, etEmailUsuario,etContrasena,etNuevaContrasena;
    ImageView ivCambiarFoto, ivFotoPerfil;
    PerfilViewModel perfilViewModel;
    int userId;
    String contrasenaOriginal, nombreUsuarioOriginal, emailOriginal;
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
        ivCambiarFoto = findViewById(R.id.fpivCambiarFotoPerfil);
        ivFotoPerfil = findViewById(R.id.fpivFotoPerfil);

        // Obtener userId del Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(LoginPantalla.ID_USUARIO)) {
            userId = intent.getIntExtra(LoginPantalla.ID_USUARIO, -1);
        }

        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        perfilViewModel.getPerfil(userId).observe(this, perfil -> {
            etNombreUsuario.setText(perfil.getNombreUsuario());
            etEmailUsuario.setText(perfil.getEmail());
            contrasenaOriginal = perfil.getPassword();
            nombreUsuarioOriginal = perfil.getNombreUsuario();
            emailOriginal = perfil.getEmail();
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

        btCerrarSesion.setOnClickListener((view) -> {
            logout();
        });

        btEditarPerfil.setOnClickListener((vie) -> {
            if (!isEditing) {
                // Activar modo edición
                isEditing = true;
                btEditarPerfil.setText("Guardar");

                // Mostrar EditText y ocultar TextView

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
                if (!TextUtils.isEmpty(nombreUsuario) && !TextUtils.isEmpty(email)) {

                    if (nombreUsuario.equals(nombreUsuarioOriginal) && email.equals(emailOriginal) && nuevaContrasena.isEmpty()) {
                        PojoUsuario usuario = new PojoUsuario(userId, nombreUsuario, email, contrasenaOriginal);
                        perfilViewModel.editarPerfil(usuario);
                    } else {
                        // Si hay cambios, validar contraseña si se requiere
                        if (TextUtils.isEmpty(contrasenaActual) || !contrasenaOriginal.equals(contrasenaActual)) {
                            new AlertDialog.Builder(this)
                                    .setTitle("Error")
                                    .setMessage("La contraseña actual es incorrecta.")
                                    .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                                    .show();
                        } else {
                            // Si la contraseña es válida y hay una nueva contraseña
                            if (!TextUtils.isEmpty(nuevaContrasena)) {
                                PojoUsuario usuario = new PojoUsuario(userId, nombreUsuario, email, nuevaContrasena);
                                perfilViewModel.editarPerfil(usuario);
                            } else {
                                PojoUsuario usuario = new PojoUsuario(userId, nombreUsuario, email, contrasenaOriginal);
                                perfilViewModel.editarPerfil(usuario);
                            }
                        }
                    }
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("Los campos no pueden estar vacíos.")
                            .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                            .show();
                }
                    etEmailUsuario.setText(nombreUsuario);
                    etEmailUsuario.setText(email);
            }

        });

        ivCambiarFoto.setOnClickListener(view -> openFilePicker());
    }
    // Abrir selector de imágenes
    private void openFilePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccion una imagen"), PICK_IMAGE_REQUEST);
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).edit();
        editor.clear(); // Eliminar todos los datos guardados
        editor.apply();

        // Redirigir al LoginPantalla
        Intent intent = new Intent(this, InicioPantalla.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}