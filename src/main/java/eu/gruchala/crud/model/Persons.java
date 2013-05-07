package eu.gruchala.crud.model;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface Persons {

    List<Person> getAll();

    void create(Person person);

    void update(Person person);

    void delete(String hash);
}
