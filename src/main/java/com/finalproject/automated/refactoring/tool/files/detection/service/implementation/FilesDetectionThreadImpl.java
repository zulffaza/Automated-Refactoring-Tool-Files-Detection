package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetectionThread;
import lombok.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
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
    public Future detect(@NonNull String path, @NonNull String fileType,
                         @NonNull Map<String, List<FileModel>> result) {
        List<FileModel> files = readAllFiles(path, fileType);
        result.put(path, files);

        return null;
    }

    private List<FileModel> readAllFiles(String path, String fileType) {
        List<FileModel> result = null;

        try {
            result = Files.walk(Paths.get(path))
                    .filter(subPath -> isSameFileType(subPath, fileType))
                    .map(this::mapIntoFileModel)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            printPathError(path);
        }

        return result;
    }

    private Boolean isSameFileType(Path path, String fileType) {
        return path.toString().endsWith(fileType);
    }

    private FileModel mapIntoFileModel(Path path) {
        return FileModel.builder()
                .path(path.getParent().toString())
                .content(getFileContent(path))
                .filename(path.getFileName().toString())
                .build();
    }

    private String getFileContent(Path path) {
        String result = null;

        try {
            result = Files.lines(path)
                    .collect(Collectors.joining(NEW_LINE_DELIMITER));
        } catch (IOException e) {
            printPathError(path.toString());
        }

        return result;
    }

    private void printPathError(String path) {
        System.err.println("Error opening " + path + "...");
    }
}
