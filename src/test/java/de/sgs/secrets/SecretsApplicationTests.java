package de.sgs.secrets;

import de.sgs.secrets.entities.App;
import de.sgs.secrets.services.AppsService;
import de.sgs.secrets.tools.Helper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecretsApplicationTests {

    @Autowired
    AppsService appsService;


    @Test
    void contextLoads() {
    }


    @Test
    void loadImages() {
        byte[] image = Helper.loadResource("static/images/whiskey.png");
        App app = appsService.getAppByRole("WHISKEY");
        app.setImage(image);
        appsService.saveApp(app);
    }


}
