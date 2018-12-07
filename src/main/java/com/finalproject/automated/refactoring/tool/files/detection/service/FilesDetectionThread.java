package com.finalproject.automated.refactoring.tool.files.detection.service;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 7 October 2018
 */

public interface FilesDetectionThread {

    Future detect(@NonNull String path, @NonNull String fileExtension,
                  @NonNull Map<String, List<FileModel>> result);
}
