package com.finalproject.automated.refactoring.tool.files.detection.service;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 6 October 2018
 */

public interface FilesDetection {

    List<FileModel> detect(@NonNull String path, @NonNull String fileExtension);

    Map<String, List<FileModel>> detect(@NonNull List<String> paths, @NonNull String fileExtension);
}
