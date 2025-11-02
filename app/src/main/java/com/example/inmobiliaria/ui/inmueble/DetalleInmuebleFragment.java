package com.example.inmobiliaria.ui.inmueble;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmobiliaria.databinding.FragmentInmuebleBinding;
import com.example.inmobiliaria.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel vm;
    private FragmentDetalleInmuebleBinding binding;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding= FragmentDetalleInmuebleBinding.inflate(inflater,container,false);
        vm.getMInmueble().observe(getViewLifecycleOwner(),inmueble->{
            binding.tvIdInmueble.setText(inmueble.getIdInmueble()+ "");
            binding.tvDireccionI.setText(inmueble.getDireccion());
            binding.tvUsoI.setText(inmueble.getUso());
            binding.tvAmbientesI.setText(inmueble.getAmbientes()+"");
            binding.tvLatitudI.setText(inmueble.getLatitud()+ "");
            binding.tvLongitudI.setText(inmueble.getLongitud()+"");
            binding.tvValorI.setText(inmueble.getValor()+ "");
            Glide.with(this)
                    .load(ApiClient.URL_BASE + inmueble.getImagen())
                    .placeholder(R.drawable.inmuebl)
                    .error("null")
                    .into(binding.imgInmueble);
            binding.checkDisponible.setChecked(inmueble.isDisponible());
        });
        vm.obtenerInmueble(getArguments());
        binding.checkDisponible.setOnClickListener(v -> {
            vm.actualizarInmueble(binding.checkDisponible.isChecked());
        });

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}