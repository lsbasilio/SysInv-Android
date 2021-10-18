package com.leandro.sysinv.adapter;

import android.content.Context;
import android.os.Environment;
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

import static android.provider.Settings.Global.getString;

public class AdapterLocais extends RecyclerView.Adapter<AdapterLocais.MyViewHolder> {

    private List<Local> listaLocais;
    private Context context;

    public AdapterLocais(List<Local> lista, Context context) {
        this.listaLocais = lista;
        this.context = context;
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
        holder.totalBens.setText( this.context.getString(R.string.total_bens_local, local.getTotalBens()) );
        //holder.totalBens.setText( "Total de Bens: " + local.getTotalBens());

        //Log.i("Passou", "Bindou");
    }

    @Override
    public int getItemCount() {
        return listaLocais.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView codigo;
        TextView descricao;
        TextView totalBens;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            codigo = itemView.findViewById(R.id.textCodigo);
            descricao = itemView.findViewById(R.id.textDescricao);
            totalBens = itemView.findViewById(R.id.textTotalBens);

        }
    }

    public void setListaLocais(List<Local> listaLocais) {
        this.listaLocais = listaLocais;
    }
}
