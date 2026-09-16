package cz.app.municipalities_parser.service.impl;

import cz.app.municipalities_parser.service.DownloadAndUnzipService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Primary
@AllArgsConstructor
public class DownloadAndUnzipServiceImpl implements DownloadAndUnzipService {
    @Override
    public void download(String url, String file) throws IOException, URISyntaxException {
        URL downloadUrl = new URI(url).toURL();

        try (ReadableByteChannel rbc = Channels.newChannel(downloadUrl.openStream())) {
            File toFile = new File(file);
            File parent = toFile.getParentFile();
            if (!parent.isDirectory() && !parent.mkdirs()) {
                throw new IOException("Failed to create directory " + parent);
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        }
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    @Override
    public void unpackZIP(String archive, String resultFolder) throws IOException {
        File destDir = new File(resultFolder);

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(archive));
        try {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
        } finally {
            zis.closeEntry();
            zis.close();
        }
    }
}
