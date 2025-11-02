package com.example.inmobiliaria.ui.inmueble;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CargarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri>mUri= new MutableLiveData<>();
    private MutableLiveData<String>mMsj= new MutableLiveData<>();
    private MutableLiveData<String>mExito= new MutableLiveData<>();
    public CargarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Uri> getMUri(){
        return mUri;
    }
    public LiveData<String> getMsj(){
        return mMsj;
    }
    public LiveData<String> getExito(){
        return mExito;
    }
   public void recibirFoto(ActivityResult result){
        if(result.getResultCode()== RESULT_OK){
            Intent data = result.getData();
            Uri uri= data.getData();
            Log.d("salada", uri.toString());
            mUri.setValue(uri);///le indica donde esta la imagen
        }
   }
   public void cargarInmueble(String direccion,String valor,String tipo,String uso,String ambientes,String superficie,boolean disponible){
        int superf;
        int amb;
        double pre;
        try{
            superf=Integer.parseInt(superficie);
            amb=Integer.parseInt(ambientes);
            pre=Double.parseDouble(valor);
            if(direccion.isBlank() || tipo.isBlank() || uso.isBlank() || ambientes.isBlank() || superficie.isBlank() || valor.isBlank()){
                mMsj.setValue("Se requiere todos los campos, no deben estar vacios");
                return;
            }
            if(mUri.getValue()==null){
                mMsj.setValue("Debe seleccionar una foto");
                return;
            }
            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setValor(pre);
            inmueble.setTipo(tipo);
            inmueble.setUso(uso);
            inmueble.setAmbientes(amb);
            inmueble.setSuperficie(superf);
            inmueble.setDisponible(disponible);
            byte[] imagen =transformarImagen();
            String inmuebleJson = new Gson().toJson(inmueble);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=uft-8"),inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"),imagen);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen","imagen.jpg",requestFile);
            ApiClient.InmoService inmoService = ApiClient.getApiInmobiliaria();
            String token = ApiClient.leerToken(getApplication());
            Call<Inmueble> llamada = inmoService.agregarInmueble("Bearer "+ token,imagenPart,inmuebleBody);
            llamada.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if(response.isSuccessful()){
                        mExito.setValue("Inmueble cargado Exitosamente");
                    }else{
                        mMsj.setValue("Error al cargar inmueble!");
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    mMsj.setValue("Error al cargar inmueble");
                }
            });
        }catch(NumberFormatException e){
            mMsj.setValue("Error de valores,en superficie,ambientes y precios,deben ser numeros en cargar inmueble");
            return;
        }


   }
   private byte[] transformarImagen(){
        try{
            Uri uri = mUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutPutStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutPutStream);///los comprime y los envia a la memoria
            return byteArrayOutPutStream.toByteArray();
        }catch(FileNotFoundException e){
            mMsj.setValue("No ha seleccionado una foto");
            return new byte[]{};
        }
   }
}