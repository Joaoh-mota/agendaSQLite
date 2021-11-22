package com.br.aula14.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.aula14.R;
import com.br.aula14.dao.PessoaDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<String> nomes = new ArrayList<String>();
    String[] telefones;
    View viewOnCreate;
    ViewHolder viewHolderLocal;

    public RecyclerViewAdapter(Context contextRecebido, String[] nomesRecebidos, String[] telefonesRecebidos) {
        context = contextRecebido;
        nomes.addAll(Arrays.asList(nomesRecebidos));
        telefones = telefonesRecebidos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNome;
        public TextView textViewTelefone;
        public ImageView imageViewicone;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewTelefone = itemView.findViewById(R.id.textViewTelefone);
            imageViewicone = itemView.findViewById(R.id.imageViewicone);

        }

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        viewOnCreate = LayoutInflater.from(context).inflate(R.layout.recyclerview_itens, parent, false);
        viewHolderLocal = new ViewHolder(viewOnCreate);

        return viewHolderLocal;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.textViewNome.setText(nomes.get(position));
        holder.textViewTelefone.setText(telefones[position]);

        holder.imageViewicone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PessoaDAO dao = new PessoaDAO(context);
                dao.deletar(nomes.get(position));


                nomes.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, nomes.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return nomes.size();
    }
}
