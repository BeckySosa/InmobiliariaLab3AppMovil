package com.example.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClient {

    private static final String URL_BASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    public static InmoService getApiInmobiliaria(){
        Gson gson = new GsonBuilder()
                .setLenient()
                 .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();


      Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(URL_BASE)
  ///// .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(InmoService.class);


    }
    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
        /// /se usa apply xqe es un metodo async sino seria commit
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    public interface InmoService{
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String>login(@Field("Usuario")String u, @Field("Clave") String c);
    }
}


