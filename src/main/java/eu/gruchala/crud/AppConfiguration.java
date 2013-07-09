package eu.gruchala.crud;

import java.io.IOException;
import java.util.HashMap;

import eu.gruchala.crud.model.Person;
import eu.gruchala.crud.utils.HashProvider;
import eu.gruchala.crud.utils.Sha256Hasher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.template.TemplateException;
import freemarker.template.utility.XmlEscape;

@Configuration
@EnableWebMvc
@ComponentScan("eu.gruchala.crud")
@Import(value = {HibernateConfiguration.class})
public class AppConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger L = LoggerFactory.getLogger(AppConfiguration.class);
    private static final String TEMPLATE_LOADER_PATH = "/views/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/**");
    }

    @Bean
    public FreeMarkerViewResolver viewResolver() {
        final FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");
        return freeMarkerViewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws TemplateException, IOException {
        final FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath(TEMPLATE_LOADER_PATH);
        final HashMap<String, Object> variables = new HashMap<String, Object>();
        variables.put("xml_escape", new XmlEscape());
        variables.put("number_format", "0.######");
        freeMarkerConfigurer.setFreemarkerVariables(variables);
                final freemarker.template.Configuration configuration = freeMarkerConfigurer.createConfiguration();
                configuration.setNumberFormat("0.######");
                if (L.isDebugEnabled()) L.debug("Looking for ftl views at: " + TEMPLATE_LOADER_PATH);
        return freeMarkerConfigurer;
    }

    @Bean
    public HashProvider<Person> getHasher() {
        return new Sha256Hasher();
    }
}
