package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetection;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetectionThread;
import com.finalproject.automated.refactoring.tool.utils.service.ThreadsWatcher;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 6 October 2018
 */

@Service
public class FilesDetectionImpl implements FilesDetection {

    @Autowired
    private FilesDetectionThread filesDetectionThread;

    @Autowired
    private ThreadsWatcher threadsWatcher;

    @Value("${threads.waiting.time}")
    private Integer waitingTime;

    @Override
    public List<FileModel> detect(@NonNull String path, @NonNull String mimeType) {
        return detect(Collections.singletonList(path), mimeType)
                .get(path);
    }

    @Override
    public Map<String, List<FileModel>> detect(@NonNull List<String> paths, @NonNull String mimeType) {
        Map<String, List<FileModel>> result = Collections.synchronizedMap(new HashMap<>());
        List<Future> threads = doFilesDetection(paths, mimeType, result);

        threadsWatcher.waitAllThreadsDone(threads, waitingTime);

        return result;
    }

    private List<Future> doFilesDetection(List<String> paths, String mimeType,
                                          Map<String, List<FileModel>> result) {
        return paths.stream()
                .map(path -> filesDetectionThread.detect(path, mimeType, result))
                .collect(Collectors.toList());
    }
}