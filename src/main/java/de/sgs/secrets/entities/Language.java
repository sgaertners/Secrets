package de.sgs.secrets.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;

@Data
@Entity
@Table(name = "languages")
@Cacheable("language")
public class Language {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column
    @Order(1)
    private Integer id;
    @Column
    @Order(2)
    private String locale;
    @Order(3)
    @Column(name = "messagekey")
    private String key;
    @Order(4)
    @Column(name = "messagecontent")
    private String content;

}