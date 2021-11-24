package com.br.aula14;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.br.aula14.dao.PessoaDAO;
import com.br.aula14.entity.Pessoa;

public class DadosPessoaisActivity extends AppCompatActivity {

    EditText activity_dados_pessoais_editText_nome_recebido,
            activity_dados_pessoais_editText_telefone_recebido,
            activity_dados_pessoais_editText_email_recebido;

    Button activity_dados_pessoais_btn_atualizar,
            activity_dados_pessoais_btn_deletar,
            activity_dados_pessoais_btn_voltar;

    String activity_dados_pessoais_nome_recebido;
    final String[] ultimoCaractereDigitado = {""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);


        //Widgets primeiro layout (editTexts)
        activity_dados_pessoais_editText_nome_recebido = findViewById(R.id.activity_dados_pessoais_editText_nome_recebido);
        activity_dados_pessoais_editText_telefone_recebido = findViewById(R.id.activity_dados_pessoais_editText_telefone_recebido);
        activity_dados_pessoais_editText_email_recebido = findViewById(R.id.activity_dados_pessoais_editText_email_recebido);

        //Widgets segundo layout (botoes)
        activity_dados_pessoais_btn_atualizar = findViewById(R.id.activity_dados_pessoais_btn_atualizar);
        activity_dados_pessoais_btn_deletar = findViewById(R.id.activity_dados_pessoais_btn_deletar);
        activity_dados_pessoais_btn_voltar = findViewById(R.id.activity_dados_pessoais_btn_voltar);

        Intent intent = getIntent();

        //Identificador para saber quem está sendo atualizado
        activity_dados_pessoais_nome_recebido = intent.getStringExtra("nome");

        activity_dados_pessoais_editText_nome_recebido.setText(intent.getStringExtra("nome"));

        activity_dados_pessoais_editText_telefone_recebido.setText(intent.getStringExtra("telefone"));

        //Mascarar telefone
        activity_dados_pessoais_editText_telefone_recebido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Integer tamanhoEditTelefone = activity_dados_pessoais_editText_telefone_recebido.getText().toString().length();
                if (tamanhoEditTelefone > 1) {
                    ultimoCaractereDigitado[0] = activity_dados_pessoais_editText_telefone_recebido.getText().toString().substring(tamanhoEditTelefone - 1);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer tamanhoEditTelefone = activity_dados_pessoais_editText_telefone_recebido.getText().toString().length();
                if (tamanhoEditTelefone == 2) {
                    if (!ultimoCaractereDigitado[0].equals(" ")) {
                        activity_dados_pessoais_editText_telefone_recebido.append(" ");
                    } else {
                        activity_dados_pessoais_editText_telefone_recebido.getText().delete(tamanhoEditTelefone - 1, tamanhoEditTelefone);
                    }
                } else if (tamanhoEditTelefone == 8) {
                    if (!ultimoCaractereDigitado[0].equals("-")) {
                        activity_dados_pessoais_editText_telefone_recebido.append("-");
                    } else {
                        activity_dados_pessoais_editText_telefone_recebido.getText().delete(tamanhoEditTelefone - 1, tamanhoEditTelefone);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activity_dados_pessoais_editText_email_recebido.setText(intent.getStringExtra("email"));

        //Funções dos botões


        //Voltar
        activity_dados_pessoais_btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Deletar
        activity_dados_pessoais_btn_deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder confirmarExclusao = new AlertDialog.Builder(DadosPessoaisActivity.this);
                confirmarExclusao.setTitle("ATENÇÃO !");
                confirmarExclusao.setMessage("Tem certeza que deseja excluir " + activity_dados_pessoais_nome_recebido + " ?");

                confirmarExclusao.setCancelable(false);
                confirmarExclusao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apagaPessoa();
                    }
                });
                confirmarExclusao.setNegativeButton("Não", null);
                confirmarExclusao.create().show();

            }
        });

        //Atualizar
        activity_dados_pessoais_btn_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaPessoa();
            }
        });




    }



    private void apagaPessoa() {
        PessoaDAO dao = new PessoaDAO(getApplicationContext());
        dao.deletar(activity_dados_pessoais_nome_recebido);
        finish();
    }

    private void atualizaPessoa() {
        PessoaDAO dao = new PessoaDAO(getApplicationContext());
        Pessoa pessoaParaAtualizar = new Pessoa();

        pessoaParaAtualizar.setNome(activity_dados_pessoais_editText_nome_recebido.getText().toString());
        pessoaParaAtualizar.setTelefone(activity_dados_pessoais_editText_telefone_recebido.getText().toString());
        pessoaParaAtualizar.setEmail(activity_dados_pessoais_editText_email_recebido.getText().toString());

        dao.cadastrar(pessoaParaAtualizar, activity_dados_pessoais_nome_recebido);
        dao.close();
        finish();
    }
}