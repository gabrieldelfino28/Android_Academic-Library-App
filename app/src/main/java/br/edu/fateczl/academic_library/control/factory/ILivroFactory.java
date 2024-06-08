package br.edu.fateczl.academic_library.control.factory;

import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;

public interface ILivroFactory extends IExemplarFactory{
    Livro LivroFactory(Exemplar ex, String ISBN, int edicao);
}
