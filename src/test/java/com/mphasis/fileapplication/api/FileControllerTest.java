package com.mphasis.fileapplication.api;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mphasis.fileapplication.exceptions.ResourceNotFoundException;
import com.mphasis.fileapplication.model.dto.FileEntityRequestDTO;
import com.mphasis.fileapplication.model.dto.SearchCriteriaDTO;
import com.mphasis.fileapplication.model.entity.FileEntity;
import com.mphasis.fileapplication.service.FileService;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    void testCreateFile() {
    	FileEntityRequestDTO requestDTO = new FileEntityRequestDTO("example.txt", "text", 1024);
        requestDTO.setFilename("TestFile.txt");

        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(1L);
        fileEntity.setFilename("TestFile.txt");

        when(fileService.createFile(requestDTO)).thenReturn(fileEntity);

        FileEntity result = fileController.createFile(requestDTO);

        assertNotNull(result);
        assertEquals("TestFile.txt", result.getFilename());
    }

    @Test
    void testDeleteFile() {
        doNothing().when(fileService).deleteFile(1L);

        assertDoesNotThrow(() -> fileController.deleteFile(1L));

        verify(fileService, times(1)).deleteFile(1L);
    }

    @Test
    void testSearchFiles() {
        SearchCriteriaDTO criteria = new SearchCriteriaDTO();
        List<FileEntity> files = Arrays.asList(new FileEntity(), new FileEntity());

        when(fileService.searching(criteria)).thenReturn(files);

        List<FileEntity> result = fileController.searching(criteria);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testUpdateStatus() {
        doNothing().when(fileService).updatestatus(1L, "ACTIVE");

        assertDoesNotThrow(() -> fileController.updatestatus(1L, "ACTIVE"));

        verify(fileService, times(1)).updatestatus(1L, "ACTIVE");
    }

    @Test
    void testArchiveFile() {
        doNothing().when(fileService).archieveFile(1L);

        assertDoesNotThrow(() -> fileController.archiveFile(1L));

        verify(fileService, times(1)).archieveFile(1L);
    }

    @Test
    void testGetFile_Success() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(1L);
        fileEntity.setFilename("TestFile.txt");

        when(fileService.getFile(1L)).thenReturn(fileEntity);

        FileEntity result = fileController.getFile(1L);

        assertNotNull(result);
        assertEquals("TestFile.txt", result.getFilename());
    }

    @Test
    void testGetFile_NotFound() {
        when(fileService.getFile(99L)).thenReturn(null);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> fileController.getFile(99L));

        assertEquals("File with ID 99 not found!", exception.getMessage());
    }
}
