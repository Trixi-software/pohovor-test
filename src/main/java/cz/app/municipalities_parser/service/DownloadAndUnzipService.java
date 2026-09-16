package cz.app.municipalities_parser.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface DownloadAndUnzipService {
    public void download(String url, String file) throws IOException, URISyntaxException;
    public void unpackZIP(String archive, String resultFolder) throws IOException;
}
