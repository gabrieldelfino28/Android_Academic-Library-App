package br.edu.fateczl.academic_library.control.factory;

import br.edu.fateczl.academic_library.model.Aluguel;
import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;

public interface IAluguelFactory {
    Aluguel AluguelFactory(Aluno aluno, Exemplar ex, String dataRetirada, String dataDevolucao);
}
