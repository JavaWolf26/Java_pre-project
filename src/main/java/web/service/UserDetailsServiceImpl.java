package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        TypedQuery<User> tq = entityManager.createQuery("select distinct u from User u " +
                "left join fetch u.roles where u.email = :email", User.class);
        tq.setParameter("email", email);
        return tq.getResultList().stream().findAny().orElse(null);
    }
}
