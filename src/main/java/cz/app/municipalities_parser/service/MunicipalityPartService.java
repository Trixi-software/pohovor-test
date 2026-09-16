package cz.app.municipalities_parser.service;

import cz.app.municipalities_parser.model.Municipality;
import cz.app.municipalities_parser.model.MunicipalityPart;

import java.util.List;

public interface MunicipalityPartService {
    public void saveAll(List<MunicipalityPart> municipalityParts);
}
