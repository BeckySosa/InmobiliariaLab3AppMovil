package com.example.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    MutableLiveData<String> mErrorLog;
    MutableLiveData<String> mExitoLog;
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMErrorLog(){
        if(mErrorLog== null){
            mErrorLog= new MutableLiveData<>();
        }
        return mErrorLog;
    }
    public LiveData<String> getMExitoLog(){
        if(mExitoLog== null){
            mExitoLog= new MutableLiveData<>();
        }
        return mExitoLog;
    }
    public void login(String usuario,String clave){

      ///  if(usuario.equalsIgnoreCase("usuario1") && clave.equalsIgnoreCase("123")){
           //// mExitoLog.setValue("Logueo Exitoso");
       /// }else{
       ///     mErrorLog.setValue("Los datos ingresados no son validos!");
        /// }

        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(usuario,clave);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String token = response.body();////viene  la respuesta correcta
                ApiClient.guardarToken(getApplication(), token);
                mExitoLog.postValue("Bienvenido");

                }else{
                    mErrorLog.postValue("Los datos ingresados no son validos!");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mErrorLog.postValue("Error!");
            }
        });
    }
}
