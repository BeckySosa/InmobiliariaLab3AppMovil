package com.example.inmobiliaria.ui.contrato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.model.Pago;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolderPago> {

    private List<Pago> lista;
    private Context context;

    public PagoAdapter(List<Pago> lista, Context context){
        this.lista=lista;
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolderPago onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pagos_card,parent,false);

        return new ViewHolderPago(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPago holder, int position) {

        DateTimeFormatter formatoLocal = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Pago pago= lista.get(position);
        holder.idPago.setText(String.valueOf(pago.getIdPago()));
        holder.fechaP.setText(pago.getFechaPago().format(formatoLocal));
        holder.detalleP.setText(pago.getDetalle());
        holder.monto.setText(String.valueOf(pago.getMonto()));

    }



    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolderPago extends RecyclerView.ViewHolder {
     EditText idPago,fechaP,detalleP,monto;

        public ViewHolderPago(@NonNull View itemView) {
            super(itemView);

            idPago=itemView.findViewById(R.id.etIdPago);
            fechaP=itemView.findViewById(R.id.etFechaP);
            detalleP=itemView.findViewById(R.id.etDetalleP);
            monto=itemView.findViewById(R.id.etMonto);
        }
    }

}
