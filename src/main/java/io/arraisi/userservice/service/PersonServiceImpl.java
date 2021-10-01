package io.arraisi.userservice.service;

import io.arraisi.userservice.model.Person;
import io.arraisi.userservice.model.Role;
import io.arraisi.userservice.repository.PersonRepository;
import io.arraisi.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl implements PersonService, UserDetailsService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username);
        if (person == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(person.getUsername(), person.getPassword(), authorities);
    }

    @Override
    public Person savePerson(Person person) {
        log.info("Saving new person {} to the database", person.getName());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToPerson(String username, String roleName) {
        log.info("Adding role {} to person {}", roleName, username);
        Person person = personRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        person.getRoles().add(role);
    }

    @Override
    public Person getPerson(String username) {
        log.info("Fetching person {}", username);
        return personRepository.findByUsername(username);
    }

    @Override
    public List<Person> getPersons() {
        log.info("Fetching all persons");
        return personRepository.findAll();
    }

    public void deleteExampleData() {
        personRepository.deleteAll();
        roleRepository.deleteAll();
    }
}
