package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String path = "src";
        String mimeType = "text/x-java-source";

        Integer pathCount = 1;
        Integer classCount = 8;

        Map<String, List<FileModel>> result = new HashMap<>();
        filesDetectionThread.detect(path, mimeType, result);

        assertEquals(pathCount.intValue(), result.size());
        assertEquals(classCount.intValue(), result.get(path).size());
    }

    @Test
    public void detect_failed_pathNotFound() {
        String path = new File("not/found/path")
                .getPath();
        String mimeType = "text/x-java-source";

        Integer pathCount = 1;

        Map<String, List<FileModel>> result = new HashMap<>();
        filesDetectionThread.detect(path, mimeType, result);

        assertEquals(pathCount.intValue(), result.size());
        assertNull(result.get(path));
    }

    @Test(expected = NullPointerException.class)
    public void detect_failed_pathIsNull() {
        String mimeType = "text/x-java-source";

        Map<String, List<FileModel>> result = new HashMap<>();
        filesDetectionThread.detect(null, mimeType, result);
    }

    @Test(expected = NullPointerException.class)
    public void detect_failed_mimeTypeIsNull() {
        String path = "src";

        Map<String, List<FileModel>> result = new HashMap<>();
        filesDetectionThread.detect(path, null, result);
    }

    @Test(expected = NullPointerException.class)
    public void detect_failed_resultIsNull() {
        String path = "src";
        String mimeType = "text/x-java-source";

        filesDetectionThread.detect(path, mimeType, null);
    }
}