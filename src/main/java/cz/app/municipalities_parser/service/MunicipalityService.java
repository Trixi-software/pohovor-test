package cz.app.municipalities_parser.service;

import cz.app.municipalities_parser.model.Municipality;

import java.util.List;

public interface MunicipalityService {
    public void saveAll(List<Municipality> municipalities);
}
