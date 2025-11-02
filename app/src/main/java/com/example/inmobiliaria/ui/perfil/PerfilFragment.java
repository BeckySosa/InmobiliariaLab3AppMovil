package com.example.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentPerfilBinding;
import com.example.inmobiliaria.model.Propietario;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       vm =new ViewModelProvider(this).get(PerfilViewModel.class);
       binding = FragmentPerfilBinding.inflate(inflater, container, false);
       vm.getMProp().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
           @Override
           public void onChanged(Propietario propietario) {
               binding.etNombre.setText(propietario.getNombre());
               binding.etApellido.setText(propietario.getApellido());
               binding.etDni.setText(propietario.getDni());
               binding.etTelefono.setText(propietario.getTelefono());
               binding.etEmail.setText(propietario.getEmail());
           }
       });

       vm.getMBoolean().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
           @Override
           public void onChanged(Boolean aBoolean) {
               binding.etNombre.setEnabled(aBoolean);
               binding.etApellido.setEnabled(aBoolean);
               binding.etDni.setEnabled(aBoolean);
               binding.etTelefono.setEnabled(aBoolean);
               binding.etEmail.setEnabled(aBoolean);
           }
       });

       vm.getMCambiar().observe(getViewLifecycleOwner(), new Observer<String>() {
           @Override
           public void onChanged(String s) {
               binding.etNombre.setText(s);
               binding.etApellido.setText(s);
               binding.etDni.setText(s);
               binding.etTelefono.setText(s);
               binding.etEmail.setText(s);
           }
       });
       vm.leerPropietario();
       binding.btnPerfil.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String nomb= binding.etNombre.getText().toString();
               String ape= binding.etApellido.getText().toString();
               String dni= binding.etDni.getText().toString();
               String tel=binding.etTelefono.getText().toString();
               String email= binding.etEmail.getText().toString();
               vm.guardar(binding.btnPerfil.getText().toString(),nomb,ape,dni,tel,email);
           }
       });
       binding.btnCambiarContra.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.passwordFragment);
           }
       });




        return binding.getRoot();

    }



}