package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.RoleUser;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
		UserServiceImp userService = context.getBean(UserServiceImp.class);
		RoleServiceImp roleService = context.getBean(RoleServiceImp.class);

		Role roleUser = new Role(RoleUser.USER);
		Role roleAdmin = new Role(RoleUser.ADMIN);
		roleService.deleteAllRoles();
		roleService.addRole(roleAdmin);
		roleService.addRole(roleUser);
		Set<Role> rolesAdminAndUser = new HashSet<>();
		Set<Role> rolesUser = new HashSet<>();
		rolesAdminAndUser.add(roleUser);
		rolesAdminAndUser.add(roleAdmin);
		rolesUser.add(roleUser);

		User user1 = new User("Иван","Петров",30,"user1@mail.ru", rolesUser, "user");
		User user2 = new User("Коля","Сидоров",31,"admin@mail.ru", rolesAdminAndUser, "admin");
		User user3 = new User("Петя","Иванов",32,"user2@mail.ru", rolesUser, "user");
		userService.deleteAllUsers();
		userService.addUser(user1);
		userService.addUser(user2);
		userService.addUser(user3);
	}

}
