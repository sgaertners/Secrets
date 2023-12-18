package de.sgs.secrets.repositories;

import de.sgs.secrets.entities.Language;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Cacheable("language")
@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language findByKeyAndLocale(String key, String locale);
}
