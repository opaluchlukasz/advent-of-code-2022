package com.github.opaluchlukasz.adventofcode;

import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;

import static java.lang.ClassLoader.getSystemResource;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllLines;

public final class FileUtils {

    @SneakyThrows
    public static List<String> read(String resourceFile) {
        return readAllLines(Path.of(getSystemResource(resourceFile).toURI()), UTF_8);
    }
}
