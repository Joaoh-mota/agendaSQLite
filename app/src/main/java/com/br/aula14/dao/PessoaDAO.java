package com.br.aula14.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.br.aula14.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaDAO extends SQLiteOpenHelper {

    public PessoaDAO(Context context) {
        super(context, "bancoAgenda", null, 3);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE pessoa(nome TEXT UNIQUE,telefone TEXT,email TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS pessoa;";
        db.execSQL(sql);
        onCreate(db);
    }

    //Cadastro ou atualização de dados
    public void cadastrar(Pessoa pessoa, String pessoaParaAtualizar) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("telefone", pessoa.getTelefone());
        dados.put("email", pessoa.getEmail());

        //Se foi chamado a partir do botão de atualizar, ele irá utilizar o nome estático como chave
        if (pessoaParaAtualizar != null){
            dados.put("nome", pessoaParaAtualizar);
        }else{
            dados.put("nome", pessoa.getNome());
        }


        //Verificação se é atualização ou inserção
        try{
            db.insertOrThrow("pessoa", null, dados);
        }catch (SQLiteConstraintException e){
            dados.put("nome", pessoa.getNome());
            db.update("pessoa", dados,"nome = ?", new String[]{pessoaParaAtualizar});
        }

    }

    public List<Pessoa> buscar() {

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM pessoa;";

        Cursor c = db.rawQuery(sql, null);

        List<Pessoa> pessoas = new ArrayList<Pessoa>();

        while (c.moveToNext()) {
            Pessoa pessoa = new Pessoa();

            pessoa.setNome(c.getString(c.getColumnIndex("nome")));
            pessoa.setTelefone(c.getString(c.getColumnIndex("telefone")));
            pessoa.setEmail(c.getString(c.getColumnIndex("email")));

            pessoas.add(pessoa);
        }
        return pessoas;
    }

    public void deletar(String nome) {

        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM pessoa WHERE nome = " + "'" + nome + "'";
        db.execSQL(sql);

    }


}
