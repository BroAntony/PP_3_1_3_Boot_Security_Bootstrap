package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;


public interface RoleService {

    List<Role> getAllRoles();
    void addRole(Role Role);
    void deleteRole (int id);
    Role findRoleById (int id);
    void deleteAllRoles();
    Optional<Role> findRoleByName(String roleName);
}
