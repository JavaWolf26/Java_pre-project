package kata.preproject.springboot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public final class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty(message = "FirstName should no be empty")
    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters")
    @Pattern(regexp = "[А-ЯA-Z][а-яА-Яa-zA-Z\\s\\-]*", message = "FirstName must begin with a capital letter")
    private String firstname;

    @Column
    @NotEmpty(message = "LastName should no be empty")
    @Size(min = 2, max = 30, message = "LastName should be between 2 and 15 characters")
    @Pattern(regexp = "[А-ЯA-Z][а-яА-Яa-zA-Z\\s\\-]*", message = "LastName must begin with a capital letter")
    private String lastname;

    @Column(unique = true)
    @NotEmpty(message = "Email should no be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Column
    @Size(min = 4, message = "Password should be between 4 and 10 characters")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @Column
    @Min(value = 0, message = "Age should be greater than 0")
    private Byte age;

    @Column
    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    public boolean hasRole(String roleName) {
        if (null == roles || 0 == roles.size()) {
            return false;
        }
        Optional<Role> findRole = roles.stream().filter(role -> roleName.equals(role.getName())).findFirst();
        return findRole.isPresent();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
