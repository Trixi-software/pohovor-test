package cz.app.municipalities_parser.service.impl;

import cz.app.municipalities_parser.model.Municipality;
import cz.app.municipalities_parser.repository.MunicipalityRepository;
import cz.app.municipalities_parser.service.MunicipalityService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class MunicipalityServiceImpl implements MunicipalityService {
    private final MunicipalityRepository municipalityRepository;

    @Override
    @Transactional
    public void saveAll(List<Municipality> municipalities) {
        municipalityRepository.saveAll(municipalities);
    }
}
