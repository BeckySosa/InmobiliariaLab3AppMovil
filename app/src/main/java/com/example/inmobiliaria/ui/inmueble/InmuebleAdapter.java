package com.example.inmobiliaria.ui.inmueble;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.request.ApiClient;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder>{
  private List<Inmueble> lista;
  private Context context;

    public InmuebleAdapter(List<Inmueble> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public InmuebleAdapter.InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.inmueble_card, parent, false);

        return new InmuebleViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.InmuebleViewHolder holder, int position) {
        String urlBase ="https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    Inmueble i =lista.get(position);
    holder.tvDireccion.setText(i.getDireccion());
    holder.tvTipo.setText(i.getTipo());
    holder.tvPrecio.setText(String.valueOf(i.getValor()));
    Glide.with(context)
            .load(ApiClient.URL_BASE + i.getImagen())
            .placeholder(R.drawable.inmuebl)
            .error("null")
            .into(holder.imgInmueble);
            holder.cardView.setOnClickListener(v ->{
                Bundle bundle =new Bundle();
                bundle.putSerializable("inmueble",i);
                Navigation.findNavController((Activity)v.getContext(),R.id.nav_host_fragment_content_main).navigate(R.id.detalleInmuebleFragment,bundle);
                    });


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    public class InmuebleViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccion, tvTipo, tvPrecio;
        private ImageView imgInmueble;
        private CardView cardView;
        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            imgInmueble = itemView.findViewById(R.id.imgInmueble);
            cardView = itemView.findViewById(R.id.idCard);

        }
    }
}
