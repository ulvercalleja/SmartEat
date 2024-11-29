package proyecto.smarteat.perfil;

import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;

import proyecto.smarteat.ConstantUtils;
import proyecto.smarteat.R;
import proyecto.smarteat.InicioPantalla;
import proyecto.smarteat.auth.login.LoginPantalla;

public class ActividadPerfil extends AppCompatActivity {
    private static final String SHARED_PREFS = "user_session";
    private static final int PICK_IMAGE_REQUEST = 1;

    Button btCerrarSesion, btEditarPerfil;
    EditText etNombreUsuario, etEmailUsuario,etContrasena,etNuevaContrasena;
    ImageView ivCambiarFoto, ivFotoPerfil, ivVolver;
    TextView tvError;
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
        tvError = findViewById(R.id.fptvError);
        ivCambiarFoto = findViewById(R.id.fpivCambiarFotoPerfil);
        ivFotoPerfil = findViewById(R.id.fpivFotoPerfil);
        ivVolver = findViewById(R.id.fpivVolver);

        // Obtener userId del Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(LoginPantalla.ID_USUARIO)) {
            userId = intent.getIntExtra(LoginPantalla.ID_USUARIO, -1);
        }

        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        ivVolver.setOnClickListener(v -> {
            finish();
        });

        perfilViewModel.getPerfil(userId).observe(this, perfil -> {
            etNombreUsuario.setText(perfil.getNombreUsuario());
            etEmailUsuario.setText(perfil.getEmail());
            contrasenaOriginal = perfil.getPassword();
            nombreUsuarioOriginal = perfil.getNombreUsuario();
            emailOriginal = perfil.getEmail();
        });

        perfilViewModel.getSuccessMessage().observe(this, success -> {
            if (success != null && success) {
                ConstantUtils.customSnackBarExito(
                        this,
                        findViewById(android.R.id.content),
                        R.layout.csb_operacion_exitosa // El layout que quieres usar
                );

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
                tvError.setText("");
                String nombreUsuario = etNombreUsuario.getText().toString();
                String email = etEmailUsuario.getText().toString();
                String contrasenaActual = etContrasena.getText().toString();
                String nuevaContrasena = etNuevaContrasena.getText().toString();
                String imagenBase64 = convertirImagenAStringBase64(ivFotoPerfil);

                etNombreUsuario.setEnabled(false);
                etEmailUsuario.setEnabled(false);
                etContrasena.setEnabled(false);
                etNuevaContrasena.setEnabled(false);
                etContrasena.setVisibility(EditText.GONE);
                etNuevaContrasena.setVisibility(EditText.GONE);

                // Validar campos
                if (!TextUtils.isEmpty(nombreUsuario) && !TextUtils.isEmpty(email)) {

                    if (nombreUsuario.equals(nombreUsuarioOriginal) && email.equals(emailOriginal) && nuevaContrasena.isEmpty()) {
                        PojoUsuario usuario = new PojoUsuario(userId, nombreUsuario, imagenBase64, email, contrasenaOriginal);
                        perfilViewModel.editarPerfil(usuario);
                    } else {
                        // Si hay cambios, validar contraseña si se requiere
                        if (TextUtils.isEmpty(contrasenaActual) || !contrasenaOriginal.equals(contrasenaActual)) {
                            tvError.setText("La contraseña actual es incorrecta.");
                        } else {
                            // Si la contraseña es válida y hay una nueva contraseña
                            if (!TextUtils.isEmpty(nuevaContrasena)) {
                                tvError.setText("");
                                PojoUsuario usuario = new PojoUsuario(userId, nombreUsuario, imagenBase64, email, nuevaContrasena);
                                perfilViewModel.editarPerfil(usuario);

                            } else {
                                tvError.setText("");
                                PojoUsuario usuario = new PojoUsuario(userId, nombreUsuario, imagenBase64, email, contrasenaOriginal);
                                perfilViewModel.editarPerfil(usuario);
                            }
                        }
                    }
                } else {
                    tvError.setText("Los campos no pueden estar vacíos.");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Obtener la URI de la imagen seleccionada
            try {
                // Convertir la URI a un Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                // Establecer el Bitmap en ivFotoPerfil
                ivFotoPerfil.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    private String convertirImagenAStringBase64(ImageView imageView) {
        // Obtener la imagen del ImageView
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        // Convertir la imagen a un array de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Convertir el array de bytes a Base64
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}

