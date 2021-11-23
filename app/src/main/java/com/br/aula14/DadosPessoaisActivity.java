package com.br.aula14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DadosPessoaisActivity extends AppCompatActivity {

    TextView activity_dados_pessoais_text_nome_recebido,
            activity_dados_pessoais_text_telefone_recebido,
            activity_dados_pessoais_text_email_recebido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);

        activity_dados_pessoais_text_nome_recebido = findViewById(R.id.activity_dados_pessoais_text_nome_recebido);
        activity_dados_pessoais_text_telefone_recebido = findViewById(R.id.activity_dados_pessoais_text_telefone_recebido);
        activity_dados_pessoais_text_email_recebido = findViewById(R.id.activity_dados_pessoais_text_email_recebido);

        Intent intent = getIntent();

        activity_dados_pessoais_text_nome_recebido.setText(intent.getStringExtra("nome"));
        activity_dados_pessoais_text_telefone_recebido.setText(intent.getStringExtra("telefone"));
        activity_dados_pessoais_text_email_recebido.setText(intent.getStringExtra("email"));


    }
}