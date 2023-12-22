package de.sgs.secrets.knowhow.services;

import de.sgs.secrets.knowhow.entities.Knowhow;
import de.sgs.secrets.knowhow.repositories.KnowhowRepository;
import org.springframework.stereotype.Service;

@Service
public class KnowhowService {

    final KnowhowRepository knowhowRepository;

    public KnowhowService(KnowhowRepository knowhowRepository) {
        this.knowhowRepository = knowhowRepository;
    }

    public void save(Knowhow knowhow) {
        knowhowRepository.save(knowhow);
    }

}
