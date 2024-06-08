package br.edu.fateczl.academic_library.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDAO extends SQLiteOpenHelper {

    private static final String DATA_BASE = "LIBRARY.DB";
    private static final int DATA_BASE_VER = 1;

    private final static String CREATE_TABLE_ALUNO =
            "CREATE TABLE Aluno (" +
                    "ra NUMERIC(10) UNIQUE PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(50) NOT NULL);";

    private final static String CREATE_TABLE_EXEMPLAR =
            "CREATE TABLE Exemplar (" +
                    "codigo NUMERIC(10) UNIQUE PRIMARY KEY, " +
                    "nome varchar(100), " +
                    "qtd_paginas integer(10));";
    private final static String CREATE_TABLE_LIVRO =
            "CREATE TABLE Livro( " +
                    "codigo NUMERIC(10) UNIQUE, " +
                    "isbn varchar(40), " +
                    "edicao integer(10), " +
                    "PRIMARY KEY(codigo), " +
                    "FOREIGN KEY(codigo) REFERENCES EXEMPLAR(codigo) " +
                    ");";

    private final static String CREATE_TABLE_REVISTA =
            "CREATE TABLE Revista( " +
                    "codigo NUMERIC(10) UNIQUE, " +
                    "issn varchar(40), " +
                    "PRIMARY KEY(codigo), " +
                    "FOREIGN KEY(codigo) REFERENCES EXEMPLAR(codigo) " +
                    ");";

    private final static String CREATE_ALUGUEL =
            "CREATE TABLE Aluguel( " +
                    "codigo NUMERIC(10) UNIQUE NOT NULL, " +
                    "ra NUMERIC(10) UNIQUE NOT NULL, " +
                    "data_retirada VARCHAR(30) NOT NULL, " +
                    "data_devolucao VARCHAR(30), " +
                    "PRIMARY KEY(codigo, ra, data_retirada), " +
                    "FOREIGN KEY(codigo) REFERENCES EXEMPLAR(codigo), " +
                    "FOREIGN KEY(ra) REFERENCES ALUNO(ra) " +
                    ");";
    public GenericDAO(Context context) {
        super(context, DATA_BASE, null, DATA_BASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALUNO);
        db.execSQL(CREATE_TABLE_EXEMPLAR);
        db.execSQL(CREATE_TABLE_LIVRO);
        db.execSQL(CREATE_TABLE_REVISTA);
        db.execSQL(CREATE_ALUGUEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS Aluguel");
            db.execSQL("DROP TABLE IF EXISTS Aluno");
            db.execSQL("DROP TABLE IF EXISTS Livro");
            db.execSQL("DROP TABLE IF EXISTS Revista");
            db.execSQL("DROP TABLE IF EXISTS Exemplar");
            onCreate(db);
        }
    }
}
