package com.example.inmobiliaria.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.model.Propietario;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> mBoolean=new MutableLiveData<>();
    private MutableLiveData<Propietario> mProp=new MutableLiveData<>();
    private MutableLiveData<String> mCambiar=new MutableLiveData<>();
    public PerfilViewModel(@NonNull Application application) {

        super(application);
    }
    public LiveData<Propietario> getMProp(){

        return mProp;
    }
    public LiveData<Boolean> getMBoolean(){

        return mBoolean;
    }
    public LiveData<String> getMCambiar(){

        return mCambiar;
    }
    public void guardar(String btn,String nomb,String ape,String dni, String tel,String email){
        if(btn.equalsIgnoreCase("Editar")){
         mBoolean.setValue(true);
         mCambiar.setValue("Guardar");
        }else{
            /// /validar campos si no estan vacios y q dni sea num
        if(nomb.isBlank()||ape.isBlank()||dni.isBlank()||tel.isBlank()||email.isBlank()){
            Toast.makeText(getApplication(),"No puede haber campos vacios",Toast.LENGTH_LONG).show();
        }
        int t;
        int d;
            try {

                t=Integer.parseInt(tel);
                d=Integer.parseInt(dni);

            } catch (NumberFormatException e) {
                Toast.makeText(getApplication(),"Los campos dni o telefono deber ser numeros",Toast.LENGTH_LONG).show();
                Log.d("error ","Telefono o dni debe ser un numero"+e);

            }

         Propietario p =new Propietario();
         p.setIdPropietario(mProp.getValue().getIdPropietario());
         p.setNombre(nomb);
         p.setApellido(ape);
         p.setDni(dni);
         p.setTelefono(tel);
         p.setEmail(email);
         p.setClave(null);

         mCambiar.setValue("Editar");
         mBoolean.setValue(false);
         String token= ApiClient.leerToken(getApplication());
         Call<Propietario>llamada=ApiClient.getApiInmobiliaria().actualizarPropietario("Bearer "+token,p);
         llamada.enqueue(new Callback<Propietario>() {
             @Override
             public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                 if(response.isSuccessful()){
                     Toast.makeText(getApplication(),"Propietario Actualizado",Toast.LENGTH_LONG);
                 }
                 else{
                     Toast.makeText(getApplication(),"Error al actualizar el propietario",Toast.LENGTH_LONG).show();
                 }
             }

             @Override
             public void onFailure(Call<Propietario> call, Throwable t) {
                 Toast.makeText(getApplication(),"Error de servidor",Toast.LENGTH_LONG).show();
             }
         });
        }
    }

    public void leerPropietario(){
        String token= ApiClient.leerToken(getApplication());
        Call<Propietario> llamada=ApiClient.getApiInmobiliaria().obtenerPropietario("Bearer "+token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            /// cuando lanza la respuesta
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    mProp.postValue(response.body());
                }
                else{
                    Toast.makeText(getApplication(),"No se pudo obtener propietario",Toast.LENGTH_LONG).show();
                    Log.d("error",response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(),"Error servidor",Toast.LENGTH_LONG).show();
                Log.d("error",t.getMessage());
            }
        });


    }



}

