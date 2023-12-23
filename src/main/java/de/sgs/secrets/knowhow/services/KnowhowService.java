package de.sgs.secrets.knowhow.services;

import de.sgs.secrets.knowhow.entities.Knowhow;
import de.sgs.secrets.knowhow.repositories.KnowhowRepository;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;

@Service
public class KnowhowService {

    final KnowhowRepository knowhowRepository;

    public KnowhowService(KnowhowRepository knowhowRepository) {
        this.knowhowRepository = knowhowRepository;
    }

    public void save(Knowhow knowhow) {
        knowhowRepository.save(knowhow);
    }

    public byte[] getImageById(Long id) {
        Blob blob = knowhowRepository.findById(id).get().getImage();
        byte[] image = null;
        try {
            int len = (int) blob.length();
            image = blob.getBytes(1, len);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return image;
    }


}
