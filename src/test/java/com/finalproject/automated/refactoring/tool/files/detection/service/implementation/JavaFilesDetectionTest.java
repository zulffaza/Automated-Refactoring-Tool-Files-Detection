package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetectionThread;
import com.sun.xml.internal.ws.util.CompletedFuture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 7 October 2018
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaFilesDetectionTest {

    @Autowired
    private JavaFilesDetection filesDetection;

    @MockBean
    private FilesDetectionThread filesDetectionThread;

    private static final String PATH = "src/test/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation";
    private static final String FILES_EXTENSION = ".java";

    private static final Integer NUMBER_OF_PATH = 3;

    @Before
    public void setUp() {
        Future future = new CompletedFuture<>(null, null);

        when(filesDetectionThread.detect(eq(PATH), eq(FILES_EXTENSION),
                eq(Collections.synchronizedMap(new HashMap<>())))).thenReturn(future);
    }

    @Test
    public void detect_singlePath_success() {
        List<FileModel> result = filesDetection.detect(PATH);
        assertNull(result);
    }

    @Test
    public void detect_multiPath_success() {
        Map<String, List<FileModel>> result = filesDetection.detect(Collections.nCopies(NUMBER_OF_PATH, PATH));
        assertNotNull(result);
    }

    @Test(expected = NullPointerException.class)
    public void detect_singlePath_failed_pathIsNull() {
        String path = null;
        filesDetection.detect(path);
    }

    @Test(expected = NullPointerException.class)
    public void detect_multiPath_failed_listOfPathIsNull() {
        List<String> paths = null;
        filesDetection.detect(paths);
    }
}