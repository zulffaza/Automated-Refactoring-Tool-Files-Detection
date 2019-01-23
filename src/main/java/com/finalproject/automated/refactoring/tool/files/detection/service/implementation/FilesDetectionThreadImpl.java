package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetectionThread;
import lombok.NonNull;
import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 7 October 2018
 */

@Service
public class FilesDetectionThreadImpl implements FilesDetectionThread {

    private static final String NEW_LINE_DELIMITER = "\n";

    @Async
    @Override
    public Future detect(@NonNull String path, @NonNull String mimeType,
                         @NonNull Map<String, List<FileModel>> result) {
        result.put(path, readFiles(path, mimeType));
        return null;
    }

    private List<FileModel> readFiles(String path, String mimeType) {
        List<FileModel> result = null;
        Path start = Paths.get(path);

        try {
            result = Files.walk(start)
                    .filter(filePath -> checkType(filePath, mimeType))
                    .map(this::mapIntoFileModel)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            printPathError(path);
        }

        return result;
    }

    private Boolean checkType(Path filePath, String mimeType) {
        Tika tika = new Tika();

        try {
            return Optional.of(tika.detect(filePath))
                    .map(type -> isTypeEquals(type, mimeType))
                    .get();
        } catch (IOException | NullPointerException e) {
            return Boolean.FALSE;
        }
    }

    private Boolean isTypeEquals(String type, String mimeType) {
        return type.equals(mimeType);
    }

    private FileModel mapIntoFileModel(Path filePath) {
        return FileModel.builder()
                .path(filePath.getParent().toString())
                .content(getFileContent(filePath))
                .filename(filePath.getFileName().toString())
                .build();
    }

    private String getFileContent(Path filePath) {
        String result = null;

        try {
            result = Files.lines(filePath)
                    .collect(Collectors.joining(NEW_LINE_DELIMITER));
        } catch (IOException e) {
            printPathError(filePath.toString());
        }

        return result;
    }

    private void printPathError(String path) {
        System.err.println("Error opening " + path + "...");
    }
}
