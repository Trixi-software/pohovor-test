package cz.app.municipalities_parser.repository;

import cz.app.municipalities_parser.model.MunicipalityPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityPartRepository extends JpaRepository<MunicipalityPart, Long> {
}
