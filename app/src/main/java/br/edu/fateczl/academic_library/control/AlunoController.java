package br.edu.fateczl.academic_library.control;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.academic_library.control.factory.IAlunoFactory;
import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.persistence.AlunoDAO;

public class AlunoController implements IAlunoFactory, IController<Aluno>{

    private final AlunoDAO alunoDAO;

    public AlunoController(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    @Override
    public Aluno AlunoFactory(int RA, String nome, String email) {
        Aluno aluno = new Aluno();
        aluno.setRA(RA);
        aluno.setNome(nome);
        aluno.setEmail(email);
        return aluno;
    }

    @Override
    public void inserir(Aluno aluno) throws SQLException {
        if (alunoDAO.open() != null) {
            alunoDAO.open();
        }
        alunoDAO.insert(aluno);
        alunoDAO.close();
    }

    @Override
    public void atualizar(Aluno aluno) throws SQLException {
        if (alunoDAO.open() != null) {
            alunoDAO.open();
        }
        alunoDAO.update(aluno);
        alunoDAO.close();
    }

    @Override
    public void remover(Aluno aluno) throws SQLException {
        if (alunoDAO.open() != null) {
            alunoDAO.open();
        }
        alunoDAO.delete(aluno);
        alunoDAO.close();
    }

    @Override
    public Aluno buscar(Aluno aluno) throws SQLException {
        if (alunoDAO.open() != null) {
            alunoDAO.open();
        }
        Aluno al = alunoDAO.findOne(aluno);
        alunoDAO.close();
        return al;
    }

    @Override
    public List<Aluno> listar() throws SQLException {
        if (alunoDAO.open() != null) {
            alunoDAO.open();
        }
        List<Aluno> alunos = alunoDAO.findAll();
        alunoDAO.close();
        return alunos;
    }
}
