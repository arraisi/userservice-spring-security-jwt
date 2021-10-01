package io.arraisi.userservice;

import io.arraisi.userservice.model.Person;
import io.arraisi.userservice.model.Role;
import io.arraisi.userservice.service.PersonService;
import io.arraisi.userservice.service.PersonServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(PersonServiceImpl personService) {
        return args -> {
            personService.deleteExampleData();
            personService.saveRole(new Role(null, "ROLE_USER"));
            personService.saveRole(new Role(null, "ROLE_MANAGER"));
            personService.saveRole(new Role(null, "ROLE_ADMIN"));
            personService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            personService.savePerson(new Person(null, "John Travolta", "john", "1234", new ArrayList<>()));
            personService.savePerson(new Person(null, "Will Smith", "will", "1234", new ArrayList<>()));
            personService.savePerson(new Person(null, "Jim Carry", "jim", "1234", new ArrayList<>()));
            personService.savePerson(new Person(null, "Arnold Schwarzenegger", "arnold", "1234", new ArrayList<>()));

            personService.addRoleToPerson("john", "ROLE_USER");
            personService.addRoleToPerson("john", "ROLE_MANAGER");
            personService.addRoleToPerson("will", "ROLE_MANAGER");
            personService.addRoleToPerson("jim", "ROLE_ADMIN");
            personService.addRoleToPerson("arnold", "ROLE_SUPER_ADMIN");
            personService.addRoleToPerson("arnold", "ROLE_ADMIN");
            personService.addRoleToPerson("arnold", "ROLE_USER");
        };
    }
}
