package de.sgs.secrets.repositories;

import de.sgs.secrets.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language findByKeyAndLocale(String key, String locale);
}
