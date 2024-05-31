package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleUser roleUser;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


    public Role() {
    }

    public Role(String roleName) {
        this.roleUser = RoleUser.valueOf(roleName);
    }

    public Role(RoleUser roleUser) {
        this.roleUser = roleUser;
    }



    @Override
    public String getAuthority() {
        return "ROLE_" + roleUser.name();
    }

    public String getRoleName() {
        return roleUser.name();
    }
    public RoleUser getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(RoleUser roleUser) {
        this.roleUser = roleUser;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleUser=" + roleUser +
                '}';
    }
}
