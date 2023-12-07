package de.sgs.secrets.controller;

import de.sgs.secrets.entities.Role;
import de.sgs.secrets.entities.User;
import de.sgs.secrets.services.CustomUserDetailsService;
import de.sgs.secrets.services.RolesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    final CustomUserDetailsService userDetailsService;
    final RolesService rolesService;

    public RoleController(CustomUserDetailsService userDetailsService, RolesService rolesService) {
        this.userDetailsService = userDetailsService;
        this.rolesService = rolesService;
    }

    @GetMapping("/roles")
    public String showRoles(Model model) {
        LOGGER.info("ROLES: SHOW");
        List<Role> roles = this.rolesService.getAllRoles();

        for(Role role : roles) {
            role.setName(role.getName().replace("ROLE_", ""));
        }

        model.addAttribute("roles", roles);
        return "roles";
    }

    @PostMapping("/addrole")
    public String addRole(@RequestParam String role) {
        if (!role.isEmpty()) {
            LOGGER.info("ROLE: SAVE '{}'.", role);
            Role newRole = new Role();
            newRole.setName(role.toUpperCase());
            this.rolesService.save(newRole);
        }
        return "redirect:roles";
    }

    @GetMapping("/deleterole")
    public String deleteRole(@RequestParam String id) {
        LOGGER.info("ROLE: DELETE '{}'.", id);
        Role role = this.rolesService.getRole(Long.parseLong(id));

        List<User>userList = userDetailsService.loadAlluser();
        for(User user: userList) {
            if (user.getRoles().contains(role)) {
                user.getRoles().remove(role);
                userDetailsService.saveUser(user);
            }
        }

        this.rolesService.delete(Long.parseLong(id));

        return "redirect:roles";
    }



}