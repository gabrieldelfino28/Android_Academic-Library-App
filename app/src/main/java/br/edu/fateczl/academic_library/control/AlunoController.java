package br.edu.fateczl.academic_library.control;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.academic_library.control.factory.IAlunoFactory;
import br.edu.fateczl.academic_library.model.Aluno;

public class AlunoController implements IAlunoFactory, IController<Aluno>{
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

    }

    @Override
    public void atualizar(Aluno aluno) throws SQLException {

    }

    @Override
    public void remover(Aluno aluno) throws SQLException {

    }

    @Override
    public Aluno buscar(Aluno aluno) throws SQLException {
        return aluno;
    }

    @Override
    public List<Aluno> listar() throws SQLException {
        List<Aluno> alunos = null;
        return alunos;
    }
}
