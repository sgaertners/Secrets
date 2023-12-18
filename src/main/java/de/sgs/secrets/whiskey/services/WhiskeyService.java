package de.sgs.secrets.whiskey.services;

import de.sgs.secrets.whiskey.entities.Whiskey;
import de.sgs.secrets.whiskey.repositories.WhiskeysRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WhiskeyService {

    final WhiskeysRepository whiskeysRepository;


    public WhiskeyService(WhiskeysRepository whiskeysRepository) {
        this.whiskeysRepository = whiskeysRepository;
    }

    public void save(Whiskey whiskey) {
        this.whiskeysRepository.save(whiskey);
    }

    public void update(Whiskey whiskey) {
//        Long id = whiskey.getId();
//        this.whiskeysRepository.delete(whiskey);
        if (whiskey != null) {
            this.whiskeysRepository.saveAndFlush(whiskey);
        }
    }

    public List<Whiskey> loadAll() {
        return this.whiskeysRepository.findAll();
    }

    public void deleteWhiskey(Long id) {
        this.whiskeysRepository.deleteById(id);
    }

    public Whiskey loadById(Long id) {
        Whiskey result = null;
        Optional<Whiskey> whiskey = this.whiskeysRepository.findById(id);
        if (whiskey.isPresent()) {
            result = whiskey.get();
        }
        return result;
    }
}
