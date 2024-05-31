package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;


public interface RoleDao {

    List<Role> getAllRoles();
    void addRole(Role Role);
    void deleteRole (int id);
    Role findRoleById (int id);
    void deleteAllRoles();
    Optional<Role> findRoleByName(String roleName);
}
