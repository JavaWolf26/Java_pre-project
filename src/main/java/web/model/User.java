package web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements UserDetails {

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

    @Column(unique = true)
    @NotEmpty(message = "Email should no be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Column
    private boolean enabled;

    @Column
    @Size(min = 4, max = 10, message = "Password should be between 4 and 10 characters")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}