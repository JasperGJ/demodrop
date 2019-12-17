package com.hexagon.demodrop.service;

import com.hexagon.demodrop.model.Role;
import com.hexagon.demodrop.model.Template;
import com.hexagon.demodrop.model.User;
import com.hexagon.demodrop.repository.RoleRepository;
import com.hexagon.demodrop.repository.TemplateRepository;
import com.hexagon.demodrop.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TemplateRepository templateRepository;
    private EmailService emailService;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository, TemplateRepository templateRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.templateRepository = templateRepository;
        this.emailService = emailService;
    }

    public boolean createEmployee(String email,String password) {
        User user = userRepository.findByEmail(email);
        if(user != null) return false;
        user = new User(email, passwordEncoder.encode(password), true);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        roles.add(roleRepository.findByName("ROLE_BACKOFFICE"));
        user.setRoles(roles);
        userRepository.save(user);

        emailService.sendMail("Welcome to Hexagon",
                "You can login and go directly to your Inbox with demos",
        user.getEmail());

        return true;
     }

     public boolean saveTemplate(long id, String comment, String status) throws NotFoundException {
         Template template = (id == 0)
                 ? new Template()
                 : templateRepository.findById(id).orElseThrow(() -> new NotFoundException("No template found with id " + id));
                 template.setComment(comment);
                 template.setStatus(status);
                 templateRepository.save(template);
                 return true;
     }

     public boolean deleteTemplate(long id) throws NotFoundException {
        Template template = templateRepository.findById(id).orElseThrow(() -> new NotFoundException("No template found with id " + id));
        templateRepository.deleteById(id);
        return true;
     }
}
