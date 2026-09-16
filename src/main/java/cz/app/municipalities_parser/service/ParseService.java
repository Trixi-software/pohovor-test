package cz.app.municipalities_parser.service;

import cz.app.municipalities_parser.dto.ParseResult;
import cz.app.municipalities_parser.model.Municipality;
import cz.app.municipalities_parser.model.MunicipalityPart;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ParseService {
    public ParseResult parse(File sourceXmlFile) throws IOException, XMLStreamException;
}
