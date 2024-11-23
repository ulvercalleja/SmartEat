package proyecto.smarteat.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import proyecto.smarteat.auth.login.RespuestaLogin;
import proyecto.smarteat.perfil.PojoUsuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
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

    public void generarPerfil(int id) {
        new Thread(() -> {
            AuthApi ser = AuthApi.getInstancia();
            Call<PojoUsuario> llamada = ser.getRepo().getUsuarioPorId(id);
            llamada.enqueue(new Callback<PojoUsuario>() {
                @Override
                public void onResponse(Call<PojoUsuario> call, Response<PojoUsuario> response) {
                    if (response.isSuccessful()) {
                        PojoUsuario c = response.body();
                        perfil.postValue(c);
                    }
                }

                @Override
                public void onFailure(Call<PojoUsuario> call, Throwable t) {
                    System.out.println("Error en la llamada: " + t.getMessage());
                }
            });
        }).start();
    }

    //cosas login
    private MutableLiveData<RespuestaLogin> respuestaLogin = new MutableLiveData<>();

    public LiveData<RespuestaLogin> getRespuestaLogin() {
        if (respuestaLogin == null) {
            respuestaLogin = new MutableLiveData<>();
        }
        return respuestaLogin;
    }

    public void crearUsuario(PojoUsuario usuario) {
        new Thread(() -> {
            AuthApi ser = AuthApi.getInstancia();
            Call<PojoUsuario> llamada = ser.getRepo().crearUsuario(usuario);
            llamada.enqueue(new Callback<PojoUsuario>() {
                @Override
                public void onResponse(Call<PojoUsuario> call, Response<PojoUsuario> response) {
                    if (response.isSuccessful()) {
                        successMessage.postValue(true);
                    } else if (response.code() == 500) {
                        successMessage.postValue(false);
                    }
                }

                @Override
                public void onFailure(Call<PojoUsuario> call, Throwable t) {
                    successMessage.postValue(false);
                }
            });
        }).start();
    }

    public void login(String email, String password) {
        new Thread(() -> {
            AuthApi ser = AuthApi.getInstancia();
            Call<RespuestaLogin> call = ser.getRepo().login(email, password);

            call.enqueue(new Callback<RespuestaLogin>() {
                @Override
                public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                    if (response.isSuccessful()) {
                        respuestaLogin.postValue(response.body());
                    } else {
                        respuestaLogin.postValue(new RespuestaLogin(false, null)); // Asume un constructor para manejar fallos
                    }
                }

                @Override
                public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                    respuestaLogin.postValue(new RespuestaLogin(false, null)); // Asume un constructor para manejar fallos
                }
            });
        }).start();
    }

    public LiveData<Boolean> getSuccessMessage() {
        return successMessage;
    }

    public void resetSuccessMessage() {
        successMessage.postValue(null);
    }
}
