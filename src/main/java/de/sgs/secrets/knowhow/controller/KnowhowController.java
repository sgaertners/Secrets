package de.sgs.secrets.knowhow.controller;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.ScreenshotType;
import de.sgs.secrets.entities.User;
import de.sgs.secrets.knowhow.entities.Knowhow;
import de.sgs.secrets.knowhow.entities.Search;
import de.sgs.secrets.knowhow.services.KnowhowService;
import de.sgs.secrets.services.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/knowhow")
public class KnowhowController {

    final CustomUserDetailsService userDetailsService;
    final KnowhowService knowhowService;

    public KnowhowController(CustomUserDetailsService userDetailsService, KnowhowService knowhowService) {
        this.userDetailsService = userDetailsService;
        this.knowhowService = knowhowService;
    }


    @GetMapping("/show")
    public String showKnowHow(Model model) {
        if (checkUserHasNotRole("KNOWHOW")) {
            return "redirect:/welcome";
        }

        Knowhow knowhow = (Knowhow) model.getAttribute("knowhow");
        if (knowhow == null) {
            knowhow = new Knowhow();
        }

        model.addAttribute("knowhow", knowhow);

        return "knowhow";
    }


    @GetMapping("/list")
    public String listKnowHow(Model model) {
        if (checkUserHasNotRole("KNOWHOW")) {
            return "redirect:/welcome";
        }

        Knowhow knowhow = (Knowhow) model.getAttribute("knowhow");
        if (knowhow == null) {
            knowhow = new Knowhow();
        }

        model.addAttribute("knowhow", knowhow);

        return "knowhowsearch";
    }

    @RequestMapping("/search")
    public String searchKnowHow(@Valid @ModelAttribute("search") Search search, Model model) {
        List<Knowhow> results = new ArrayList<>();
        if (checkUserHasNotRole("KNOWHOW")) {
            return "redirect:/knowhow";
        }

        search = (Search) model.getAttribute("search");
        if (search == null) {
            search = new Search();
        }

        if (search.getToSearch() != null && !search.getToSearch().isEmpty()) {
            results = knowhowService.findKnowHow(search.getToSearch());
        }

        model.addAttribute("search", search);
        model.addAttribute("searchresults", results);

        return "knowhowsearch";
    }


    @PostMapping("/upload")
    public String saveKnowhow(@Valid @ModelAttribute("knowhow") Knowhow knowhow, Model model) {
        if (checkUserHasNotRole("KNOWHOW")) {
            return "redirect:/welcome";
        }

        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        knowhow.setDate(date);
        Document doc = null;
        try {
            doc = Jsoup.connect(knowhow.getUrl()).get();
            String text = doc.body().text();
            String title = doc.title();
            knowhow.setDescription("<pre>" + text + "</pre>");
            if (!title.isEmpty()) {
                knowhow.setTitle(title);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate(knowhow.getUrl());
            Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions()
                .setFullPage(true)
                .setType(ScreenshotType.PNG);
            byte[] screenshot = page.screenshot(screenshotOptions);
            Blob blob = new SerialBlob(screenshot);
            knowhow.setImage(blob);
        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("knowhow", knowhow);

        knowhowService.save(knowhow);
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
