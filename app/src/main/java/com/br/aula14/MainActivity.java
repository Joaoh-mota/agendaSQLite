package com.br.aula14;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.br.aula14.dao.PessoaDAO;
import com.br.aula14.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextNome;
    EditText editTextTelefone;
    EditText editTextEmail;
    Button btnSalvar;
    ListView listView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnSalvar = findViewById(R.id.btnSalvar);
        listView = findViewById(R.id.listView);

        buscaNoBanco();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(editTextNome.getText().toString().equals("") || editTextTelefone.getText().toString().equals("") || editTextEmail.getText().toString().equals(""))) {

                    PessoaDAO dao = new PessoaDAO(getApplicationContext());
                    Pessoa pessoa = new Pessoa();

                    pessoa.setNome(editTextNome.getText().toString());
                    pessoa.setTelefone(editTextTelefone.getText().toString());
                    pessoa.setEmail(editTextEmail.getText().toString());

                    dao.cadastrar(pessoa);
                    dao.close();

                    limpaFormulario();

                    buscaNoBanco();

                } else {

                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                }

            }
        });


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

    private void limpaFormulario() {
        editTextNome.setText("");
        editTextNome.requestFocus();
        editTextTelefone.setText("");
        editTextEmail.setText("");
    }
}