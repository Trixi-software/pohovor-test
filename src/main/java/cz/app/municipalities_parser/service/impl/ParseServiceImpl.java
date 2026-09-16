package cz.app.municipalities_parser.service.impl;

import cz.app.municipalities_parser.dto.ParseResult;
import cz.app.municipalities_parser.model.Municipality;
import cz.app.municipalities_parser.model.MunicipalityPart;
import cz.app.municipalities_parser.service.ParseService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@Service
@Primary
@AllArgsConstructor
public class ParseServiceImpl implements ParseService {
    @Override
    public ParseResult parse(File sourceXmlFile) throws IOException, XMLStreamException {
        ParseResult result = new ParseResult(new ArrayList<>(), new ArrayList<>());

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        FileInputStream fis = new FileInputStream(sourceXmlFile);
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(fis);
        try {
            Municipality municipality = new Municipality();
            MunicipalityPart municipalityPart = new MunicipalityPart();

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    if (startElement.getName().getPrefix().equals("vf")
                            && startElement.getName().getLocalPart().equals("Obce")) {
                        while (reader.hasNext()) {
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isStartElement()) {
                                startElement = nextEvent.asStartElement();
                                switch (startElement.getName().getLocalPart()) {
                                    case "Obec":
                                        if (startElement.getName().getPrefix().equals("vf")) {
                                            municipality = new Municipality();
                                        }
                                        break;
                                    case "Kod":
                                        if (startElement.getName().getPrefix().equals("obi")) {
                                            municipality.setCode(Long.valueOf(reader.getElementText()));
                                        }
                                        break;
                                    case "Nazev":
                                        if (startElement.getName().getPrefix().equals("obi")) {
                                            municipality.setName(reader.getElementText());
                                        }
                                        break;
                                }
                            }
                            if (nextEvent.isEndElement()) {
                                EndElement endElement = nextEvent.asEndElement();
                                if (endElement.getName().getPrefix().equals("vf")
                                        && endElement.getName().getLocalPart().equals("Obec")) {
                                    result.municipalities().add(municipality);
                                }
                                if (endElement.getName().getPrefix().equals("vf")
                                        && endElement.getName().getLocalPart().equals("Obce")) {
                                    break;
                                }
                            }
                        }
                    }
                    if (startElement.getName().getPrefix().equals("vf")
                            && startElement.getName().getLocalPart().equals("CastiObci")) {
                        while (reader.hasNext()) {
                            nextEvent = reader.nextEvent();
                            if (nextEvent.isStartElement()) {
                                startElement = nextEvent.asStartElement();
                                switch (startElement.getName().getLocalPart()) {
                                    case "CastObce":
                                        if (startElement.getName().getPrefix().equals("vf")) {
                                            municipalityPart = new MunicipalityPart();
                                        }
                                        break;
                                    case "Kod":
                                        if (startElement.getName().getPrefix().equals("coi")) {
                                            municipalityPart.setCode(Long.valueOf(reader.getElementText()));
                                        }
                                        if (startElement.getName().getPrefix().equals("obi")) {
                                            Municipality stub = new Municipality();
                                            stub.setCode(Long.valueOf(reader.getElementText()));
                                            municipalityPart.setMunicipality(stub);
                                        }
                                        break;
                                    case "Nazev":
                                        if (startElement.getName().getPrefix().equals("coi")) {
                                            municipalityPart.setName(reader.getElementText());
                                        }
                                        break;
                                }
                            }
                            if (nextEvent.isEndElement()) {
                                EndElement endElement = nextEvent.asEndElement();
                                if (endElement.getName().getPrefix().equals("vf")
                                        && endElement.getName().getLocalPart().equals("CastObce")) {
                                    result.municipalityParts().add(municipalityPart);
                                }
                                if (endElement.getName().getPrefix().equals("vf")
                                        && endElement.getName().getLocalPart().equals("CastiObci")) {
                                    break;
                                }
                            }
                        }
                    }
                }
                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if (endElement.getName().getPrefix().equals("vf")
                            && endElement.getName().getLocalPart().equals("CastiObci")) {
                        break;
                    }
                }
            }
        } finally {
            fis.close();
            reader.close();
        }


        return result;
    }
}