package eu.gruchala.crud.model;

import java.util.Date;

import eu.gruchala.crud.InvalidPersonDataProvider;
import eu.gruchala.crud.TestConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;

@ContextConfiguration(classes = {TestConfiguration.class})
public class PersonValidatorTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private Validator validator;

    @Test(dataProvider = "invalidNameDataProvider", dataProviderClass = InvalidPersonDataProvider.class)
    public void shouldNotPassWrongName(final String name) throws Exception {
        //given
        final Person person = new Person();
        person.setName(name);
        final BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(person, "person");

        //when
        validator.validate(person, bindingResult);

        //then
        assertThat(bindingResult).isNotNull();
        assertThat(bindingResult.hasErrors()).isTrue();
    }

    @Test(dataProvider = "invalidBirthDateDataProvider", dataProviderClass = InvalidPersonDataProvider.class)
    public void shouldNotPassWrongBirthDate(final Date birthDate) throws Exception {
        //given
        final Person person = new Person();
        person.setName("Valid name");
        person.setBirthDate(birthDate);
        final BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(person, "person");

        //when
        validator.validate(person, bindingResult);

        //then
        assertThat(bindingResult).isNotNull();
        assertThat(bindingResult.hasErrors()).isTrue();
    }

    @Test(dataProvider = "invalidEmailDataProvider", dataProviderClass = InvalidPersonDataProvider.class)
    public void shouldNotPassWrongEmail(final String email) throws Exception {
        //given
        final Person person = new Person();
        person.setName("Valid name");
        person.setBirthDate(new Date());
        person.setEmail(email);
        final BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(person, "person");

        //when
        validator.validate(person, bindingResult);

        //then
        assertThat(bindingResult).isNotNull();
        assertThat(bindingResult.hasErrors()).isTrue();
    }
}
