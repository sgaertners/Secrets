package de.sgs.secrets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class FaviconController {

    @RequestMapping("favicon.ico")
    @ResponseBody
    String favicon() {
        return "forward:/resources/static/favicon.ico";
    }
}