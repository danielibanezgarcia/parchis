package ai.portia.parchis.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static String readFile(final String filePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            return new String(encoded, Charset.forName("UTF-8"));
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
