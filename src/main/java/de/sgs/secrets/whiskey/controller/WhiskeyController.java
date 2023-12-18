package de.sgs.secrets.whiskey.controller;

import de.sgs.secrets.component.OPdf;
import de.sgs.secrets.entities.User;
import de.sgs.secrets.services.CustomUserDetailsService;
import de.sgs.secrets.whiskey.entities.Whiskey;
import de.sgs.secrets.whiskey.services.WhiskeyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/whiskey")
public class WhiskeyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhiskeyController.class);

    final private WhiskeyService whiskeyService;
    final private CustomUserDetailsService userDetailsService;
    final private OPdf oPdf;

    public WhiskeyController(WhiskeyService whiskeyService, CustomUserDetailsService userDetailsService, OPdf oPdf) {
        this.whiskeyService = whiskeyService;
        this.userDetailsService = userDetailsService;
        this.oPdf = oPdf;
    }


    @GetMapping("/show")
    public String showWhiskeys(Model model) {
        if (checkUserHasNotRole("WHISKEY")) {
            return "redirect:/welcome";
        }

        Whiskey whiskey = (Whiskey) model.getAttribute("whiskey");
        if (whiskey == null) {
            whiskey = new Whiskey();
        }
        model.addAttribute("whiskey", whiskey);
        return "whiskeys";
    }


    @PostMapping("/upload")
    public String saveWhiskey(@Valid @ModelAttribute("whiskey") Whiskey whiskey, @RequestParam(value = "file", required = false) MultipartFile file, Model model) {
        if (checkUserHasNotRole("WHISKEY")) {
            return "redirect:/welcome";
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            User user = userDetailsService.loadUser(name);
            whiskey.setUserID(user.getId());
            whiskey.setUserNAME(user.getName());
        }

        whiskeyService.save(whiskey);
        return "redirect:/whiskey/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        if (checkUserHasNotRole("WHISKEY")) {
            return "redirect:/welcome";
        }
        List<Whiskey> whiskeys = whiskeyService.loadAll();
        model.addAttribute("whiskeys", whiskeys);

        return "whiskeylist";
    }

    @GetMapping(value="/print")
    public ResponseEntity<Resource> print() throws URISyntaxException {
        if (checkUserHasNotRole("WHISKEY")) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .location(new URI("/welcome"))
                    .body(null);
        } else {
            byte[] pdf = createPDF();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT");
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type");
            headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            headers.add(HttpHeaders.PRAGMA, "no-cache");
            headers.add(HttpHeaders.EXPIRES, "0");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=whiskeys.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(pdf.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(new ByteArrayInputStream(pdf)));
        }
    }


    @GetMapping(value="/deletewhiskey")
    public String deleteWhiskey(@RequestParam Long id) {
        whiskeyService.deleteWhiskey(id);
        return "redirect:/whiskey/list";
    }

    @GetMapping(value="/editwhiskey")
    public String editWhiskey(@RequestParam Long id, Model model) {
        Whiskey whiskey = (Whiskey) model.getAttribute("whiskey");
        if (whiskey == null) {
            whiskey = whiskeyService.loadById(id);
        }
        model.addAttribute("whiskey", whiskey);
        return "whiskeyschange";
    }

    @RequestMapping("/update")
    public String update(Model model) {
        Whiskey whiskey = (Whiskey) model.getAttribute("whiskey");
        whiskeyService.update(whiskey);
        model.addAttribute("whiskey", whiskey);
        return "redirect:/whiskey/list";
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


    public byte[] createPDF() {
        oPdf.createDocument();
        oPdf.writeTitle("Whiskeys");
        oPdf.writeEmptyLine();
        List<Whiskey> whiskeyList = whiskeyService.loadAll();
        for(Whiskey whiskey: whiskeyList) {
            StringBuilder sb = new StringBuilder();
            oPdf.greyBar(whiskey.getName());
            if (whiskey.getAge() > 0) {
                sb.append(whiskey.getAge()).append(" Jahre, ");
            }
            if (whiskey.getVol() > 0) {
                sb.append(whiskey.getAge()).append("%, ");
            }
            if (whiskey.getSize() > 0) {
                sb.append(whiskey.getSize()).append("l, ");
            }
            if (!whiskey.getTyp().isEmpty()) {
                sb.append(whiskey.getTyp()).append(", ");
            }
            if (!whiskey.getBarrel().isEmpty()) {
                sb.append(whiskey.getBarrel());
                oPdf.writeEmptyLine();
            }

            oPdf.writeNormal14Text(sb.toString());

            if (!whiskey.getDestillery().isEmpty()) {
                oPdf.writeBold16Text("Destillerie: ");
                oPdf.writeNormal14Text(whiskey.getDestillery() + ", ");
                oPdf.writeNormal14Text(whiskey.getTown() + ", ");
                oPdf.writeNormal14Text(whiskey.getCountry());
                oPdf.writeEmptyLine();
            }

            if (!whiskey.getTaste().isEmpty()) {
                oPdf.writeBold16Text("Geschmack: ");
                oPdf.writeNormal14Text(whiskey.getTaste());
                oPdf.writeEmptyLine();
            }

            if (whiskey.getImage() != null) {
                try {
                    Blob blob = whiskey.getImage();
                    byte[] bytes = blob.getBytes(1l, (int) blob.length());
                    oPdf.image(bytes);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return oPdf.getPdf();
    }

}
