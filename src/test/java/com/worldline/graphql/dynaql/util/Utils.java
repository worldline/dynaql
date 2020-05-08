package com.worldline.graphql.dynaql.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static String getResourceFileContent(Class context, String resourceName) throws IOException, URISyntaxException {
        ClassLoader classLoader = context.getClassLoader();
        Path filePath = Paths.get(classLoader.getResource(resourceName).toURI());
        String content = new String(Files.readAllBytes(filePath));

        return content;
    }
}
