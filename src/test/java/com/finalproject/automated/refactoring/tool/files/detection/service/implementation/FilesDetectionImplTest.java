package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetectionThread;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 7 October 2018
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilesDetectionImplTest {

    @Autowired
    private FilesDetectionImpl filesDetection;

    @MockBean
    private FilesDetectionThread filesDetectionThread;

    private static final String PATH = "src";
    private static final String MIME_TYPE = "text/x-java-source";

    private static final Integer NUMBER_OF_PATH = 3;
    private static final Integer INVOKED_ONCE = 1;

    @Before
    public void setUp() {
        doNothing().when(filesDetectionThread)
                .detect(eq(PATH), eq(MIME_TYPE), eq(Collections.synchronizedMap(new HashMap<>())));
    }

    @Test
    public void detect_singlePath_success() {
        List<FileModel> result = filesDetection.detect(PATH, MIME_TYPE);
        assertNull(result);

        verifyFilesDetectionThread(INVOKED_ONCE);
    }

    @Test
    public void detect_multiPath_success() {
        Map<String, List<FileModel>> result = filesDetection.detect(
                Collections.nCopies(NUMBER_OF_PATH, PATH), MIME_TYPE);
        assertNotNull(result);

        verifyFilesDetectionThread(NUMBER_OF_PATH);
    }

    @Test(expected = NullPointerException.class)
    public void detect_singlePath_failed_pathIsNull() {
        String path = null;
        filesDetection.detect(path, MIME_TYPE);
    }

    @Test(expected = NullPointerException.class)
    public void detect_singlePath_failed_mimeTypeIsNull() {
        filesDetection.detect(PATH, null);
    }

    @Test(expected = NullPointerException.class)
    public void detect_multiPath_failed_listOfPathIsNull() {
        List<String> paths = null;
        filesDetection.detect(paths, MIME_TYPE);
    }

    @Test(expected = NullPointerException.class)
    public void detect_multiPath_failed_mimeTypeIsNull() {
        filesDetection.detect(Collections.nCopies(NUMBER_OF_PATH, PATH), null);
    }

    private void verifyFilesDetectionThread(Integer invocationsTimes) {
        verify(filesDetectionThread, times(invocationsTimes)).detect(eq(PATH), eq(MIME_TYPE),
                eq(Collections.synchronizedMap(new HashMap<>())));
        verifyNoMoreInteractions(filesDetectionThread);
    }
}