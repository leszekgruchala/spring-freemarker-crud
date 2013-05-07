package eu.gruchala.crud;

import eu.gruchala.crud.model.Person;
import eu.gruchala.crud.utils.HashProvider;
import eu.gruchala.crud.utils.Sha256Hasher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@Import(value = HibernateConfiguration.class)
@ComponentScan("eu.gruchala.crud.model")
public class TestConfiguration {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public HashProvider<Person> getHasher() {
        return new Sha256Hasher();
    }
}
