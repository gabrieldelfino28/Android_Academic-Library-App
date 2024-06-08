package br.edu.fateczl.academic_library.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.academic_library.model.Revista;
import br.edu.fateczl.academic_library.persistence.interfaces.IConnectionDAO;
import br.edu.fateczl.academic_library.persistence.interfaces.ICrudDAO;

public class RevistaDAO implements IConnectionDAO<RevistaDAO>, ICrudDAO<Revista> {
    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;
    public RevistaDAO(Context context) {
        this.context = context;
    }

    @Override
    public RevistaDAO open() throws SQLException {
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
    public void insert(Revista revista) throws SQLException {
        db.insert("Exemplar", null, exemplarValues(revista));
        db.insert("Revista", null, revistaValues(revista));
    }

    @Override
    public int update(Revista revista) throws SQLException {
        db.update("Exemplar", exemplarValues(revista), "codigo = " + revista.getCodigo(), null);
        db.update("Revista", revistaValues(revista), "codigo = " + revista.getCodigo(), null);
        return 0;
    }

    @Override
    public void delete(Revista revista) throws SQLException {
        db.delete("Revista", "codigo = " + revista.getCodigo(), null);
        db.delete("Exemplar", "codigo = " + revista.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Revista findOne(Revista revista) throws SQLException {
        String query = "SELECT e.*, r.issn FROM Revista r INNER JOIN Exemplar e ON r.codigo = e.codigo " +
                       "WHERE e.codigo = " + revista.getCodigo();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            revista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            revista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            revista.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            revista.setISSN(cursor.getString(cursor.getColumnIndex("issn")));
        }
        cursor.close();
        return revista;
    }

    @SuppressLint("Range")
    @Override
    public List<Revista> findAll() throws SQLException {
        List<Revista> revistas = new ArrayList<>();
        String query = "SELECT e.*, r.issn FROM Revista r INNER JOIN Exemplar e ON r.codigo = e.codigo";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Revista revista = new Revista();
            revista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            revista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            revista.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            revista.setISSN(cursor.getString(cursor.getColumnIndex("issn")));

            revistas.add(revista);
            cursor.moveToNext();
        }
        cursor.close();
        return revistas;
    }

    private ContentValues exemplarValues(Revista r) {
        ContentValues c = new ContentValues();
        c.put("codigo", r.getCodigo());
        c.put("nome", r.getNome());
        c.put("qtd_paginas", r.getQtdPaginas());
        return c;
    }

    private ContentValues revistaValues(Revista r) {
        ContentValues c = new ContentValues();
        c.put("codigo", r.getCodigo());
        c.put("issn", r.getISSN());
        return c;
    }
}
