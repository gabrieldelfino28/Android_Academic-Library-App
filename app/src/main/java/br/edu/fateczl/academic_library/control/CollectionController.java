package br.edu.fateczl.academic_library.control;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.academic_library.control.factory.IAlunoFactory;
import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;
import br.edu.fateczl.academic_library.model.Revista;
import br.edu.fateczl.academic_library.persistence.AlunoDAO;
import br.edu.fateczl.academic_library.persistence.LivroDAO;
import br.edu.fateczl.academic_library.persistence.RevistaDAO;

public class CollectionController implements ICollection {

    @Override
    public List<Exemplar> findExemplares(Context context) throws Exception {
        List<Exemplar> exemplares = new ArrayList<>();

        IController<Livro> livroI = new LivroController(new LivroDAO(context));
        IController<Revista> revistaI = new RevistaController(new RevistaDAO(context));

        List<Livro> livros = livroI.listar();
        List<Revista> revistas = revistaI.listar();

        exemplares.addAll(livros);
        exemplares.addAll(revistas);

        return exemplares;
    }

    @Override
    public List<Aluno> findAlunos(Context context) throws Exception {
        IController<Aluno> alunoICont = new AlunoController(new AlunoDAO(context));
        return alunoICont.listar();
    }
}
