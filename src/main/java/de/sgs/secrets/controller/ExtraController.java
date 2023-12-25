package de.sgs.secrets.controller;

import de.sgs.secrets.enums.ExtraKind;
import de.sgs.secrets.knowhow.services.KnowhowService;
import de.sgs.secrets.services.AppsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExtraController {

    final AppsService appsService;
    final KnowhowService knowhowService;

    public ExtraController(AppsService appsService, KnowhowService knowhowService) {
        this.appsService = appsService;
        this.knowhowService = knowhowService;
    }


    @RequestMapping("/dbimage")
    public @ResponseBody byte[] loadImage(@RequestParam Long id, @RequestParam ExtraKind kind) {
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

    @RequestMapping("/dbtext")
    public @ResponseBody byte[] loadText(@RequestParam Long id, @RequestParam ExtraKind kind) {
        switch (kind) {
            case APPS -> {

            }
            case KNOWHOW -> {
                return this.knowhowService.getImageById(id);
            }
        }
        return null;
    }
}
