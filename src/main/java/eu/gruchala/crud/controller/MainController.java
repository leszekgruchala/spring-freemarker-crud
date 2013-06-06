package eu.gruchala.crud.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import eu.gruchala.crud.model.Person;
import eu.gruchala.crud.model.Persons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"editablePerson"})
public class MainController {

    private final static Logger L = LoggerFactory.getLogger(MainController.class);
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private Persons personsDao;

    @Inject
    public MainController(final Persons personsDao) {
        this.personsDao = personsDao;
    }

    @ModelAttribute("people")
    public List<Person> getPeople() {
        return personsDao.getAll();
    }

    @ModelAttribute("person")
    public Person getPerson() {
        return new Person();
    }

    @ModelAttribute("editablePerson")
    public Person getEditablePerson() {
        return new Person();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/savePerson", method = RequestMethod.POST)
    public ModelAndView savePerson(@Validated @ModelAttribute("person") final Person person, final BindingResult result) {
        if (result.hasErrors()) {
            L.warn("Found errors.\n" + result.getAllErrors());
            return new ModelAndView("index");
        }
        personsDao.create(person);
        L.info("Added person: " + person);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/editPerson", method = RequestMethod.POST)
    public ModelAndView editPerson(@Validated @ModelAttribute("editablePerson") final Person person, final BindingResult result) {
        if (result.hasErrors()) {
            L.warn("Found errors.\n" + result.getAllErrors());
            final ModelAndView view = new ModelAndView("index");
            view.addObject("editWithErrors", "true");
            return view;
        }
        personsDao.update(person);
        L.info("Edited person: " + person);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/removePerson/{hash}", method = RequestMethod.POST)
    public ModelAndView removePerson(@PathVariable("hash") final String hash) {
        personsDao.delete(hash);
        L.debug("Attempting to remove person with hash: {}", hash);
        return new ModelAndView("redirect:/");
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat(DATE_FORMAT), true);
        binder.registerCustomEditor(Date.class, editor);
    }
}
