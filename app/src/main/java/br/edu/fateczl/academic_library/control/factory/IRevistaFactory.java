package br.edu.fateczl.academic_library.control.factory;

import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Revista;

public interface IRevistaFactory extends IExemplarFactory{
    Revista RevistaFactory(Exemplar ex, String ISSN);
}
