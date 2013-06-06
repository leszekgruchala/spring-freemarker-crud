package eu.gruchala.crud.model;

import java.util.List;

import javax.inject.Inject;

import eu.gruchala.crud.utils.HashProvider;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public class PersonsDao implements Persons {

    private final SessionFactory sessionFactory;
    private final HashProvider<Person> hasher;

    @Inject
    public PersonsDao(final SessionFactory sessionFactory, final HashProvider<Person> hasher) {
        this.sessionFactory = sessionFactory;
        this.hasher = hasher;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> getAll() {
        final Session currentSession = getCurrentSession();
        final Query query = currentSession.createQuery("select new Person(name, birthDate, email, hash) from Person");
        return query.list();
    }

    @Override
    public void create(final Person person) {
        person.setHash(hasher.get(person));
        getCurrentSession().persist(person);
    }

    @Override
    public void update(final Person person) {
        final Session currentSession = getCurrentSession();
        final Person oldPerson = getByHash(person.getHash(), currentSession);
        person.setId(oldPerson.getId());
        currentSession.merge(person);
    }

    @Override
    public void delete(final String hash) {
        final Session currentSession = getCurrentSession();
        final Person person = getByHash(hash, currentSession);
        currentSession.delete(person);
    }

    private Person getByHash(final String hash, final Session currentSession) {
        final Criteria criteria = currentSession.createCriteria(Person.class);
        criteria.add(Restrictions.eq("hash", hash));
        return (Person) criteria.uniqueResult();
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
