package de.sgs.secrets.whiskey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/whiskey")
public class WhiskeyController {

    @RequestMapping("/show")
    public String showWhiskeys() {
        return "whiskeys";
    }

}
