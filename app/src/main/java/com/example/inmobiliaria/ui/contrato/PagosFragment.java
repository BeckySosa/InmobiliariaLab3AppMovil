package com.example.inmobiliaria.ui.contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentContratoBinding;
import com.example.inmobiliaria.databinding.FragmentPagosBinding;
import com.example.inmobiliaria.model.Pago;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel vm;
    private FragmentPagosBinding binding;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm= new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater,container,false);
        vm.getMPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
               PagoAdapter adapter= new PagoAdapter(pagos,getContext());
                GridLayoutManager glm= new GridLayoutManager(getContext(),1);
                RecyclerView rv= binding.listaPagos;
                rv.setLayoutManager(glm);
                rv.setAdapter(adapter);
            }
        });
        vm.obtenerPagos(getArguments());

        vm.getMMsj().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMsjPago.setText("s");
            }
        });

        return binding.getRoot();
    }



}