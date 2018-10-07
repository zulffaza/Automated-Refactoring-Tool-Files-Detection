package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 7 October 2018
 */

public class FilesDetectionThreadImplTest {

    private FilesDetectionThreadImpl filesDetectionThread;

    @Before
    public void setUp() {
        filesDetectionThread = new FilesDetectionThreadImpl();
    }

    @Test
    public void detect_success() {
        String path = "src/test/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation";
        String fileType = ".java";

        Integer pathCount = 1;
        Integer classCount = 2;

        Map<String, List<FileModel>> result = new HashMap<>();
        Future future = filesDetectionThread.detect(path, fileType, result);

        assertNull(future);
        assertEquals(pathCount.intValue(), result.size());
        assertEquals(classCount.intValue(), result.get(path).size());
    }

    @Test
    public void detect_failed_pathNotFound() {
        String path = "not/found/path";
        String fileType = ".java";

        Integer pathCount = 1;

        Map<String, List<FileModel>> result = new HashMap<>();
        Future future = filesDetectionThread.detect(path, fileType, result);

        assertNull(future);
        assertEquals(pathCount.intValue(), result.size());
        assertNull(result.get(path));
    }

    @Test(expected = NullPointerException.class)
    public void detect_failed_pathIsNull() {
        String fileType = ".java";

        Map<String, List<FileModel>> result = new HashMap<>();
        filesDetectionThread.detect(null, fileType, result);
    }

    @Test(expected = NullPointerException.class)
    public void detect_failed_fileTypeIsNull() {
        String path = "src/test/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation";

        Map<String, List<FileModel>> result = new HashMap<>();
        filesDetectionThread.detect(path, null, result);
    }

    @Test(expected = NullPointerException.class)
    public void detect_failed_resultIsNull() {
        String path = "src/test/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation";
        String fileType = ".java";

        filesDetectionThread.detect(path, fileType, null);
    }
}