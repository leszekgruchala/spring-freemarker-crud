package eu.gruchala.crud.model;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PersonsDao implements Persons {

    private final SessionFactory sessionFactory;

    @Inject
    public PersonsDao(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> getAll() {
        return getCurrentSession().createQuery("from eu.gruchala.crud.model.Person").list();
    }

    @Override
    public void create(final Person person) {
        getCurrentSession().persist(person);
    }

    @Override
    public void update(final Person person) {
        getCurrentSession().update(person);
    }

    @Override
    public void delete(final long personId) {
        final Object object = getCurrentSession().get(Person.class, personId);
        getCurrentSession().delete(object);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
