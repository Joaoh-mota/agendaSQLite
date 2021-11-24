package com.br.aula14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.aula14.dao.PessoaDAO;
import com.br.aula14.entity.Pessoa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    EditText editTextNome;
    EditText editTextTelefone;
    EditText editTextEmail;
    Button btnSalvar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnSalvar = findViewById(R.id.btnSalvar);


        //Mascarando telefone
        final String[] ultimoCaractereDigitado = {""};

        editTextTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Integer tamanhoEditTelefone = editTextTelefone.getText().toString().length();
                if (tamanhoEditTelefone > 1) {
                    ultimoCaractereDigitado[0] = editTextTelefone.getText().toString().substring(tamanhoEditTelefone - 1);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer tamanhoEditTelefone = editTextTelefone.getText().toString().length();
                if (tamanhoEditTelefone == 2) {
                    if (!ultimoCaractereDigitado[0].equals(" ")) {
                        editTextTelefone.append(" ");
                    } else {
                        editTextTelefone.getText().delete(tamanhoEditTelefone - 1, tamanhoEditTelefone);
                    }
                } else if (tamanhoEditTelefone == 8) {
                    if (!ultimoCaractereDigitado[0].equals("-")) {
                        editTextTelefone.append("-");
                    } else {
                        editTextTelefone.getText().delete(tamanhoEditTelefone - 1, tamanhoEditTelefone);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Adiciona pessoa
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(editTextNome.getText().toString().equals("") || editTextTelefone.getText().toString().equals("") || editTextEmail.getText().toString().equals(""))) {

                    inserePessoa();
                    limpaFormulario();

                    Toast.makeText(getApplicationContext(), "Pessoa cadastrada com sucesso!", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        exibirDados();

    }

    private void inserePessoa() {
        PessoaDAO dao = new PessoaDAO(getApplicationContext());
        Pessoa pessoa = new Pessoa();

        pessoa.setNome(editTextNome.getText().toString());
        pessoa.setTelefone(editTextTelefone.getText().toString());
        pessoa.setEmail(editTextEmail.getText().toString());

        dao.cadastrar(pessoa, null);
        dao.close();
    }

    private void exibirDados() {
        FloatingActionButton fab = findViewById(R.id.fabListar);
        Intent intent = new Intent(this, ListActivity.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    private void limpaFormulario() {
        editTextNome.setText("");
        editTextNome.requestFocus();
        editTextTelefone.setText("");
        editTextEmail.setText("");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}