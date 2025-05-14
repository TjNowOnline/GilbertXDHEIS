package org.example.gilbertxdheis.infrastructure;

public interface CRUDRepository <T, ID> {
    void create(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
    Iterable<T> findAll();
}
