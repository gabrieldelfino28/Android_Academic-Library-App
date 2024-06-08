package br.edu.fateczl.academic_library.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.academic_library.control.factory.IAluguelFactory;
import br.edu.fateczl.academic_library.model.Aluguel;
import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;

public class AluguelController implements IAluguelFactory, IController<Aluguel> {
    @Override
    public Aluguel AluguelFactory(Aluno aluno, Exemplar ex, String dataRetirada, String dataDevolucao) {
        Aluguel aluguel = new Aluguel();
        aluguel.setAluno(aluno);
        aluguel.setExemplar(ex);
        aluguel.setDataRetirada(dataRetirada);
        aluguel.setDataDevolucao(dataDevolucao);
        return aluguel;
    }

    @Override
    public void inserir(Aluguel aluguel) throws SQLException {

    }

    @Override
    public void atualizar(Aluguel aluguel) throws SQLException {

    }

    @Override
    public void remover(Aluguel aluguel) throws SQLException {

    }

    @Override
    public Aluguel buscar(Aluguel aluguel) throws SQLException {
        return null;
    }

    @Override
    public List<Aluguel> listar() throws SQLException {
        return null;
    }
}
