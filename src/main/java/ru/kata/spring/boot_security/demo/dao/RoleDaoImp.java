package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.RoleUser;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public class RoleDaoImp implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> getAllRoles(){
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public void addRole(Role role) {
        em.persist(role);
    }
    @Override
    public void deleteRole (int id){
        Role role = em.find(Role.class, id);
        if (role != null) {
            em.remove(role);
        }
    }
    @Override
    public Role findRoleById (int id){
        return em.find(Role.class, id);
    }
    @Override
    public void deleteAllRoles() {
        em.createQuery("DELETE FROM Role ").executeUpdate();
        em.createNativeQuery("ALTER TABLE aa_user_db.roles AUTO_INCREMENT = 1").executeUpdate();
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
