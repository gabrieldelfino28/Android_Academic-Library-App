package br.edu.fateczl.academic_library.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.academic_library.control.factory.IAluguelFactory;
import br.edu.fateczl.academic_library.model.Aluguel;
import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;
import br.edu.fateczl.academic_library.persistence.AluguelDAO;

public class AluguelController implements IAluguelFactory, IController<Aluguel> {

    private final AluguelDAO alDAO;

    public AluguelController(AluguelDAO alDAO) {
        this.alDAO = alDAO;
    }
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
        if (alDAO.open() != null) {
            alDAO.open();
        }
        alDAO.insert(aluguel);
        alDAO.close();
    }

    @Override
    public void atualizar(Aluguel aluguel) throws SQLException {
        if (alDAO.open() != null) {
            alDAO.open();
        }
        alDAO.update(aluguel);
        alDAO.close();
    }

    @Override
    public void remover(Aluguel aluguel) throws SQLException {
        if (alDAO.open() != null) {
            alDAO.open();
        }
        alDAO.delete(aluguel);
        alDAO.close();
    }

    @Override
    public Aluguel buscar(Aluguel aluguel) throws SQLException {
        if (alDAO.open() != null) {
            alDAO.open();
        }
        Aluguel al = alDAO.findOne(aluguel);
        alDAO.close();
        return al;
    }

    @Override
    public List<Aluguel> listar() throws SQLException {
        if (alDAO.open() != null) {
            alDAO.open();
        }
        List<Aluguel> list = alDAO.findAll();
        alDAO.close();
        return list;
    }
}
