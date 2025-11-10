package com.example.inmobiliaria.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble= new MutableLiveData<>();
    private MutableLiveData<Boolean> mNavegar= new MutableLiveData<>();
    private MutableLiveData<Integer> idInmu= new MutableLiveData<>();
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    LiveData<Inmueble> getMInmueble(){
        return mInmueble;
    }
    LiveData<Boolean> getMNavegar(){
        return mNavegar;
    }
    LiveData<Integer> getIdInmu(){

        return idInmu;
    }

    public void obtenerInmueble(Bundle inmuebleBundle){
        Inmueble inmu = (Inmueble) inmuebleBundle.getSerializable("inmueble");
        if(inmu != null){
            if(inmu.isTieneContratoVigente()){
                mNavegar.setValue(true);
                idInmu.setValue(inmu.getIdInmueble());
            }
            this.mInmueble.setValue(inmu);
        }
    }

    public void actualizarInmueble(Boolean disponible){
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(disponible);
        inmueble.setIdInmueble(this.mInmueble.getValue().getIdInmueble());

        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> llamada =ApiClient.getApiInmobiliaria().actualizarInmueble("Bearer "+ token, inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplication(),"Inmueble actualizado correctamente",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplication(),"Error al actualizar el inmueble",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(),"Error al contactar servidor"+ t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}