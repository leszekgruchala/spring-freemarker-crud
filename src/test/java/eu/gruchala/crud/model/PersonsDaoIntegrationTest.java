package eu.gruchala.crud.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eu.gruchala.crud.TestConfiguration;
import eu.gruchala.crud.utils.HashProvider;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestConfiguration.class})
public class PersonsDaoIntegrationTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private Persons personsDao;
    private Person person;

    @BeforeMethod
    public void setUp() throws Exception {
        person = new Person();
        final Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, -1);
        final Date yesterday = new Date(today.getTimeInMillis());
        person.setBirthDate(yesterday);
        person.setEmail("valid@email.pl");
        person.setName("Franciszek Dolas");
    }

    @Test
    public void shouldDbBeInitialized() throws Exception {
        //when
        final List<Person> people = personsDao.getAll();

        //then
        assertThat(people).isNotNull().isEmpty();
    }

    @Test
    public void shouldAddPerson() throws Exception {
        //when
        personsDao.create(person);

        //then
        assertThat(person.getId()).isNotNull();
    }

    @Test(dependsOnMethods = "shouldAddPerson")
    public void shouldGetPerson() throws Exception {
        //given
        personsDao.create(person);

        //when
        final List<Person> people = personsDao.getAll();

        //then
        assertThat(people).isNotNull().isNotEmpty();
        assertThat(people.get(0)).isNotNull();
        assertThat(people.get(0).getName()).isEqualTo(person.getName());
        assertThat(people.get(0).getId()).isNull();
    }

    @Test(dependsOnMethods = "shouldAddPerson")
    public void shouldEditPerson() throws Exception {
        //given
        personsDao.create(person);
        final String newName = "Grzegorz Brzeczyszczykiewicz";
        person.setName(newName);

        //when
        personsDao.update(person);

        //then
        final List<Person> people = personsDao.getAll();
        assertThat(people).isNotNull().isNotEmpty();
        final Person actualPerson = people.get(0);
        assertThat(actualPerson).isNotNull();
        assertThat(actualPerson.getName()).isEqualTo(newName);
    }

    @Test(dependsOnMethods = "shouldAddPerson")
    public void shouldDeletePerson() throws Exception {
        //given
        personsDao.create(person);
        final String hash = personsDao.getAll().get(0).getHash();

        //when
        personsDao.delete(hash);

        //then
        final List<Person> people = personsDao.getAll();
        assertThat(people).isNotNull().isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldProduceHashForNewPerson() throws Exception {
        //given
        final SessionFactory sessionFactory = mock(SessionFactory.class);
        final HashProvider<Person> hashProvider = mock(HashProvider.class);
        final PersonsDao dao = new PersonsDao(sessionFactory, hashProvider);
        final Session session = mock(Session.class);
        final Person mockedPerson = mock(Person.class);
        when(hashProvider.get(mockedPerson)).thenReturn("asd");
        when(sessionFactory.getCurrentSession()).thenReturn(session);

        //when
        dao.create(mockedPerson);

        //then
        verify(hashProvider, times(1)).get(mockedPerson);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).persist(mockedPerson);
    }
}
