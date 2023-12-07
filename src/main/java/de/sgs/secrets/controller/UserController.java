package de.sgs.secrets.controller;

import de.sgs.secrets.entities.Role;
import de.sgs.secrets.entities.User;
import de.sgs.secrets.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    final CustomUserDetailsService userDetailsService;

    public UserController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/user")
    public String showUser(@Valid @ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        LOGGER.info("USER: SHOW");
        String lang = (String) httpSession.getAttribute("lang");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User loggedInUser = userDetailsService.loadUser(userName);
        model.addAttribute("user", loggedInUser);
        return "user";
    }

    @PostMapping("/user")
    public String updateUser(@Valid @ModelAttribute("user") User user, Model model, HttpServletRequest request, HttpSession httpSession) {
        LOGGER.info("USER: SAVE '{}'.", user.getName());
        String lang = (String) httpSession.getAttribute("lang");
        User loggedInUser = (User) model.getAttribute("user");
        userDetailsService.updateUser(loggedInUser);
        model.addAttribute("user", loggedInUser);
        if (request.isUserInRole("ROLE_USER")) {
            return "redirect:welcome/user?lang=" + lang;
        }
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:welcome/admin?lang=" + lang;
        }
        return "welcome";
    }

    @RequestMapping("/togglelockuser")
    public String lockuser(@RequestParam Long id, HttpSession httpSession) {
        String lang = (String) httpSession.getAttribute("lang");
        User user = userDetailsService.loadUserById(id);
        user.setEnabled(!user.isEnabled());
        return "redirect:welcome/admin?lang=" + lang;
    }


    @RequestMapping("/deleteuser")
    public String deleteUser(@RequestParam Long id, HttpSession httpSession) {
        String lang = (String) httpSession.getAttribute("lang");
        User user = userDetailsService.loadUserById(id);

        Set<Role> roles = user.getRoles();
        for(Role role: roles) {
            user.getRoles().remove(role);
        }

        userDetailsService.deleteUser(user.getId());
        LOGGER.info("DELETE USER: {}", id);
        return "redirect:welcome/admin?lang=" + lang;
    }

}