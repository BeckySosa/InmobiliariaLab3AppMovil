package com.example.inmobiliaria.ui.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordViewModel extends AndroidViewModel {
    private MutableLiveData<String> mError= new MutableLiveData<>();
    private MutableLiveData<String> mExito= new MutableLiveData<>();
    public PasswordViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<String> getMExito(){
        return mExito;
    }
    public void cambiarContra(String actual,String nueva,String confir) {
        if (actual.isBlank() || nueva.isBlank() || confir.isBlank()) {
            mError.setValue("Los campos no pueden estar vacios");
            return;
        }
        if (!nueva.equals(confir)) {
            mError.setValue("Las contraseña nueva y la confirmacion deben ser iguales");
            return;
        }
        String token = ApiClient.leerToken(getApplication());
        Call<Void> llamada = ApiClient.getApiInmobiliaria().cambiarPassword("Bearer " + token, actual, nueva);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mExito.setValue("Cambio de contraseña exitoso");
                }else{
                    mError.setValue("Error al cambiar la contraseña"+response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
              mError.setValue("Error de servidor"+t.getMessage());
            }
        });
    }
}