package com.br.aula14;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.br.aula14.dao.PessoaDAO;
import com.br.aula14.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);

        buscaNoBanco();
    }

    private void buscaNoBanco() {
        PessoaDAO dao = new PessoaDAO(getApplicationContext());
        List<Pessoa> pessoas = dao.buscar();
        List<String> nomes = new ArrayList<String>();

        for (Pessoa nomeBuscado : pessoas){
            nomes.add(nomeBuscado.getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);
    }
}