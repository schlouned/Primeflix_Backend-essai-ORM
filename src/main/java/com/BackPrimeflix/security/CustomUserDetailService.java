package com.BackPrimeflix.security;

import com.BackPrimeflix.model.Role;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailService implements UserDetailsService{
    //members
    @Autowired
    UserRepository userRepository;

    //load user by username where user name = email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final UserEntity customer = userRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }
        boolean enabled = !customer.isAccountVerified(); // we can use this in case we want to activate account after customer verified the account
        UserDetails user = User.withUsername(customer.getEmail())
                .password(customer.getPassword())
                .disabled(customer.isLoginDisabled())
                .authorities(getAuthorities(customer)).build()
                ;

        return user;
    }

    //find which authorities has the user
    private Collection<GrantedAuthority> getAuthorities(UserEntity user){
        Set<Role> userRoles = user.getUserRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
        for(Role userRole : userRoles){
            authorities.add(new SimpleGrantedAuthority(userRole.getCode().toUpperCase()));
        }

        return authorities;
    }
}
