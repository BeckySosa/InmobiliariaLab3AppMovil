package com.example.inmobiliaria.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {
 private MutableLiveData<Mapa> mMapa= new MutableLiveData<>();
  private MutableLiveData<String> mMensaje= new MutableLiveData<>();


    public InicioViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Mapa> getMMapa(){
        return mMapa;
    }

    public void cargarMapa(){
     Mapa mapa =new Mapa();
     mMapa.setValue(mapa);
    }
  public class Mapa implements OnMapReadyCallback {
      LatLng ubi =new LatLng(-33.30249128,-66.34624298);
      @Override
      public void onMapReady(@NonNull GoogleMap googleMap) {
       MarkerOptions marcador = new MarkerOptions();
       marcador.position(ubi);
       marcador.title("Inmobiliaria RS");

       googleMap.addMarker(marcador);
       googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
       CameraPosition camaraPos= new CameraPosition
               .Builder()
               .target(ubi)
               .zoom(30)
               .bearing(45)
               .tilt(15)
               .build();
       CameraUpdate cameraUpd = CameraUpdateFactory.newCameraPosition(camaraPos);

       googleMap.animateCamera(cameraUpd);


      }
  }

}