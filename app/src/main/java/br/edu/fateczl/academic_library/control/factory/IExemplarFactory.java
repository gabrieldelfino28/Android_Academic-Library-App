package br.edu.fateczl.academic_library.control.factory;

import br.edu.fateczl.academic_library.model.Exemplar;

public interface IExemplarFactory {
    Exemplar ExemplarFactory(int codExemplar, String nome, int qtdPag);
}
