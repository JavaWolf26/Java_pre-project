package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.security.LoginSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{bcrypt}$2y$10$7snpJ7LL.BaQUrryMm1qLOEF128t/W95fouhBoE64nfZNGNz4BHKW")
//                .roles("ADMIN");
////         конфигурация для прохождения аутентификации
////        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                //указываем логику обработки при логине
                .successHandler(new LoginSuccessHandler())
                // указываем action с формы логина
                .loginProcessingUrl("/login")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable();

        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/hello").authenticated()
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/usersNo/**").hasRole("USER")
                //страницы аутентификаци доступна всем
//                .antMatchers("/login").anonymous()
                // защищенные URL
//                .antMatchers("/hello").access("hasAnyRole('ADMIN')").anyRequest().authenticated();
                .and()
                // Spring сам подставит свою логин форму
                .formLogin()
                .and()
                //в корень приложения
                .logout().logoutSuccessUrl("/");
    }

    @Bean
    public UserDetailsService detailsService(){
        UserDetails userDetailsService = User.builder()
                .username("user")
                .password("{bcrypt}$2y$10$5duTig6OVYC5ZSTJJO5tjOvwR/IjjcvVHOoZY7ryJ8mOHGidPhqIC")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2y$10$7snpJ7LL.BaQUrryMm1qLOEF128t/W95fouhBoE64nfZNGNz4BHKW")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(userDetailsService, admin);
    }
}
