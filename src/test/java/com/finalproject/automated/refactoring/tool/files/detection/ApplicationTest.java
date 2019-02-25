package com.finalproject.automated.refactoring.tool.files.detection;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author fazazulfikapp
 * @version 1.0.0
 * @since 2 November 2018
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private FilesDetection filesDetection;

    private static final String PATH = "src";
    private static final String MIME_TYPE = "text/x-java-source";

    private static final Integer NUMBER_OF_PATH = 3;

    private List<FileModel> fileModelsExpected;

    @Before
    public void setUp() {
        fileModelsExpected = createFileModelsExpected();
    }

    @Test
    public void filesDetection_singlePath_success() {
        List<FileModel> fileModels = filesDetection.detect(PATH, MIME_TYPE);

        fileModels.forEach(fileModel -> System.out.println(fileModel.getPath() + File.separator + fileModel.getFilename()));

        assertEquals(fileModelsExpected.size(), fileModels.size());
        assertFileModels(fileModels);
    }

    @Test
    public void filesDetection_multiPath_success() {
        Map<String, List<FileModel>> result = filesDetection.detect(
                Collections.nCopies(NUMBER_OF_PATH, PATH), MIME_TYPE);

        result.forEach(this::assertFilesDetectionMultiPathSuccess);
    }

    @Test(expected = NullPointerException.class)
    public void filesDetection_singlePath_failed_pathIsNull() {
        String path = null;
        filesDetection.detect(path, MIME_TYPE);
    }

    @Test(expected = NullPointerException.class)
    public void filesDetection_singlePath_failed_mimeTypeIsNull() {
        filesDetection.detect(PATH, null);
    }

    @Test(expected = NullPointerException.class)
    public void filesDetection_multiPath_failed_listOfPathIsNull() {
        List<String> paths = null;
        filesDetection.detect(paths, MIME_TYPE);
    }

    @Test(expected = NullPointerException.class)
    public void filesDetection_multiPath_failed_mimeTypeIsNull() {
        filesDetection.detect(Collections.nCopies(NUMBER_OF_PATH, PATH), null);
    }

    private List<FileModel> createFileModelsExpected() {
        List<FileModel> fileModels = new ArrayList<>();

        fileModels.add(FileModel.builder()
                .path(getEnvironmentPath("src/test/java/com/finalproject/automated/refactoring/tool"))
                .filename("Application.java")
                .build());

        fileModels.add(FileModel.builder()
                .path(getEnvironmentPath("src/test/java/com/finalproject/automated/refactoring/tool/files/detection"))
                .filename("ApplicationTest.java")
                .build());

        fileModels.add(FileModel.builder()
                .path(getEnvironmentPath("src/main/java/com/finalproject/automated/refactoring/tool/files/detection/service"))
                .filename("FilesDetection.java")
                .build());

        fileModels.add(FileModel.builder()
                .path(getEnvironmentPath("src/main/java/com/finalproject/automated/refactoring/tool/files/detection/service"))
                .filename("FilesDetectionThread.java")
                .build());

        fileModels.add(FileModel.builder()
                .path(getEnvironmentPath("src/main/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation"))
                .filename("FilesDetectionImpl.java")
                .build());

        fileModels.add(FileModel.builder()
                .path(getEnvironmentPath("src/test/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation"))
                .filename("FilesDetectionImplTest.java")
                .build());

        fileModels.add(FileModel.builder()
                .path(getEnvironmentPath("src/main/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation"))
                .filename("FilesDetectionThreadImpl.java")
                .build());

        fileModels.add(FileModel.builder()
                .path(getEnvironmentPath("src/test/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation"))
                .filename("FilesDetectionThreadImplTest.java")
                .build());

        return fileModels;
    }

    private String getEnvironmentPath(String path) {
        return new File(path).getPath();
    }

    private void assertFileModels(List<FileModel> fileModels) {
        assertTrue(fileModelsExpected.containsAll(fileModels));
    }

    private void assertFilesDetectionMultiPathSuccess(String path, List<FileModel> fileModels) {
        assertEquals(PATH, path);
        assertEquals(fileModelsExpected.size(), fileModels.size());
        assertFileModels(fileModels);
    }
}
