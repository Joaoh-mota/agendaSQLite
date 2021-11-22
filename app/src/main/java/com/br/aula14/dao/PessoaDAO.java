package com.br.aula14.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.br.aula14.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaDAO extends SQLiteOpenHelper {

    public PessoaDAO(Context context) {
        super(context, "bancoAgenda", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE pessoa(nome TEXT,telefone TEXT,email TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS pessoa;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void cadastrar(Pessoa pessoa) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("nome", pessoa.getNome());
        dados.put("telefone", pessoa.getTelefone());
        dados.put("email", pessoa.getEmail());

        db.insert("pessoa", null, dados);
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
