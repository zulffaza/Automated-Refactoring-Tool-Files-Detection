package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetection;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetectionThread;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 6 October 2018
 */

@Service
public class FilesDetectionImpl implements FilesDetection {

    @Autowired
    private FilesDetectionThread filesDetectionThread;

    @Override
    public List<FileModel> detect(@NonNull String path, @NonNull String mimeType) {
        return detect(Collections.singletonList(path), mimeType)
                .get(path);
    }

    @Override
    public Map<String, List<FileModel>> detect(@NonNull List<String> paths, @NonNull String mimeType) {
        Map<String, List<FileModel>> result = Collections.synchronizedMap(new HashMap<>());
        doFilesDetection(paths, mimeType, result);

        return result;
    }

    private void doFilesDetection(List<String> paths, String mimeType,
                                  Map<String, List<FileModel>> result) {
        paths.parallelStream()
                .forEach(path -> filesDetectionThread.detect(path, mimeType, result));
    }
}