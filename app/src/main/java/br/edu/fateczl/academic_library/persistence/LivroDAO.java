package br.edu.fateczl.academic_library.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.academic_library.model.Livro;
import br.edu.fateczl.academic_library.persistence.interfaces.IConnectionDAO;
import br.edu.fateczl.academic_library.persistence.interfaces.ICrudDAO;

public class LivroDAO implements IConnectionDAO<LivroDAO>, ICrudDAO<Livro> {

    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;

    public LivroDAO(Context context) { this.context = context; }

    @Override
    public LivroDAO open() throws SQLException {
        gDAO = new GenericDAO(context);
        db = gDAO.getWritableDatabase();
        db.execSQL("PRAGMA FOREIGN_KEYS=ON;");
        return this;
    }

    @Override
    public void close() {
        gDAO.close();
    }

    @Override
    public void insert(Livro livro) throws SQLException {
        db.insert("Exemplar", null, exemplarValues(livro));
        db.insert("Livro", null, livroValues(livro));
    }

    @Override
    public int update(Livro livro) throws SQLException {
        db.update("Exemplar", exemplarValues(livro), "codigo = " + livro.getCodigo(), null);
        db.update("Livro", livroValues(livro), "codigo = " + livro.getCodigo(), null);
        return 0;
    }

    @Override
    public void delete(Livro livro) throws SQLException {
        db.delete("Livro", "codigo = " + livro.getCodigo(), null);
        db.delete("Exemplar", "codigo = " + livro.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Livro findOne(Livro livro) throws SQLException {
        String query = "SELECT e.*, l.isbn, l.edicao FROM Livro l INNER JOIN Exemplar e ON l.codigo = e.codigo " +
                       "WHERE e.codigo = " + livro.getCodigo();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            livro.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            livro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            livro.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            livro.setISBN(cursor.getString(cursor.getColumnIndex("isbn")));
            livro.setEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));
        }
        cursor.close();
        return livro;
    }

    @SuppressLint("Range")
    @Override
    public List<Livro> findAll() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String query = "SELECT e.*, l.isbn, l.edicao FROM Livro l INNER JOIN Exemplar e ON l.codigo = e.codigo";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Livro livro = new Livro();
            livro.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            livro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            livro.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            livro.setISBN(cursor.getString(cursor.getColumnIndex("isbn")));
            livro.setEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));

            livros.add(livro);
            cursor.moveToNext();
        }
        cursor.close();
        return livros;
    }

    private ContentValues exemplarValues(Livro l) {
        ContentValues c = new ContentValues();
        c.put("codigo", l.getCodigo());
        c.put("nome", l.getNome());
        c.put("qtd_paginas", l.getQtdPaginas());
        return c;
    }

    private ContentValues livroValues(Livro l) {
        ContentValues c = new ContentValues();
        c.put("codigo", l.getCodigo());
        c.put("isbn", l.getISBN());
        c.put("edicao", l.getEdicao());
        return c;
    }
}
