package com.hexagon.demodrop.configuration;

import com.hexagon.demodrop.model.Role;
import com.hexagon.demodrop.model.User;
import com.hexagon.demodrop.repository.RoleRepository;
import com.hexagon.demodrop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitialDataBaseUsers implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private String adminEmail;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public InitialDataBaseUsers(@Value("${cust.adminEmail}") String adminEmail,
                                UserRepository userRepository,
                                RoleRepository roleRepository) {
        this.adminEmail = adminEmail;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (userRepository.count() > 0) return;
        createRoles();
        List<Role> roles = new ArrayList<>();

        User user = new User(
                adminEmail,
                passwordEncoder.encode("secret"),
                true);
        roles.add(roleRepository.findByName("ROLE_USER"));
        roles.add(roleRepository.findByName("ROLE_BACKOFFICE"));
        roles.add(roleRepository.findByName("ROLE_ADMIN"));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional
    protected void createRoles(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));
        roles.add(new Role("ROLE_BACKOFFICE"));
        roles.add(new Role("ROLE_ADMIN"));
        roleRepository.saveAll(roles);
    }

}