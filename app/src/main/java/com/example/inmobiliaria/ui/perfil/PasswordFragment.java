package com.example.inmobiliaria.ui.perfil;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentPasswordBinding;

public class PasswordFragment extends Fragment {

    private PasswordViewModel vm;
    private FragmentPasswordBinding binding;

    public static PasswordFragment newInstance() {
        return new PasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PasswordViewModel.class);///nose como :P
        binding = FragmentPasswordBinding.inflate(inflater,container,false);
        View root= binding.getRoot();

        vm.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
             binding.tvMensaje.setText(s);
            }
        });
        vm.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                /// si es exitoso mandar al login o al perfil o donde sino un toast
               Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_perfil);
            }
        });
        binding.btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String act= binding.etActual.getText().toString();
                String nuev= binding.etNueva.getText().toString();
                String confi= binding.etConfirm.getText().toString();
                vm.cambiarContra(act,nuev,confi);
            }

        });
      return root;
    }

 @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
 }

}