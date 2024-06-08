package br.edu.fateczl.academic_library.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.academic_library.control.factory.IAlunoFactory;
import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;
import br.edu.fateczl.academic_library.model.Revista;

public class CollectionController implements ICollection {
    public Exemplar findExemplar(int cod) throws Exception {
        List<Exemplar> exemplars = findExemplares();
        for (Exemplar ex : exemplars) {
            if (cod == ex.getCodigo()) {
                return ex;
            }
        }
        return null;
    }

    @Override
    public List<Exemplar> findExemplares() throws Exception {
        IController<Livro> livroI = new LivroController();
        IController<Revista> revistaI = new RevistaController();
        List<Exemplar> exemplares = new ArrayList<>();

        List<Livro> livros =  livroI.listar();
        List<Revista> revistas = revistaI.listar();

        exemplares.addAll(livros);
        exemplares.addAll(revistas);
        return exemplares;
    }

    @Override
    public Aluno findAluno(int codAluno) throws Exception {
        IController<Aluno> alunoICont = new AlunoController();
        IAlunoFactory factory = new AlunoController();
        Aluno aluno = factory.AlunoFactory(codAluno, null, null);

        return alunoICont.buscar(aluno);
    }

    @Override
    public List<Aluno> findAlunos() throws Exception {
        IController<Aluno> alunoICont = new AlunoController();
        return alunoICont.listar();
    }
}
