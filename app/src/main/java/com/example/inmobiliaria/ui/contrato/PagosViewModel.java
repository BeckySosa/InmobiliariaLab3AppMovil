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
import com.example.inmobiliaria.model.Inquilino;
import com.example.inmobiliaria.model.Pago;
import com.example.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> mPagos = new MutableLiveData<>();
    private MutableLiveData<String> mMsj=new MutableLiveData<>();
    public PagosViewModel(@NonNull Application application) {
        super(application);
    }
    LiveData<List<Pago>> getMPagos(){
        return mPagos;
    }
    LiveData<String> getMMsj(){
        return mMsj;
    }
    public void obtenerPagos(Bundle bundle){
    int idContrato = bundle.getInt("idContrato");
    String token = ApiClient.leerToken(getApplication());
    ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
    Call<List<Pago>> llamada = api.getPagos("Bearer " + token,idContrato);
    llamada.enqueue(new Callback<List<Pago>>() {
        @Override
        public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
            if (response.isSuccessful()) {
                mPagos.postValue(response.body());

            } else {
                Toast.makeText(getApplication(), "No hay pagos disponibles" + response.message(), Toast.LENGTH_SHORT).show();
                mMsj.setValue("No hay pagos para mostrar");
            }
        }

        @Override
        public void onFailure(Call<List<Pago>> call, Throwable t) {
            Toast.makeText(getApplication(), "Error en servidor " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
    }
}