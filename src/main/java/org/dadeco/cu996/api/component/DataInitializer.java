package org.dadeco.cu996.api.component;

import org.dadeco.cu996.api.model.Privilege;
import org.dadeco.cu996.api.model.Role;
import org.dadeco.cu996.api.model.User;
import org.dadeco.cu996.api.repository.PrivilegeRepository;
import org.dadeco.cu996.api.repository.RoleRepository;
import org.dadeco.cu996.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@DependsOn("securityConfig")
public class DataInitializer implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        Set<Privilege> adminPrivileges = new HashSet<Privilege>(Arrays.asList(
                readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", new HashSet<Privilege>(Arrays.asList(readPrivilege)));

        String adminEmail = "test@dadeco.com";
        String adminNtAccount = "test7sgh";

        User admin = userRepository.findByEmail(adminEmail);
        if(admin == null) {

            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            User user = new User();
            user.setName("Test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail(adminEmail);
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            user.setRoles(roles);
            user.setNtAccount(adminNtAccount);
            userRepository.save(user);
        }

        alreadySetup = true;
    }

    @Transactional
    protected Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    protected Role createRoleIfNotFound(
            String name, Set<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
