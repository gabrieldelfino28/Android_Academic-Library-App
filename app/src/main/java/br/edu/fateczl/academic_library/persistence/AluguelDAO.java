package br.edu.fateczl.academic_library.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.academic_library.model.Aluguel;
import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;
import br.edu.fateczl.academic_library.persistence.interfaces.IConnectionDAO;
import br.edu.fateczl.academic_library.persistence.interfaces.ICrudDAO;

public class AluguelDAO implements IConnectionDAO<AluguelDAO>, ICrudDAO<Aluguel> {
    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;

    public AluguelDAO(Context context) {
        this.context = context;
    }

    @Override
    public AluguelDAO open() throws SQLException {
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
    public void insert(Aluguel aluguel) throws SQLException {
        db.insert("Aluguel", null, aluguelValues(aluguel));
    }

    @Override
    public int update(Aluguel aluguel) throws SQLException {
        String[] whereArgs = {aluguel.getDataRetirada()};
        db.update("Aluguel", aluguelValues(aluguel), "data_retirada = ?", whereArgs);
        return 0;
    }

    @Override
    public void delete(Aluguel aluguel) throws SQLException {
        String[] whereArgs = {aluguel.getDataRetirada()};
        db.delete("Aluguel", "data_retirada = ?", whereArgs);
    }

    @SuppressLint("Range")
    @Override
    public Aluguel findOne(Aluguel aluguel) throws SQLException {
        String query =
                "SELECT al.data_retirada, al.data_devolucao, e.codigo, e.nome AS nome_exemplar, e.qtd_paginas, a.* " +
                "FROM Aluguel al INNER JOIN Exemplar e ON al.codigo = e.codigo " +
                "INNER JOIN Aluno a ON al.ra = a.ra AND al.data_retirada = ?";
        String[] args = {aluguel.getDataRetirada()};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToNext();
        }
        if(!cursor.isAfterLast()) {
            Aluno aluno = new Aluno();
            aluno.setRA(cursor.getInt(cursor.getColumnIndex("ra")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));

            Exemplar ex = new Livro();
            ex.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            ex.setNome(cursor.getString(cursor.getColumnIndex("nome_exemplar")));
            ex.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));

            aluguel.setExemplar(ex);
            aluguel.setAluno(aluno);
            aluguel.setDataRetirada(cursor.getString(cursor.getColumnIndex("data_retirada")));
            aluguel.setDataDevolucao(cursor.getString(cursor.getColumnIndex("data_devolucao")));
        }
        cursor.close();
        return aluguel;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluguel> findAll() throws SQLException {
        List<Aluguel> alugueis = new ArrayList<>();
        String query =
                "SELECT al.data_retirada, al.data_devolucao, e.codigo, e.nome AS nome_exemplar, e.qtd_paginas, a.* " +
                "FROM Aluguel al INNER JOIN Exemplar e ON al.codigo = e.codigo " +
                "INNER JOIN Aluno a ON al.ra = a.ra";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToNext();
        }
        while (!cursor.isAfterLast()) {
            Aluguel aluguel = new Aluguel();
            Aluno aluno = new Aluno();
            Exemplar ex = new Livro();

            aluno.setRA(cursor.getInt(cursor.getColumnIndex("ra")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            ex.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            ex.setNome(cursor.getString(cursor.getColumnIndex("nome_exemplar")));
            ex.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));

            aluguel.setExemplar(ex);
            aluguel.setAluno(aluno);
            aluguel.setDataRetirada(cursor.getString(cursor.getColumnIndex("data_retirada")));
            aluguel.setDataDevolucao(cursor.getString(cursor.getColumnIndex("data_devolucao")));

            alugueis.add(aluguel);
            cursor.moveToNext();
        }
        cursor.close();
        return alugueis;
    }

    private ContentValues aluguelValues(Aluguel aluguel) {
        ContentValues c = new ContentValues();
        c.put("codigo", aluguel.getExemplar().getCodigo());
        c.put("ra", aluguel.getAluno().getRA());
        c.put("data_retirada", aluguel.getDataRetirada());
        c.put("data_devolucao", aluguel.getDataDevolucao());
        return c;
    }
}
