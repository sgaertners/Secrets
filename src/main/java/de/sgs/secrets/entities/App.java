package de.sgs.secrets.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cache.annotation.Cacheable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apps")
@Cacheable("apps")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String role;
    @Column(nullable = false, unique = true)
    private String endpoint;
    @Lob
    private byte[] image;
    @Column(nullable = false)
    private String headline;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String button;

}
