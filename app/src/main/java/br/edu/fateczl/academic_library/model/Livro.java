package br.edu.fateczl.academic_library.model;

import androidx.annotation.NonNull;

public class Livro extends Exemplar{
    private String ISBN;
    private int edicao;

    public Livro() {
        super();
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    @NonNull
    @Override
    public String toString() {
        return "#" + codigo + " - " + nome + " - Págs: " + qtdPaginas + " - " + ISBN + " - " + edicao;
    }
}
