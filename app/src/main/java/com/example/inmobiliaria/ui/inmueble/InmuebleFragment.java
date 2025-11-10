package com.example.inmobiliaria.ui.inmueble;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentInmuebleBinding;
import com.example.inmobiliaria.model.Inmueble;

import java.util.List;

public class InmuebleFragment extends Fragment {
    private InmuebleViewModel vm;
    private FragmentInmuebleBinding binding;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm=new ViewModelProvider(this).get(InmuebleViewModel.class);

        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        vm.getMInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdapter adapter = new InmuebleAdapter(inmuebles,getContext());
                GridLayoutManager glm =new GridLayoutManager(getContext(),2);
                RecyclerView rv = binding.rvListaInmuebles;
                rv.setLayoutManager(glm);
                rv.setAdapter(adapter);

            }
        });
        vm.leerInmuebles(false);
        binding.ckbVerAlqui.setOnClickListener(view ->{
            vm.leerInmuebles(binding.ckbVerAlqui.isChecked());
        });

        binding.btnFAgregarIn.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_nav_inmueble_to_cargarInmuebleFragment);
        });




        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}