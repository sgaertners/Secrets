package de.sgs.secrets.services;

import de.sgs.secrets.entities.App;
import de.sgs.secrets.entities.Role;
import de.sgs.secrets.repositories.AppsRepository;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppsService {

    final AppsRepository appsRepository;

    public AppsService(AppsRepository appsRepository) {
        this.appsRepository = appsRepository;
    }

    public List<App> getAllApps() {
        return this.appsRepository.findAll();
    }

    public List<String> getRoleList() {
        List<String> roles = new ArrayList<>();
        List<App> apps = this.appsRepository.findAll();
        for(App app: apps) {
            roles.add(app.getRole());
        }
        return roles;
    }

    public App getAppByRole(String role) {
        List<App> apps = getAllApps();
        App result = null;
        for (App app: apps) {
            if (app.getRole().equalsIgnoreCase(role)) {
                result = app;
                break;
            }
        }

        return result;
    }

    public byte[] getImageById(Long id) {
        byte[] image = this.appsRepository.findById(id).get().getImage();
        return image;
    }

    public void saveApp(App app) {
        this.appsRepository.save(app);
    }

}
