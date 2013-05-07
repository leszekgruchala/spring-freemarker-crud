package eu.gruchala.crud.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PEOPLE")
public class Person implements Serializable {

    private static final long serialVersionUID = -172412219240754562L;
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    @Length(min = 3, max = 100, message = "Name needs to have at least 3 characters")
    @Pattern(regexp = "(\\w+)(\\s)(\\w+)", message = "Provide your name and surname")
    private String name;
    @NotNull(message = "Birthdate cannot be empty")
    @Past(message = "Birthdate shall be from the past")
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date birthDate;
    @Email(message = "Please provide valid email address.")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    private String hash;

    public Person() {}

    public Person(final String name, final Date birthDate, final String email, final String hash) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.hash = hash;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setHash(final String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    @Transient
    public String getFormattedBirthDate() {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(birthDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", email='").append(email).append('\'');
        sb.append(", hash='").append(hash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
