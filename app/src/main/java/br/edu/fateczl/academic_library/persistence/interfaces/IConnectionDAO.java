package br.edu.fateczl.academic_library.persistence.interfaces;

import java.sql.SQLException;

public interface IConnectionDAO<T> {
    T open() throws SQLException;
    void close();
}
