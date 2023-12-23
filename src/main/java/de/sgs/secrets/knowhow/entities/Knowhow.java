package de.sgs.secrets.knowhow.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.sql.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "knowhow")
public class Knowhow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;
    @Column
    Date date;
    @Column
    private String url;
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "knowhow_categories",
//        joinColumns = @JoinColumn(name = "knowhow_id", referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
//    )
//    private Set<Category> categories;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Blob image;
}
