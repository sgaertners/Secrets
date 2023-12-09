package de.sgs.secrets.repositories;

import de.sgs.secrets.entities.App;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppsRepository extends JpaRepository<App, Long> {
//    Optional<App> findByRole(String role);
}
