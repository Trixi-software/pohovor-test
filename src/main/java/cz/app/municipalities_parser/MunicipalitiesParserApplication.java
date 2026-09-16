package cz.app.municipalities_parser;

import cz.app.municipalities_parser.dto.ParseResult;
import cz.app.municipalities_parser.service.DownloadAndUnzipService;
import cz.app.municipalities_parser.service.MunicipalityPartService;
import cz.app.municipalities_parser.service.MunicipalityService;
import cz.app.municipalities_parser.service.ParseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class MunicipalitiesParserApplication implements ApplicationRunner {
	private final DownloadAndUnzipService downloadAndUnzipService;
	private final ParseService parseService;
	private final MunicipalityService municipalityService;
	private final MunicipalityPartService municipalityPartService;

	public static void main(String[] args) {
		SpringApplication.run(MunicipalitiesParserApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Path tempDir = Path.of(System.getProperty("java.io.tmpdir"));
		Path archivePath = tempDir.resolve("archieve.zip");
		Path unpackedDir = tempDir.resolve("unpacked");

		FileSystemUtils.deleteRecursively(unpackedDir);

		try {
			log.info("Downloading file");
			downloadAndUnzipService.download("https://www.smartform.cz/download/kopidlno.xml.zip", archivePath.toString());
			log.info("Unzipping file");
			downloadAndUnzipService.unpackZIP(archivePath.toString(), unpackedDir.toString());

			File root = unpackedDir.toFile();
			File[] files = root.listFiles();
			if (files == null || files.length == 0) {
				throw new IllegalStateException("Unpacked folder is empty or not found");
			}
			File XMLFile = files[0];

			log.info("Parsing XML file");
			ParseResult parseResult = parseService.parse(XMLFile);
			log.info("Successfully parsed XML file");

			log.info("Saving data to database");
			municipalityService.saveAll(parseResult.municipalities());
			municipalityPartService.saveAll(parseResult.municipalityParts());
			log.info("Successfully saved data to database");
		} finally {
			Files.deleteIfExists(archivePath);
			FileSystemUtils.deleteRecursively(unpackedDir);
		}
	}
}
