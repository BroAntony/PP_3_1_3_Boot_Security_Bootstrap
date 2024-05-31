package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RoleServiceImp implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }
    @Override
    public Role findRoleById (int id) {
        return roleDao.findRoleById(id);
    }
    @Override
    public void deleteRole(int id) {
        roleDao.deleteRole(id);
    }
    @Override
    public void deleteAllRoles() {
        roleDao.deleteAllRoles();
    }

    @Override
    public Optional<Role> findRoleByName(String roleName){
        return roleDao.findRoleByName(roleName);
    }

}
