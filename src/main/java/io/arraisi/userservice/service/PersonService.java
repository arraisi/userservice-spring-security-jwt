package io.arraisi.userservice.service;

import io.arraisi.userservice.model.Person;
import io.arraisi.userservice.model.Role;

import java.util.List;

public interface PersonService {
    Person savePerson(Person person);
    Role saveRole(Role role);
    void addRoleToPerson(String username, String roleName);
    Person getPerson(String username);
    List<Person> getPersons();
}
