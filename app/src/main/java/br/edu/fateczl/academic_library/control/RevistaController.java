package br.edu.fateczl.academic_library.control;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.academic_library.control.factory.IRevistaFactory;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;
import br.edu.fateczl.academic_library.model.Revista;

public class RevistaController implements IRevistaFactory, IController<Revista> {
    @Override
    public Exemplar ExemplarFactory(int codExemplar, String nome, int qtdPag) {
        Exemplar ex = new Livro();
        ex.setNome(nome);
        ex.setCodigo(codExemplar);
        ex.setQtdPaginas(qtdPag);
        return ex;
    }

    @Override
    public Revista RevistaFactory(Exemplar ex, String ISSN) {
        Revista revista = new Revista();
        revista.setNome(ex.getNome());
        revista.setCodigo(ex.getCodigo());
        revista.setQtdPaginas(ex.getQtdPaginas());
        revista.setISSN(ISSN);
        return revista;
    }

    @Override
    public void inserir(Revista revista) throws SQLException {

    }

    @Override
    public void atualizar(Revista revista) throws SQLException {

    }

    @Override
    public void remover(Revista revista) throws SQLException {

    }

    @Override
    public Revista buscar(Revista revista) throws SQLException {
        return null;
    }

    @Override
    public List<Revista> listar() throws SQLException {
        return null;
    }
}
