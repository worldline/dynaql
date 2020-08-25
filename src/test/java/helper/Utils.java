package helper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static String getResourceFileContent(String resourceName) throws IOException, URISyntaxException {
        ClassLoader classLoader = Utils.class.getClassLoader();
        Path filePath = Paths.get(classLoader.getResource(resourceName).toURI());
        String content = new String(Files.readAllBytes(filePath));

        return content;
    }

    private Utils() {
        // HideUtilityClassConstructor
    }
}
