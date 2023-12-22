package de.sgs.secrets.whiskey.repositories;

import de.sgs.secrets.whiskey.entities.Whiskey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhiskeysRepository extends JpaRepository<Whiskey, Long> {

}
