package web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
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

//Условие:
//        Модуль Spring Security позволяет нам внедрять права доступа, а также контролировать их исполнение
//        без ручных проверок.
//        Spring Security базируется на 2х интерфейсах, которые определяют связь сущностей с секьюрностью:
//        UserDetails и GrantedAuthority.
//        UserDetails - то, что будет интерпретироваться системой как пользователь.
//        GrantedAuthority - сущность, описывающая права юзера.
//        Оба эти интерфейса имеют множество реализаций: просмотрите класс SecurityConfig, в методе configure()
//        с помощью настроек inMemoryAuthentication() мы собираем единственный на всю программу экземпляр UserDetails
//        с именем и паролем админ-админ, а его роль “ADMIN” так же будет преобразована в экземпляр GrantedAuthority.
//        Это простейший способ создания секьюрности. Так же мы можем использовать jdbc-аутентификацию путем
//        написания запроса, возвращающего пользователя и роль.
//        Как вы понимаете, такие способы максимально просты, но лишены достаточной гибкости, потому наиболее
//        часто используемый вариант настройки выглядит как имплементация UserDetails и GrantedAuthority
//        в классах-сущностях с переопределением существующих методов.
//
//Новые классы:
//        - SpringSecurityInitializer - обязателен для не boot-приложения. Кода в нем нет, но требуется
//        для регистрации секьюрити в Спринг-контейнере.
//        - SecurityConfig - настройка секьюрности по определенным URL, а также настройка UserDetails и GrantedAuthority.
//        - LoginSuccessHandler - хэндлер, содержащий в себе алгоритм действий при успешной аутентификации.
//        Например, тут мы можем отправить пользователя с ролью админа на админку после логина,
//        а с ролью юзер на главную страницу сайта и т.п.
//
//Задание:
//        1. Перенесите классы и зависимости из примера в свое MVC приложение из предыдущей задачи.
//        2. Создайте класс Role и свяжите User с ролями так, чтобы юзер мог иметь несколько ролей.
//        3. Имплементируйте модели Role и User интерфейсами GrantedAuthority и UserDetails соответственно.
//        Измените настройку секьюрности с inMemory на userDetailService.
//        4. Все CRUD-операции и страницы для них должны быть доступны только пользователю с ролью admin по url: /admin/.
//        5. Пользователь с ролью user должен иметь доступ только к своей домашней странице /user,
//        где выводятся его данные. Доступ к этой странице должен быть только у пользователей с ролью user и admin.
//        Не забывайте про несколько ролей у пользователя!
//        6. Настройте logout с любой страницы с использованием возможностей thymeleaf.
//        7. Настройте LoginSuccessHandler так, чтобы админа после логина направляло на страницу /admin,
//        а юзера на его страницу.
