package de.sgs.secrets.controller;

import de.sgs.secrets.entities.Role;
import de.sgs.secrets.entities.User;
import de.sgs.secrets.services.CustomUserDetailsService;
import de.sgs.secrets.services.RolesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static java.lang.System.out;

@Controller
public class WelcomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);
    private final CustomUserDetailsService userDetailsService;
    private final RolesService rolesService;

    public WelcomeController(CustomUserDetailsService userDetailsService, RolesService rolesService) {
        this.userDetailsService = userDetailsService;
        this.rolesService = rolesService;
    }

    @GetMapping(value={"/welcome", "/index", "/"})
    public String index(Locale locale, HttpSession httpSession, HttpServletRequest request, Model model) {
        try {
            FileSystemUtils.deleteRecursively(Paths.get("./work"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("LANGUAGE: Welcome={}", locale.getLanguage());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            UserDetails userDetails = userDetailsService.loadUserByUsername(name);
            out.println(userDetails.isEnabled());
            httpSession.setAttribute("userdetails", userDetails);

            if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return "redirect:welcome/admin?lang=" + locale.getLanguage();
            }
            if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
                return "redirect:welcome/user?lang=" + locale.getLanguage();
            }
        }
        model.addAttribute("ip", request.getRemoteAddr());
        return "welcome";
    }

    @GetMapping("/welcome/admin")
    public String welcomeAdmin(@RequestParam String lang, HttpSession httpSession, Model model) {
        LOGGER.info("LANGUAGE: Admin={}", lang);
        httpSession.setAttribute("lang", lang);
        model.addAttribute("lang", lang);
        List<User> userList = userDetailsService.loadAlluser();
        model.addAttribute("users", userList);

        StringBuilder rollen = new StringBuilder();
        List<String> rolesPerUser = new ArrayList<>();
        for(User user: userList) {
            Set<Role> roles = user.getRoles();
            for(Role role: roles) {
                rollen.append(role.getName()).append(", ");
            }
            rolesPerUser.add(rollen.substring(0, rollen.length()-2));
            rollen.setLength(0);
        }
        model.addAttribute("userroles", rolesPerUser);

        return "welcomeAdmin";
    }

    @GetMapping("/welcome/user")
    public String welcomeUser(@RequestParam String lang, HttpSession httpSession) {
        LOGGER.info("LANGUAGE: User={}", lang);
        httpSession.setAttribute("lang", lang);

        return "welcomeUser";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/failure")
    public String failure(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            out.println(name);
        }

        return "welcome";
    }

    @GetMapping("/rolesfromuser")
    public String rolesFromUser(@RequestParam(required = false) Long id, @RequestParam String lang, Model model, HttpSession httpSession) {
        if (id == null) {
            id = (Long) httpSession.getAttribute("id");
        }

        User user = userDetailsService.loadUserById(id);
        httpSession.setAttribute("id", id);
        model.addAttribute("lang", lang);
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("name", user.getName());

        List<Role> allRoles = rolesService.getAllRoles();
        model.addAttribute("allroles", allRoles);

        return "rolesfromuser";
    }

    @RequestMapping(value="/updateuserroles", method = RequestMethod.POST)
    public String updateUserRoles(@RequestParam final MultiValueMap<String, Boolean> data, HttpSession httpSession) {
        Long id = (Long) httpSession.getAttribute("id");
        List<Role> allRoles = rolesService.getAllRoles();
        userDetailsService.updateUserRoles(id, data, allRoles);
        return "redirect:welcome";
    }

}