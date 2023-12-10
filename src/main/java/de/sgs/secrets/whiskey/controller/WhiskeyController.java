package de.sgs.secrets.whiskey.controller;

import de.sgs.secrets.SecretsApplication;
import de.sgs.secrets.services.CustomUserDetailsService;
import de.sgs.secrets.whiskey.entities.Whiskey;
import de.sgs.secrets.whiskey.services.WhiskeyService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;

@Controller
@RequestMapping("/whiskey")
public class WhiskeyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhiskeyController.class);

    final private WhiskeyService whiskeyService;
    final private CustomUserDetailsService userDetailsService;

    public WhiskeyController(WhiskeyService whiskeyService, CustomUserDetailsService userDetailsService) {
        this.whiskeyService = whiskeyService;
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/show")
    public String showWhiskeys(Model model, HttpSession httpSession) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            UserDetails userDetails = userDetailsService.loadUserByUsername(name);
            httpSession.setAttribute("userdetails", userDetails);

            if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("WHISKEY"))) {
                return "redirect:/welcome";
            }
        }

        Whiskey whiskey = (Whiskey) model.getAttribute("whiskey");
        if (whiskey == null) {
            whiskey = new Whiskey();
        }
        model.addAttribute("whiskey", whiskey);
        return "whiskeys";
    }


    @PostMapping("/upload")
    public String saveWhiskey(@Valid @ModelAttribute("whiskey") Whiskey whiskey, @RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes, Model model, HttpSession httpSession) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            UserDetails userDetails = userDetailsService.loadUserByUsername(name);
            httpSession.setAttribute("userdetails", userDetails);

            if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("WHISKEY"))) {
                return "redirect:/welcome";
            }
        }

        model.addAttribute("whiskey", whiskey);
        if (file != null) {
            whiskey.setImagename(file.getOriginalFilename());
            try {
                byte[] bytes = file.getBytes();
                Blob blob = new SerialBlob(bytes);
                whiskey.setImage(blob);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), "ERROR saving image {}!", file.getOriginalFilename());
            }
        }

        whiskeyService.save(whiskey);
        return "redirect:/whiskey/show";
    }

    @GetMapping("/list")
    public String list(HttpSession httpSession) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            UserDetails userDetails = userDetailsService.loadUserByUsername(name);
            httpSession.setAttribute("userdetails", userDetails);

            if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("WHISKEY"))) {
                return "redirect:/welcome";
            }
        }

        return "whiskeylist";
    }

}
