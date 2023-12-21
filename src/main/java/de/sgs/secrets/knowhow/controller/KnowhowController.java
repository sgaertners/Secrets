package de.sgs.secrets.knowhow.controller;

import de.sgs.secrets.entities.User;
import de.sgs.secrets.services.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/knowhow")
public class KnowhowController {

    final CustomUserDetailsService userDetailsService;

    public KnowhowController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/show")
    public String showKnowHow(Model model) {
        if (checkUserHasNotRole("KNOWHOW")) {
            return "redirect:/welcome";
        }

        return "knowhow";
    }


    public boolean checkUserHasNotRole(String role) {
        boolean result = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            User user = userDetailsService.loadUser(name);
            if (user != null && user.getRoles().stream().noneMatch(r -> r.getName().equals(role))) {
                result = true;
            }
        }
        return result;
    }

}
