package nl.hsleiden.service;

import nl.hsleiden.auth.Role;
import nl.hsleiden.model.User;
import nl.hsleiden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        User user = userRepository.findByEmail(email);

        if (user == null) throw new UsernameNotFoundException(email);

        if (this.isAdmin(user))
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN));
        else if (this.isUser(user))
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    public boolean isUser(User user) {
        return user != null;
    }

    public boolean isAdmin(User user) {
        return this.isUser(user) && user.getId() == 7;
    }
}
