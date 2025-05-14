package org.example.gilbertxdheis.infrastructure;

import org.springframework.stereotype.Repository;

@Repository
public class JdbcItemRepository implements CRUDRepository {
    @Override
    public void create(Object entity) {

    }

    @Override
    public Object read(Object o) {
        return null;
    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public Iterable findAll() {
        return null;
    }
}
