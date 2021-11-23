package com.br.aula14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.br.aula14.adapter.RecyclerViewAdapter;
import com.br.aula14.dao.PessoaDAO;
import com.br.aula14.entity.Pessoa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    Context context;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        context = getApplicationContext();

        recyclerView = findViewById(R.id.recyclerView);
        buscaNoBanco();
        voltaActivity();

    }

    private void buscaNoBanco() {
        PessoaDAO dao = new PessoaDAO(getApplicationContext());
        List<Pessoa> pessoas = dao.buscar();

        List<String> nomes = new ArrayList<>();
        List<String> telefones = new ArrayList<>();
        List<String> emails = new ArrayList<>();

        String[] dados_nomes = new String[]{};
        String[] dados_telefones = new String[]{};
        String[] dados_emails = new String[]{};


        for (Pessoa pessoaBuscada : pessoas){
            nomes.add(pessoaBuscada.getNome());
            telefones.add(pessoaBuscada.getTelefone());
            emails.add(pessoaBuscada.getEmail());
        }

        dados_nomes = nomes.toArray(new String[0]);
        dados_telefones = telefones.toArray(new String[0]);
        dados_emails = emails.toArray(new String[0]);

        recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(context,dados_nomes,dados_telefones, dados_emails);

        recyclerView.setAdapter(recyclerViewAdapter);


    }

    @Override
    public void onResume(){
        super.onResume();
        buscaNoBanco();
    }

    private void voltaActivity() {
        FloatingActionButton fab = findViewById(R.id.fabVoltar);
        Intent intent = new Intent(this, ListActivity.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}