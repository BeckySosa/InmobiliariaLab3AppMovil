package com.example.inmobiliaria.ui.contrato;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.model.Contrato;
import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.model.Inquilino;
import com.example.inmobiliaria.model.Pago;
import com.example.inmobiliaria.request.ApiClient;

import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato>mContrato= new MutableLiveData<>();
    private MutableLiveData<Inquilino>mInquilino= new MutableLiveData<>();
    private MutableLiveData<Integer>mIdContrato= new MutableLiveData<>();
    private MutableLiveData<String>mFechaF= new MutableLiveData<>();
    private MutableLiveData<String>mFechaI= new MutableLiveData<>();

    public ContratoViewModel(@NonNull Application application) {
        super(application);
    }


    LiveData<Inquilino> getMInquilino(){
        return mInquilino;
    }
    LiveData<Contrato> getMContrato(){
        return mContrato;
    }
    LiveData<Integer> getMIdContrato(){
        return mIdContrato;
    }
    LiveData<String> getMFechaF(){
        return mFechaF;
    }
    LiveData<String> getMFechaI(){
        return mFechaI;
    }
public void obtenerContrato(Bundle bundle) {

    int idInmueble = bundle.getInt("id");
    String token = ApiClient.leerToken(getApplication());
    ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
    Call<Contrato> llamada = api.getContratoInquilino("Bearer " + token, idInmueble);
    llamada.enqueue(new Callback<Contrato>() {
        @Override
        public void onResponse(Call<Contrato> call, Response<Contrato> response) {
            if (response.isSuccessful()) {

                DateTimeFormatter formatoVista = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                mFechaF.postValue(response.body().getFechaFinalizacion().format(formatoVista));
                mFechaI.postValue(response.body().getFechaInicio().format(formatoVista));
                mContrato.postValue(response.body());
                mInquilino.postValue(response.body().getInquilino());
                mIdContrato.postValue(response.body().getIdContrato());
            } else {
                Toast.makeText(getApplication(), "No hay contrato disponible" + response.message(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Contrato> call, Throwable t) {
            Toast.makeText(getApplication(), "Error en servidor " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}

}
