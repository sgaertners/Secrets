package de.sgs.secrets.controller;

import de.sgs.secrets.enums.ImageKind;
import de.sgs.secrets.knowhow.services.KnowhowService;
import de.sgs.secrets.services.AppsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {

    final AppsService appsService;
    final KnowhowService knowhowService;

    public ImageController(AppsService appsService, KnowhowService knowhowService) {
        this.appsService = appsService;
        this.knowhowService = knowhowService;
    }


    @RequestMapping("/dbimage")
    public @ResponseBody byte[] loadImage(@RequestParam Long id, @RequestParam ImageKind kind) {
        switch (kind) {
            case APPS -> {
                return this.appsService.getImageById(id);
            }
            case KNOWHOW -> {
                return this.knowhowService.getImageById(id);
            }
        }
        return null;
    }
}
