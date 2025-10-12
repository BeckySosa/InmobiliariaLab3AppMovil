package com.example.inmobiliaria.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LogoutViewModel extends AndroidViewModel {
    public LogoutViewModel(@NonNull Application application) {
        super(application);
    }


    public void logout(){
        SharedPreferences sp = getApplication().getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.remove("token");
        editor.apply();

    }
}
