package de.sgs.secrets.controller;

import de.sgs.secrets.entities.Role;
import de.sgs.secrets.entities.User;
import de.sgs.secrets.services.CustomUserDetailsService;
import de.sgs.secrets.services.RolesService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RegisterController {

    final CustomUserDetailsService userDetailsService;
    final RolesService rolesService;
    final PasswordEncoder passwordEncoder;

    public RegisterController(CustomUserDetailsService userDetailsService, RolesService rolesService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.rolesService = rolesService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login/register")
    public String showRegistration(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }


    @PostMapping("/login/register/save")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if(userDetailsService.findUserByEmail(user.getEmail())) {
            result.rejectValue("email", null,"There is already an account registered with the same email");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "/login/register";
        }

        Role role = rolesService.getRoleByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);


        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDetailsService.saveUser(user);

        return "redirect:/login/register?success";
    }
}
