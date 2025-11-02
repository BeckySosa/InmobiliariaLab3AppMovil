package com.example.inmobiliaria.ui.inmueble;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentCargarInmuebleBinding;

public class CargarInmuebleFragment extends Fragment {

    private CargarInmuebleViewModel vm;
    private FragmentCargarInmuebleBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;
    public static CargarInmuebleFragment newInstance() {
        return new CargarInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm= new ViewModelProvider(this).get(CargarInmuebleViewModel.class);
        binding= FragmentCargarInmuebleBinding.inflate(inflater,container,false);
        abrirGaleria();
        binding.btnAgFoto.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                arl.launch(intent);///se inicia la activity es como el start activity
            }
        });
        vm.getMUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.imgInmu.setImageURI(uri);///reacciona a la uri Y muestra la imagen
            }
        });
        binding.btnAgregarInmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.cargarInmueble(
                        binding.etDireccion.getText().toString(),
                        binding.etPrecio.getText().toString(),
                        binding.etTipo.getText().toString(),
                        binding.etUso.getText().toString(),
                        binding.etAmbiente.getText().toString(),
                        binding.etSuperficie.getText().toString(),
                        binding.ckDisponible.isChecked()

                );
            }
        });
        vm.getMsj().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMsj.setText(s);
            }
        });

        vm.getExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.nav_inmueble);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    private void abrirGaleria(){
     intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);///abre la galeria
     arl= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
         @Override
         public void onActivityResult(ActivityResult result) {
             Log.d("agregarInmuebleFragment","Result:" + result);///nos dice la uri(laubicaciondelrecursodentrodeldispositivo)
          vm.recibirFoto(result);
         }
     });
    }


}