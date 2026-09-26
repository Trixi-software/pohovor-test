package cz.app.municipalities_parser.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "municipality_parts")
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalityPart {
    @Id
    private Long code;

    @Column(nullable = false)
    private String name;

    // TODO poznamka
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;
}
