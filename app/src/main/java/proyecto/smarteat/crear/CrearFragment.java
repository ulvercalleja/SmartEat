package proyecto.smarteat.crear;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import proyecto.smarteat.ConstantUtils;
import proyecto.smarteat.home.MenuPantalla;
import proyecto.smarteat.R;
import proyecto.smarteat.home.comidas.seleccion.ApiAlimentos;
import proyecto.smarteat.home.comidas.seleccion.PojoAlimentos;
import proyecto.smarteat.home.comidas.seleccion.RepoAlimentos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearFragment extends Fragment {

    private EditText etNombreComida, etCalorias, etGrasas, etHidratos, etProteinas;
    private ImageView ivFotoComida, ivCambiarFoto, ivGuardar;
    private int idUsuario;

    public CrearFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crea, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNombreComida = view.findViewById(R.id.fcetNombreComida);
        etCalorias = view.findViewById(R.id.fcetCalorias);
        etGrasas = view.findViewById(R.id.fcetGrasas);
        etHidratos = view.findViewById(R.id.fcetHidratos);
        etProteinas = view.findViewById(R.id.fcetProteinas);
        ivFotoComida = view.findViewById(R.id.fcivFotoComida);
        ivGuardar = view.findViewById(R.id.fcivBotonCrear);
        ivCambiarFoto = view.findViewById(R.id.fcivCambiarFotoComida);

        ivCambiarFoto.setOnClickListener(v -> abrirGaleria());

        if (getActivity() instanceof MenuPantalla) {
            MenuPantalla menuPantalla = (MenuPantalla) getActivity();
            idUsuario = menuPantalla.getUserId(); // Obtener el ID del usuario
        } else {
            idUsuario = -1;
        }

        ivGuardar.setOnClickListener(v -> {
            if (validarCampos()) {
                guardarReceta(idUsuario);
            }
        });
    }

    private boolean validarCampos() {
        if (TextUtils.isEmpty(etNombreComida.getText().toString().trim())) {
            etNombreComida.setError(ConstantUtils.CREAR_MSG_ERROR_CAMPO_OBLIGATORIO);
            etNombreComida.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etCalorias.getText().toString().trim())) {
            etCalorias.setError(ConstantUtils.CREAR_MSG_ERROR_CAMPO_OBLIGATORIO);
            etCalorias.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etGrasas.getText().toString().trim())) {
            etGrasas.setError(ConstantUtils.CREAR_MSG_ERROR_CAMPO_OBLIGATORIO);
            etGrasas.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etHidratos.getText().toString().trim())) {
            etHidratos.setError(ConstantUtils.CREAR_MSG_ERROR_CAMPO_OBLIGATORIO);
            etHidratos.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etProteinas.getText().toString().trim())) {
            etProteinas.setError(ConstantUtils.CREAR_MSG_ERROR_CAMPO_OBLIGATORIO);
            etProteinas.requestFocus();
            return false;
        }

        return true;
    }

    private void guardarReceta(int idUsuario) {
        // Recoger los valores de los campos
        String nombre = etNombreComida.getText().toString().trim();
        int valorCalorico = Integer.parseInt(etCalorias.getText().toString().trim());
        int grasas = Integer.parseInt(etGrasas.getText().toString().trim());
        int hidratos = Integer.parseInt(etHidratos.getText().toString().trim());
        int proteinas = Integer.parseInt(etProteinas.getText().toString().trim());

        // Convertir la imagen a Base64
        String imagenBase64 = convertirImagenAStringBase64(ivFotoComida);

        PojoAlimentos alimento = new PojoAlimentos(valorCalorico, nombre, imagenBase64,
                grasas, proteinas, hidratos);

        // Inicializa Retrofit
        RepoAlimentos apiService = ApiAlimentos.getInstancia().create(RepoAlimentos.class);

        // Llamada a la API para obtener la lista de alimentos
        Call<Void> call = apiService.addComida(idUsuario, alimento);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    ConstantUtils.customSnackBarExito(
                            getActivity(),
                            getView(),
                            R.layout.csb_operacion_exitosa // El layout que quieres usar
                    );
                } else {
                    ConstantUtils.customSnackBarError(
                            getActivity(),
                            getView(),
                            R.layout.csb_operacion_error // El layout que quieres usar
                    );
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(ConstantUtils.CREAR_TAG_RESPUESTA_API, ConstantUtils.CREAR_TAG_ERROR_API, t);
            }
        });
    }

    // Método para abrir la galería de imágenes
    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, ConstantUtils.CREAR_PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantUtils.CREAR_PICK_IMAGE_REQUEST && resultCode ==
                getActivity().RESULT_OK && data != null && data.getData() != null) {
            // Obtener la URI de la imagen seleccionada
            Uri imageUri = data.getData();

            try {
                // Convertir la URI en un Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                // Establecer el Bitmap como imagen en el ImageView
                ivFotoComida.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(ConstantUtils.CREAR_TAG_RESPUESTA_GALERIA, ConstantUtils.CREAR_LOG_ERROR_GALERIA, e);
            }
        }
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

