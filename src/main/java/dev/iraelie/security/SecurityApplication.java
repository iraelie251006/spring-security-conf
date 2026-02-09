package dev.iraelie.security;

import dev.iraelie.security.role.Role;
import dev.iraelie.security.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
			if (userRole.isEmpty()) {
				Role role = new Role();
				role.setName("ROLE_USER");
				role.setCreatedBy("APP");
				roleRepository.save(role);
			}
		};
	}
}
