package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;


public interface UserDao {

    List<User> getAllUsers();
    void addUser(User user);
    void addRole(Role role);
    void deleteUser (int id);
    User findUserById (int id);
    void updateUser(User user);
    void deleteAllUsers();
    void deleteAllRoles();
    Optional<User> findUserByName(String userName);
    Optional<Role> findRoleByName(String roleName);
}
