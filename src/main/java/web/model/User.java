package web.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    @NotEmpty(message = "FirstName should no be empty")
    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters")
    @Pattern(regexp = "[А-ЯA-Z][а-яА-Яa-zA-Z\\s\\-]*", message = "FirstName must begin with a capital letter")
    private String username;

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

    @Column
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }
}
