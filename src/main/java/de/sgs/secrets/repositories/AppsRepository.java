package de.sgs.secrets.repositories;

import de.sgs.secrets.entities.App;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Cacheable("apps")
public interface AppsRepository extends JpaRepository<App, Long> {

}
