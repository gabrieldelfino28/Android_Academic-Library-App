package br.edu.fateczl.academic_library.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.persistence.interfaces.IConnectionDAO;
import br.edu.fateczl.academic_library.persistence.interfaces.ICrudDAO;

public class AlunoDAO implements IConnectionDAO<AlunoDAO>, ICrudDAO<Aluno> {

    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;

    public AlunoDAO(Context context) {
        this.context = context;
    }

    @Override
    public AlunoDAO open() throws SQLException {
        gDAO = new GenericDAO(context);
        db = gDAO.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDAO.close();
    }

    @Override
    public void insert(Aluno aluno) throws SQLException {
        db.insert("Aluno", null, getContentValues(aluno));
    }

    @Override
    public int update(Aluno aluno) throws SQLException {
        return db.update("Aluno", getContentValues(aluno), "ra = " + aluno.getRA(), null);
    }

    @Override
    public void delete(Aluno aluno) throws SQLException {
        db.delete("Aluno", "ra = " + aluno.getRA(), null);
    }

    @SuppressLint("Range")
    @Override
    public Aluno findOne(Aluno aluno) throws SQLException {
        String query ="SELECT * FROM Aluno WHERE ra = " + aluno.getRA();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            aluno.setRA(cursor.getInt(cursor.getColumnIndex("ra")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        }
        cursor.close();
        return aluno;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluno> findAll() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String query = "SELECT * FROM Aluno";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()) {
            Aluno aluno = new Aluno();
            aluno.setRA(cursor.getInt(cursor.getColumnIndex("ra")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));

            alunos.add(aluno);
            cursor.moveToNext();
        }
        cursor.close();
        return alunos;
    }

    private ContentValues getContentValues(Aluno aluno) {
        ContentValues c = new ContentValues();
        c.put("ra", aluno.getRA());
        c.put("nome", aluno.getNome());
        c.put("email", aluno.getEmail());
        return c;
    }
}
