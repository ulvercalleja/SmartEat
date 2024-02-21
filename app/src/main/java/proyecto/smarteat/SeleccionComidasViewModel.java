package proyecto.smarteat;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import proyecto.smarteat.ApiAlimentos;
import proyecto.smarteat.PojoAlimentos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeleccionComidasViewModel extends ViewModel {
    private MutableLiveData<List<PojoAlimentos>> listaComidasLiveData;

    public LiveData<List<PojoAlimentos>> getListaComidas() {
        if (listaComidasLiveData == null) {
            listaComidasLiveData = new MutableLiveData<>();
            cargarListaComidas();
        }
        return listaComidasLiveData;
    }

    private void cargarListaComidas() {
        ApiAlimentos.getInstancia().getRepo().getAlimentos().enqueue(new Callback<List<PojoAlimentos>>() {
            @Override
            public void onResponse(Call<List<PojoAlimentos>> call, Response<List<PojoAlimentos>> response) {
                if (response.isSuccessful()) {
                    listaComidasLiveData.setValue(response.body());
                } else {
                    Log.e("API Error", "Error en la respuesta de la API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PojoAlimentos>> call, Throwable t) {
            }
        });
    }
}

