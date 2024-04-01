package ru.kata.spring.boot_security.demo.services;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Set;


public interface UserService {

    User findByUsername (String name);
    void addUser(User user, Set<Role> rolesSet);
    void removeUser(long id);
    List<User> userList();
    User getOneUser(long id);
    void updateUser(User user, Set<Role> roles);
    List<Role> getAllRoles();
    Set<Role> getRolesByArrayIds(Long[] idRoles);
}
