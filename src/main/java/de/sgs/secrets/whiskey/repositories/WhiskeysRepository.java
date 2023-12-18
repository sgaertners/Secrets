package de.sgs.secrets.whiskey.repositories;

import de.sgs.secrets.whiskey.entities.Whiskey;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

//@Cacheable("whiskey")
public interface WhiskeysRepository extends JpaRepository<Whiskey, Long> {

}
