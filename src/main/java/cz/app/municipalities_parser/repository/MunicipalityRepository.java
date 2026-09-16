package cz.app.municipalities_parser.repository;

import cz.app.municipalities_parser.model.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
}
