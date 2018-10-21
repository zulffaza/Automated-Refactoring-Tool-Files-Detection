package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetection;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetectionThread;
import com.finalproject.automated.refactoring.tool.utils.service.ThreadsWatcher;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Future;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 6 October 2018
 */

@Service
public class JavaFilesDetection implements FilesDetection {

    @Autowired
    private FilesDetectionThread filesDetectionThread;

    @Autowired
    private ThreadsWatcher threadsWatcher;

    private static final Integer WAITING_TIME = 500;

    private static final String FILES_EXTENSION = ".java";

    @Override
    public List<FileModel> detect(@NonNull String path) {
        return detect(Collections.singletonList(path))
                .get(path);
    }

    @Override
    public Map<String, List<FileModel>> detect(@NonNull List<String> paths) {
        Map<String, List<FileModel>> result = Collections.synchronizedMap(new HashMap<>());
        List<Future> futures = new ArrayList<>();

        doFilesDetection(paths, futures, result);
        threadsWatcher.waitAllThreadsDone(futures, WAITING_TIME);

        return result;
    }

    private void doFilesDetection(List<String> paths, List<Future> futures, Map<String, List<FileModel>> result) {
        paths.forEach(path ->
                doFileDetection(path, futures, result));
    }

    private void doFileDetection(String path, List<Future> futures, Map<String, List<FileModel>> result) {
        Future future = filesDetectionThread.detect(path, FILES_EXTENSION, result);
        futures.add(future);
    }
}