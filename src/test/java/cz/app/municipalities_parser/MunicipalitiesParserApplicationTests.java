package cz.app.municipalities_parser;

import cz.app.municipalities_parser.dto.ParseResult;
import cz.app.municipalities_parser.service.ParseService;
import cz.app.municipalities_parser.service.impl.ParseServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MunicipalitiesParserApplicationTests {
	private final ParseService parseService = new ParseServiceImpl();

	@Test
	void shouldParseXmlCorrectly() throws Exception {
		File file = new File(getClass().getClassLoader().getResource("test.xml").getFile());
		ParseResult result = parseService.parse(file);
		assertEquals(1, result.municipalities().size());
		assertEquals(2, result.municipalityParts().size());
	}

}
