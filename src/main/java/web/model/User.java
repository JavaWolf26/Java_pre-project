package web.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty(message = "FirstName should no be empty")
    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters")
    @Pattern(regexp = "[А-ЯA-Z][а-яА-Яa-zA-Z\\s\\-]*", message = "FirstName must begin with a capital letter")
    private String firstName;

    @Column
    @NotEmpty(message = "LastName should no be empty")
    @Size(min = 2, max = 30, message = "LastName should be between 2 and 15 characters")
    @Pattern(regexp = "[А-ЯA-Z][а-яА-Яa-zA-Z\\s\\-]*", message = "LastName must begin with a capital letter")
    private String lastName;

    @Column
    @Min(value = 0, message = "Age should be greater than 0")
    private Byte age;

    @Column
    @NotEmpty(message = "Email should no be empty")
    @Email(message = "Email should be valid")
    private String email;

    public User() {
    }
}
