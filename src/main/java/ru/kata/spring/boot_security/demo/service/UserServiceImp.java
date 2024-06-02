package ru.kata.spring.boot_security.demo.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Transactional
@Service
public class UserServiceImp implements UserService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder,RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void addUser(User user) {
        Set<Role> roleSet = new HashSet<>();
        if (user.getRoles() != null) {
            for (Role roleUser : user.getRoles()) {
                Optional<Role> role = roleService.findRoleByName(roleUser.getRoleName());
                role.ifPresent(roleSet::add);
            }
        } else {
            Optional<Role> role = roleService.findRoleByName("USER");
            role.ifPresent(roleSet::add);
        }
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }
    @Override
    public User findUserById (int id) {
        return userDao.findUserById(id);
    }
    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        if (user.getRoles() != null) {
            Set<Role> roles = user.getRoles();
            Set<Role> newRoles = new HashSet<>();
            for (Role role : roles) {
                roleService.findRoleByName(role.getRoleName()).ifPresent(newRoles::add);
            }
            user.setRoles(newRoles);
        } else {
            Set<Role> roleSet = new HashSet<>();
            Optional<Role> role = roleService.findRoleByName("USER");
            role.ifPresent(roleSet::add);
            user.setRoles(roleSet);
        }
        if(user.getPassword().isEmpty()) {
            user.setPassword(userDao.findUserById(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }
    @Override
    public void deleteAllUsers() {
        userDao.deleteAllUsers();
    }
}
