package com.leandro.sysinv.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leandro.sysinv.R;
import com.leandro.sysinv.model.entities.Local;

import java.util.List;

public class AdapterLocais extends RecyclerView.Adapter<AdapterLocais.MyViewHolder> {

    private List<Local> listaLocais;

    public AdapterLocais(List<Local> lista) {
        this.listaLocais = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_locais,  parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {  // Chamado a cada item da Lista

        Local local = listaLocais.get(position);
        holder.codigo.setText(String.valueOf(local.getLocal_id()));
        holder.descricao.setText(local.getDescricao());

        Log.i("Passou", "Bindou");

    }

    @Override
    public int getItemCount() {
        return listaLocais.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView codigo;
        TextView descricao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            codigo = itemView.findViewById(R.id.textCodigo);
            descricao = itemView.findViewById(R.id.textDescricao);

        }
    }

}
