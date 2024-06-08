package br.edu.fateczl.academic_library.control;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;

public interface ICollection {
    Exemplar findExemplar(int codEx) throws Exception;

    List<Exemplar> findExemplares() throws Exception;

    Aluno findAluno(int codAluno) throws Exception;

    List<Aluno> findAlunos() throws Exception;
}
