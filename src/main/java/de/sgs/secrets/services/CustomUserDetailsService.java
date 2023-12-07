package de.sgs.secrets.services;

import de.sgs.secrets.entities.Role;
import de.sgs.secrets.entities.User;
import de.sgs.secrets.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                user.isEnabled(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                authorities);
    }

    public User loadUser(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return user;
    }

    public String getName(String name) {
        String username = "";
        Optional<User> optionalUser = userRepository.findByUsername(name);
        if (optionalUser.isPresent()) {
            username = optionalUser.get().getName();
        }
        return username;
    }

    public String getEMail(String name) {
        String email = "";
        Optional<User> optionalUser = userRepository.findByUsername(name);
        if (optionalUser.isPresent()) {
            email = optionalUser.get().getEmail();
        }
        return email;
    }

    public Set<Role> getRoles(String name) {
        Set<Role> roles = null;
        Optional<User> optionalUser = userRepository.findByUsername(name);
        if (optionalUser.isPresent()) {
            roles = optionalUser.get().getRoles();
        }
        return roles;
    }

    public Set<Role> getRolesById(Long id) {
        Set<Role> roles = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            roles = optionalUser.get().getRoles();
        }
        return roles;
    }

    public void updateUser(User user) {
        String password = user.getPassword();
        Set<Role> roles = getRoles(user.getUsername());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
        userRepository.flush();
    }

    public List<User> loadAlluser() {
        return userRepository.findAll();
    }

    public User loadUserById(Long id) {
        User loaded = new User();
        Optional<User> user =  userRepository.findById(id);

        if (user.isPresent()) {
            loaded = user.get();
        }
        return loaded;
    }

    public String getNameById(Long id) {
        return loadUserById(id).getName();
    }


    public void updateUserRoles(Long id, MultiValueMap<String, Boolean> data, List<Role> allRoles) {
        User user = loadUserById(id);

        Set<Role> roles = new HashSet<>();
        for(String key: data.keySet()) {
            Role role = getRoleByName(key, allRoles);
            roles.add(role);
        }

        user.setRoles(roles);
        userRepository.save(user);
    }

    private Role getRoleByName(String roleName, List<Role> allRoles) {
        Role result = null;
        for(Role item: allRoles) {
            if (item.getName().equals(roleName)) {
                result = item;
                break;
            }
        }
        return result;
    }

    public boolean findUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}