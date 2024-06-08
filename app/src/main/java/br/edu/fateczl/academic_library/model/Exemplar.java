package br.edu.fateczl.academic_library.model;

import androidx.annotation.NonNull;

public abstract class Exemplar {
    protected int codigo;
    protected String nome;
    protected int qtdPaginas;

    public Exemplar() {
        super();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdPaginas() {
        return qtdPaginas;
    }

    public void setQtdPaginas(int qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }

    @NonNull
    @Override
    public String toString() {
        return "#" + codigo + " - " + nome + " - Págs: " + qtdPaginas;
    }
}
