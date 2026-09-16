package cz.app.municipalities_parser.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "municipalities")
@AllArgsConstructor
@NoArgsConstructor
public class Municipality {
    @Id
    private Long code;

    @Column(nullable = false)
    private String name;

    @Nullable
    @OneToMany(mappedBy = "municipality")
    List<MunicipalityPart> parts = new ArrayList<>();
}
