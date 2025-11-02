package com.example.inmobiliaria.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {
   private InicioViewModel vm;
    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        vm=new ViewModelProvider(this).get(InicioViewModel.class);
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
         vm.getMMapa().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.Mapa>() {
             @Override
             public void onChanged(InicioViewModel.Mapa mapa) {
                 SupportMapFragment mapFrag=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                 mapFrag.getMapAsync(mapa);
             }
         });
         vm.cargarMapa();

          return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}