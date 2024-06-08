package br.edu.fateczl.academic_library;

public interface IFragOperations<T> {
    void operacaoInserir();

    void operacaoAtualizar();

    void operacaoDeletar();

    void operacaoBuscar();

    void operacaoListar();

    void fillFields(T t);

    void cleanFields();

    void checkFields() throws Exception;

    T mountObject();

    boolean isFound();
}
