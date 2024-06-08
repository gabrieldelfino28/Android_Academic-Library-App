package br.edu.fateczl.academic_library.control.factory;

import br.edu.fateczl.academic_library.model.Aluno;

public interface IAlunoFactory {
    Aluno AlunoFactory(int RA, String nome, String email);
}
