package tech.lemnova.continuum_backend.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    /*@OneToMany(
       //mappedBy = "user",
        //cascade = CascadeType.ALL,
        //orphanRemoval = true
    )
    private List<Meta> metas; */
}
