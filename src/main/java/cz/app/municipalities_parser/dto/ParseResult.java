package cz.app.municipalities_parser.dto;

import cz.app.municipalities_parser.model.Municipality;
import cz.app.municipalities_parser.model.MunicipalityPart;

import java.util.List;

public record ParseResult(
        List<Municipality> municipalities,
        List<MunicipalityPart> municipalityParts
) {
}
