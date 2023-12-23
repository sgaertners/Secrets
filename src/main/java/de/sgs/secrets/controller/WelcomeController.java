package de.sgs.secrets.controller;

import de.sgs.secrets.entities.App;
import de.sgs.secrets.entities.Role;
import de.sgs.secrets.entities.User;
import de.sgs.secrets.services.AppsService;
import de.sgs.secrets.services.CustomUserDetailsService;
import de.sgs.secrets.services.RolesService;
import de.sgs.secrets.tools.HtmlTools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
public class WelcomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);
    private final CustomUserDetailsService userDetailsService;
    private final RolesService rolesService;
    private final AppsService appsService;
    private final HtmlTools htmlTools;

    public WelcomeController(CustomUserDetailsService userDetailsService, RolesService rolesService, AppsService appsService, HtmlTools htmlTools) {
        this.userDetailsService = userDetailsService;
        this.rolesService = rolesService;
        this.appsService = appsService;
        this.htmlTools = htmlTools;
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
        LOGGER.info("LANGUAGE: {}", lang);
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
    public String welcomeUser(@RequestParam String lang, HttpSession httpSession, Model model) {
        LOGGER.info("LANGUAGE: {}", lang);
        httpSession.setAttribute("lang", lang);

        StringBuilder html = new StringBuilder();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            List<String> appRoles = this.appsService.getRoleList();
            String name = authentication.getName();
            User user = userDetailsService.loadUser(name);
            Set<Role> roles = user.getRoles();
            List<String> userRoles = new ArrayList<>();
            for (Role role: roles) {
                userRoles.add(role.getName());
            }
            for (String role: appRoles) {
                if (userRoles.contains(role)) {
                    App app = this.appsService.getAppByRole(role);
                    if (app != null) {
                        html.append(htmlTools.generateCardFromApp(app, lang, "APPS"));
                    }
                }
            }

        }

        model.addAttribute("html", html);
        return "welcomeUser";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @RequestMapping("/logout")
    public void logout() {
        Authentication authentication = SecurityContextHolder.createEmptyContext().getAuthentication();
    }

    @GetMapping("/failure")
    public String failure(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
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