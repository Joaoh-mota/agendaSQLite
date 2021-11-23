package com.br.aula14.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.aula14.DadosPessoaisActivity;
import com.br.aula14.R;
import com.br.aula14.dao.PessoaDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<String> nomes = new ArrayList<String>();
    String[] telefones;
    String[] emails;
    View viewOnCreate;
    ViewHolder viewHolderLocal;

    public RecyclerViewAdapter(Context contextRecebido, String[] nomesRecebidos, String[] telefonesRecebidos, String[] emailsRecebidos) {
        context = contextRecebido;
        nomes.addAll(Arrays.asList(nomesRecebidos));
        telefones = telefonesRecebidos;
        emails = emailsRecebidos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewNome;
        public TextView textViewTelefone;
        public TextView textViewEmail;
        public ImageView imageViewicone;

        public ViewHolder(View itemView) {
            super(itemView);


            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewTelefone = itemView.findViewById(R.id.textViewTelefone);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        @Override
        public void onClick(View v) {

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
        holder.textViewEmail.setText(emails[position]);

        viewOnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DadosPessoaisActivity.class);
                intent.putExtra("nome", nomes.get(position));
                intent.putExtra("telefone", telefones[position]);
                intent.putExtra("email", emails[position]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return nomes.size();
    }
}
