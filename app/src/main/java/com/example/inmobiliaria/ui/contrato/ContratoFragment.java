package com.example.inmobiliaria.ui.contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentContratoBinding;
import com.example.inmobiliaria.model.Contrato;
import com.example.inmobiliaria.model.Inquilino;
import com.example.inmobiliaria.model.Pago;

public class ContratoFragment extends Fragment {

    private ContratoViewModel vm;
    private FragmentContratoBinding binding;

    public static ContratoFragment newInstance() {
        return new ContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm= new ViewModelProvider(this).get(ContratoViewModel.class);
        binding= FragmentContratoBinding.inflate(inflater,container,false);


       vm.getMContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
           @Override
           public void onChanged(Contrato contrato) {

              binding.tvMontoAlqui.setText(contrato.getMontoAlquiler()+"");
           }

        });
       vm.getMFechaI().observe(getViewLifecycleOwner(), new Observer<String>() {
           @Override
           public void onChanged(String s) {
               binding.tvFechaInicio.setText(s);
           }
       });
       vm.getMFechaF().observe(getViewLifecycleOwner(), new Observer<String>() {
           @Override
           public void onChanged(String s) {
               binding.tvFechaFin.setText(s);
           }
       });
        vm.obtenerContrato(getArguments());




       vm.getMInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
           @Override
           public void onChanged(Inquilino inquilino) {
              Bundle bundle = new Bundle();
               bundle.putSerializable("inqui", inquilino);
               Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_nav_contrato_to_inquilinoFragment,bundle);
           }
       });
       binding.btnInquilino.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               vm.obtenerContrato(getArguments());

           }
       });

       vm.getMIdContrato().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                   @Override
                   public void onChanged(Integer integer) {
                       Bundle bundle = new Bundle();
                       bundle.putSerializable("idContrato",integer);
                       Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_nav_contrato_to_pagosFragment,bundle);
                   }
               });
               binding.btnPagos.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       vm.obtenerContrato(getArguments());
                   }
               });



        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm=new ViewModelProvider(this).get(ContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}