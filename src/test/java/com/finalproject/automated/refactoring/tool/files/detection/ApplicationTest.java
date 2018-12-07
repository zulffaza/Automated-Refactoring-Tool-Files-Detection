package com.finalproject.automated.refactoring.tool.files.detection;

import com.finalproject.automated.refactoring.tool.files.detection.model.FileModel;
import com.finalproject.automated.refactoring.tool.files.detection.service.FilesDetection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    private static final String FILES_EXTENSION = ".java";

    private static final Integer NUMBER_OF_PATH = 3;

    private List<FileModel> fileModelsExpected;

    @Before
    public void setUp() {
        fileModelsExpected = createFileModelsExpected();
    }

    @Test
    public void filesDetection_singlePath_success() {
        List<FileModel> fileModels = filesDetection.detect(PATH, FILES_EXTENSION);

        assertEquals(fileModelsExpected.size(), fileModels.size());
        assertFileModels(fileModels);
    }

    @Test
    public void filesDetection_multiPath_success() {
        Map<String, List<FileModel>> result = filesDetection.detect(
                Collections.nCopies(NUMBER_OF_PATH, PATH), FILES_EXTENSION);

        result.forEach((path, fileModels) -> {
            assertEquals(PATH, path);
            assertEquals(fileModelsExpected.size(), fileModels.size());
            assertFileModels(fileModels);
        });
    }

    @Test(expected = NullPointerException.class)
    public void filesDetection_singlePath_failed_pathIsNull() {
        String path = null;
        filesDetection.detect(path, FILES_EXTENSION);
    }

    @Test(expected = NullPointerException.class)
    public void filesDetection_singlePath_failed_fileExtensionIsNull() {
        filesDetection.detect(PATH, null);
    }

    @Test(expected = NullPointerException.class)
    public void filesDetection_multiPath_failed_listOfPathIsNull() {
        List<String> paths = null;
        filesDetection.detect(paths, FILES_EXTENSION);
    }

    @Test(expected = NullPointerException.class)
    public void filesDetection_multiPath_failed_fileExtensionIsNull() {
        filesDetection.detect(Collections.nCopies(NUMBER_OF_PATH, PATH), null);
    }

    private List<FileModel> createFileModelsExpected() {
        List<FileModel> fileModels = new ArrayList<>();

        fileModels.add(FileModel.builder()
                .path("src/test/java/com/finalproject/automated/refactoring/tool/files/detection")
                .filename("ApplicationTest.java")
                .build());

        fileModels.add(FileModel.builder()
                .path("src/test/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation")
                .filename("FilesDetectionThreadImplTest.java")
                .build());

        fileModels.add(FileModel.builder()
                .path("src/test/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation")
                .filename("FilesDetectionImplTest.java")
                .build());

        fileModels.add(FileModel.builder()
                .path("src/test/java/com/finalproject/automated/refactoring/tool/configuration")
                .filename("AsyncConfig.java")
                .build());

        fileModels.add(FileModel.builder()
                .path("src/test/java/com/finalproject/automated/refactoring/tool")
                .filename("Application.java")
                .build());

        fileModels.add(FileModel.builder()
                .path("src/main/java/com/finalproject/automated/refactoring/tool/files/detection/service")
                .filename("FilesDetectionThread.java")
                .build());

        fileModels.add(FileModel.builder()
                .path("src/main/java/com/finalproject/automated/refactoring/tool/files/detection/service")
                .filename("FilesDetection.java")
                .build());

        fileModels.add(FileModel.builder()
                .path("src/main/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation")
                .filename("FilesDetectionThreadImpl.java")
                .build());

        fileModels.add(FileModel.builder()
                .path("src/main/java/com/finalproject/automated/refactoring/tool/files/detection/service/implementation")
                .filename("FilesDetectionImpl.java")
                .build());

        return fileModels;
    }

    private void assertFileModels(List<FileModel> fileModels) {
        for (int index = 0; index < fileModels.size(); index++) {
            assertEquals(fileModelsExpected.get(index).getPath(), fileModels.get(index).getPath());
            assertEquals(fileModelsExpected.get(index).getFilename(), fileModels.get(index).getFilename());
            assertNotNull(fileModels.get(index).getContent());
        }
    }
}
