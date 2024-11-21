package proyecto.smarteat.perfil;

import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends ViewModel {
    private MutableLiveData<PojoUsuario> perfil;
    private MutableLiveData<Boolean> successMessage = new MutableLiveData<>();
    public int idPerfil;

    public LiveData<PojoUsuario> getPerfil(int id) {
        idPerfil = id;
        if (perfil == null) {
            perfil = new MutableLiveData<>();
            generarPerfil(idPerfil);
        }
        return perfil;
    }

    public LiveData<Boolean> getSuccessMessage() {
        return successMessage;
    }

    public void generarPerfil(int id) {
        new Thread(() -> {
            ApiPerfil ser = ApiPerfil.getInstancia();
            Call<PojoUsuario> llamada = ser.getRepo().getUsuarioPorId(id);
            llamada.enqueue(new Callback<PojoUsuario>() {
                @Override
                public void onResponse(Call<PojoUsuario> call, Response<PojoUsuario> response) {
                    if (response.isSuccessful()) {
                        PojoUsuario user = response.body();
                        perfil.postValue(user);
                    }
                }

                @Override
                public void onFailure(Call<PojoUsuario> call, Throwable t) {
                    System.out.println("Error en la llamada: " + t.getMessage());
                }
            });
        }).start();
    }

    public void editarPerfil(PojoUsuario usuario) {
        //new Thread(() -> {
        ApiPerfil ser = ApiPerfil.getInstancia();
        Call<PojoUsuario> llamada = ser.getRepo().actualizarUsuario(usuario);
        llamada.enqueue(new Callback<PojoUsuario>() {
            @Override
            public void onResponse(Call<PojoUsuario> call, Response<PojoUsuario> response) {
                if (response.isSuccessful()) {
                    successMessage.postValue(true);
                }
            }
            @Override
            public void onFailure(Call<PojoUsuario> call, Throwable t) {
            }
        });
        //}).start();
    }

    public void resetSuccessMessage() {
        successMessage.setValue(false);
    }

}
