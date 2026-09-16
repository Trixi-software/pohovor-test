package cz.app.municipalities_parser.service.impl;

import cz.app.municipalities_parser.model.MunicipalityPart;
import cz.app.municipalities_parser.repository.MunicipalityPartRepository;
import cz.app.municipalities_parser.service.MunicipalityPartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class MunicipalityPartServiceImpl implements MunicipalityPartService {
    private final MunicipalityPartRepository municipalityPartRepository;

    @Override
    @Transactional
    public void saveAll(List<MunicipalityPart> municipalityParts) {
        municipalityPartRepository.saveAll(municipalityParts);
    }
}
