package eu.gruchala.crud.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eu.gruchala.crud.TestConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;

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
        assertThat(people.get(0)).isNotNull().isEqualTo(person);
    }

    @Test(dependsOnMethods = "shouldAddPerson")
    public void shouldEditPerson() throws Exception {
        //given
        personsDao.create(person);
        person.setName("Grzegorz Brzeczyszczykiewicz");

        //when
        personsDao.update(person);

        //then
        final List<Person> people = personsDao.getAll();
        assertThat(people).isNotNull().isNotEmpty();
        assertThat(people.get(0)).isNotNull().isEqualTo(person);
    }

    @Test(dependsOnMethods = "shouldAddPerson")
    public void shouldRemovePerson() throws Exception {
        //given
        personsDao.create(person);

        //when
        personsDao.delete(person.getId());

        //then
        final List<Person> people = personsDao.getAll();
        assertThat(people).isNotNull().isEmpty();
    }
}
