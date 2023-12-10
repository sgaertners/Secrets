package de.sgs.secrets.whiskey.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "whiskeys")
public class Whiskey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private Integer age;
    @Column
    private Float vol;
    @Column
    private Float size;
    @Column
    private String taste;
    @Column
    private String barrel;
    @Column
    private String type;
    @Column
    private String destillery;
    @Column
    private String town;
    @Column
    private String country;
    @Column
    private Blob image;
    @Column
    private String imagename;
    @Column
    private String tastingnote;
    @Column
    private Date tastingdate;

}
