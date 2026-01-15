package tech.lemnova.continuum_backend.habit;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habits")
public class Habit {

    @Id
    private String id;

    private String name;

    private String description;

    private String category;

    private String icon;

    private String color;

    private Boolean isActive = true;

    @Indexed
    private String userId;

    private Boolean deleted = false;

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();
}
