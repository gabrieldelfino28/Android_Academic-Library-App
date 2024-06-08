package br.edu.fateczl.academic_library.control;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.academic_library.control.factory.ILivroFactory;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;
import br.edu.fateczl.academic_library.persistence.LivroDAO;

public class LivroController implements ILivroFactory, IController<Livro> {

    private final LivroDAO lDAO;

    public LivroController(LivroDAO lDAO) {
        this.lDAO = lDAO;
    }
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
        if (lDAO.open() != null) {
            lDAO.open();
        }
        lDAO.insert(livro);
        lDAO.close();
    }

    @Override
    public void atualizar(Livro livro) throws SQLException {
        if (lDAO.open() != null) {
            lDAO.open();
        }
        lDAO.update(livro);
        lDAO.close();
    }

    @Override
    public void remover(Livro livro) throws SQLException {
        if (lDAO.open() != null) {
            lDAO.open();
        }
        lDAO.delete(livro);
        lDAO.close();
    }

    @Override
    public Livro buscar(Livro livro) throws SQLException {
        if (lDAO.open() != null) {
            lDAO.open();
        }
        Livro l = lDAO.findOne(livro);
        lDAO.close();
        return l;
    }

    @Override
    public List<Livro> listar() throws SQLException {
        if (lDAO.open() != null) {
            lDAO.open();
        }
        List<Livro> livros = lDAO.findAll();
        lDAO.close();
        return livros;
    }
}
