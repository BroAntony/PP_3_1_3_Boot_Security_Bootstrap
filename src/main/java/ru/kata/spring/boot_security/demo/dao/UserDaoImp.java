package ru.kata.spring.boot_security.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.RoleUser;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers(){
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }
    @Override
    public void addRole(Role role) {
        em.persist(role);
    }

    @Override
    public void deleteUser (int id){
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }
    @Override
    public User findUserById (int id){
        return em.find(User.class, id);
    }

    @Override
    public void updateUser(User user){
        em.merge(user);
    }

    @Override
    public void deleteAllUsers() {
        em.createQuery("DELETE FROM User").executeUpdate();
        em.createNativeQuery("ALTER TABLE aa_user_db.users_mvc AUTO_INCREMENT = 1").executeUpdate();
    }
    @Override
    public void deleteAllRoles() {
        em.createQuery("DELETE FROM Role ").executeUpdate();
        em.createNativeQuery("ALTER TABLE aa_user_db.roles AUTO_INCREMENT = 1").executeUpdate();
    }

    @Override
    public Optional<User> findUserByName(String userName) {
        User user = em.createQuery("SELECT u FROM User u WHERE u.email = :username", User.class)
                .setParameter("username", userName)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        return Optional.ofNullable(user);
    }
    @Override
    public Optional<Role> findRoleByName(String roleName) {
        RoleUser roleUser;
        try {
            roleUser = RoleUser.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        Role role = em.createQuery("SELECT r FROM Role r WHERE r.roleUser = :roleUser", Role.class)
                .setParameter("roleUser", roleUser)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        return Optional.ofNullable(role);
    }
}
