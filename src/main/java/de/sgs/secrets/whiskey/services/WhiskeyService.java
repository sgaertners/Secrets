package de.sgs.secrets.whiskey.services;

import de.sgs.secrets.whiskey.entities.Whiskey;
import de.sgs.secrets.whiskey.repositories.WhiskeysRepository;
import org.springframework.stereotype.Service;

@Service
public class WhiskeyService {

    final WhiskeysRepository whiskeysRepository;


    public WhiskeyService(WhiskeysRepository whiskeysRepository) {
        this.whiskeysRepository = whiskeysRepository;
    }

    public void save(Whiskey whiskey) {
        this.whiskeysRepository.save(whiskey);
    }

}
