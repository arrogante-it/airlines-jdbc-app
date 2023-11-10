package com.airlines.jdbc.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public final class FileUtil {
    public static String readWholeFileFromResources(String fileName) {
        Path filePath = createPathFromFileName(fileName);

        try (Stream<String> fileLinesStream = openFileLinesStream(filePath)) {
            return fileLinesStream.collect(joining("\n"));
        }
    }

    private static Stream<String> openFileLinesStream(Path filePath) {
        try {
            return Files.lines(filePath);
        } catch (IOException e) {
            throw new IllegalStateException("can't open file lines stream");
        }
    }

    private static Path createPathFromFileName(String fileName) {
        Objects.requireNonNull(fileName);
        URL fileUrl = FileUtil.class.getClassLoader().getResource(fileName);

        try {
            Objects.requireNonNull(fileUrl);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("can't get path from file");
        }
    }
}
