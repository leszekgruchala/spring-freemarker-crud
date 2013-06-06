package eu.gruchala.crud.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.gruchala.crud.model.Person;
import eu.gruchala.crud.model.Persons;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MainControllerTest {

    private Persons personsDao;
    private MainController mainController;
    private BindingResult bindingResult;
    private Person person;

    @BeforeMethod
    public void setUp() throws Exception {
        personsDao = mock(Persons.class);
        mainController = new MainController(personsDao);
        person = mock(Person.class);
        when(person.getId()).thenReturn(1l);
        when(person.getName()).thenReturn("Franciszek Dolas");
        when(person.getBirthDate()).thenReturn(new Date());
        bindingResult = mock(BindingResult.class);
    }

    @Test
    public void shouldProvideSomePeople() throws Exception {
        //given
        when(personsDao.getAll()).thenReturn(new ArrayList<Person>());

        //when
        final List<Person> people = mainController.getPeople();

        //then
        assertThat(people).isNotNull();
    }

    @Test
    public void shouldShowIndexPage() throws Exception {
        //when
        final String index = mainController.index();

        //then
        assertThat(index).isEqualTo("index");
    }

    @Test
    public void shouldAddPersonWithoutError() throws Exception {
        //given
        when(bindingResult.hasErrors()).thenReturn(false);

        //when
        final ModelAndView page = mainController.savePerson(person, bindingResult);

        //then
        assertThat(page).isNotNull();
        assertThat(page.isEmpty()).isFalse();
        assertThat(page.getViewName()).isEqualTo("redirect:/");
        assertThat(page.getModel()).isNullOrEmpty();
        verify(personsDao, times(1)).create(person);
    }

    @Test
    public void shouldNotAddPersonWithError() throws Exception {
        //given
        when(bindingResult.hasErrors()).thenReturn(true);

        //when
        final ModelAndView page = mainController.savePerson(person, bindingResult);

        //then
        assertThat(page).isNotNull();
        assertThat(page.isEmpty()).isFalse();
        assertThat(page.getViewName()).isEqualTo("index");
        verifyZeroInteractions(personsDao);
    }

    @Test
    public void shouldEditPersonWithoutError() throws Exception {
        //given
        when(bindingResult.hasErrors()).thenReturn(false);

        //when
        final ModelAndView page = mainController.editPerson(person, bindingResult);

        //then
        assertThat(page).isNotNull();
        assertThat(page.isEmpty()).isFalse();
        assertThat(page.getViewName()).isEqualTo("redirect:/");
        assertThat(page.getModel()).isNullOrEmpty();
        assertThat(page.getModel().get("currentlyEditedPerson")).isNull();
        verify(personsDao, times(1)).update(person);
    }

    @Test
    public void shouldNotEditPersonWithError() throws Exception {
        //given
        when(bindingResult.hasErrors()).thenReturn(true);

        //when
        final ModelAndView page = mainController.editPerson(person, bindingResult);

        //then
        assertThat(page).isNotNull();
        assertThat(page.isEmpty()).isFalse();
        assertThat(page.getViewName()).isEqualTo("index");
        assertThat(page.getModel().get("editWithErrors")).isEqualTo("true");
        verifyZeroInteractions(personsDao);
    }

    @Test
    public void shouldRemovePerson() throws Exception {
        //given valid hash
        personsDao.create(person);
        final String hash = "ABD123";

        //when
        final ModelAndView page = mainController.removePerson(hash);

        //then
        verify(personsDao, times(1)).delete(hash);
        assertThat(page).isNotNull();
        assertThat(page.isEmpty()).isFalse();
        assertThat(page.getViewName()).isEqualTo("redirect:/");
    }
}
