package de.sgs.secrets.tools;

import de.sgs.secrets.component.DBMessageSource;
import de.sgs.secrets.entities.App;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;

@Component
public class HtmlTools {

    private final DBMessageSource dbMessageSource;

    public HtmlTools(DBMessageSource dbMessageSource) {
        this.dbMessageSource = dbMessageSource;
    }

    public String getMessage(String key, String lang) {
        Locale locale = new Locale(lang, "");
        String text = this.dbMessageSource.resolveCode(key, locale).toPattern();
        return text;
    }

    public String generateCardFromApp(App app, String lang) {
        HashMap<String, String> map = null;
        HashMap<String, String> messageKeys = null;
        String card = "";
        if (app.getImage() != null) {
            card = Helper.loadFragment("card");
            map = Helper.findVariables(card);
            Long img = app.getId();
            map.put("image",String.valueOf(img));
        } else {
            card = Helper.loadFragment("card_noimage");
            map = Helper.findVariables(card);
        }

        map.put("endpoint", app.getEndpoint());
        map.put("headline", getMessage(app.getHeadline(), lang));
        map.put("text", getMessage(app.getText(), lang));
        map.put("button", getMessage(app.getButton(), lang));

        return Helper.resolve(card, map);
    }
}
