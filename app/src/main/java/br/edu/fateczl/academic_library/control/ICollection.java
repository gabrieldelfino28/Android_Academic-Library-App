package br.edu.fateczl.academic_library.control;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;

public interface ICollection {

    List<Exemplar> findExemplares(Context context) throws Exception;

    List<Aluno> findAlunos(Context context) throws Exception;
}
