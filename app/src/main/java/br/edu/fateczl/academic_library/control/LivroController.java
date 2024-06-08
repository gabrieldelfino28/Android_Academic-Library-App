package br.edu.fateczl.academic_library.control;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.academic_library.control.factory.ILivroFactory;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;

public class LivroController implements ILivroFactory, IController<Livro> {
    @Override
    public Exemplar ExemplarFactory(int codExemplar, String nome, int qtdPag) {
        Exemplar ex = new Livro();
        ex.setNome(nome);
        ex.setCodigo(codExemplar);
        ex.setQtdPaginas(qtdPag);
        return ex;
    }

    @Override
    public Livro LivroFactory(Exemplar ex, String ISBN, int edicao) {
        Livro livro = new Livro();
        livro.setNome(ex.getNome());
        livro.setCodigo(ex.getCodigo());
        livro.setQtdPaginas(ex.getQtdPaginas());
        livro.setISBN(ISBN);
        livro.setEdicao(edicao);
        return livro;
    }

    @Override
    public void inserir(Livro livro) throws SQLException {

    }

    @Override
    public void atualizar(Livro livro) throws SQLException {

    }

    @Override
    public void remover(Livro livro) throws SQLException {

    }

    @Override
    public Livro buscar(Livro livro) throws SQLException {
        return null;
    }

    @Override
    public List<Livro> listar() throws SQLException {
        return null;
    }
}
