package com.finalproject.automated.refactoring.tool.files.detection.service.implementation;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetectionThread;
import com.finalproject.automated.refactoring.tool.utils.service.ThreadsWatcher;
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
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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

    @MockBean
    private ThreadsWatcher threadsWatcher;

    private static final String PATH = "src/test/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation";
    private static final String FILES_EXTENSION = ".java";

    private static final Integer NUMBER_OF_PATH = 3;
    private static final Integer WAITING_TIME = 500;

    @Before
    public void setUp() {
        Future future = new Future() {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public Object get() {
                return null;
            }

            @Override
            public Object get(long timeout, TimeUnit unit) {
                return null;
            }
        };

        when(filesDetectionThread.detect(eq(PATH), eq(FILES_EXTENSION),
                eq(Collections.synchronizedMap(new HashMap<>())))).thenReturn(future);
        doNothing().when(threadsWatcher)
                .waitAllThreadsDone(eq(Collections.singletonList(future)), eq(WAITING_TIME));
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