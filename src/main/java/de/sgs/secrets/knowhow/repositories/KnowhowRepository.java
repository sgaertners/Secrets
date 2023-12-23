package de.sgs.secrets.knowhow.repositories;

import de.sgs.secrets.knowhow.entities.Knowhow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KnowhowRepository extends JpaRepository<Knowhow, Long> {

    // JPA doesn't support Fulltext-Search with MySQL, only a native Query will do that.
    @Query(value = "SELECT * FROM knowhow WHERE MATCH (title, description) AGAINST (:keyword IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<Knowhow> fullTextSearch(String keyword);

}
