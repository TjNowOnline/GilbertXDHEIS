package org.example.gilbertxdheis.infrastucture;

public interface CRUDRepository <t, ID> {
    void create(t entity);
    t read(ID id);
    void update(t entity);
    void delete(ID id);
    Iterable<t> findAll();
}
