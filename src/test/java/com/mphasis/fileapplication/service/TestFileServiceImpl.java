package com.mphasis.fileapplication.service;

import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.List;
import com.mphasis.fileapplication.dao.FileRepositary;
import com.mphasis.fileapplication.model.dto.FileEntityRequestDTO;
import com.mphasis.fileapplication.model.entity.FileEntity;

@SpringBootTest
class FileServiceImplTest {

    @Mock
    private FileRepositary fileRepositary;

    @InjectMocks
    private FileServiceImpl fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFile() {
        FileEntityRequestDTO requestDTO = new FileEntityRequestDTO(null, null, null);
        requestDTO.setFilename("testFile.txt");
        requestDTO.setRecordCount(100);
        requestDTO.setStatus("created");

        FileEntity savedEntity = new FileEntity();
        savedEntity.setFilename("testFile.txt");
        savedEntity.setRecordCount(100);
        savedEntity.setStatus("created");

        when(fileRepositary.save(any(FileEntity.class))).thenReturn(savedEntity);

        FileEntity result = fileService.createFile(requestDTO);
        assertNotNull(result);
        assertEquals("testFile.txt", result.getFilename());
        assertEquals(100, result.getRecordCount());
        assertEquals("created", result.getStatus());
        verify(fileRepositary).save(any(FileEntity.class));
    }

    @Test
    void testDeleteFile() {
        Long fileId = 1L;
        when(fileRepositary.findById(fileId)).thenReturn(Optional.of(new FileEntity()));

        fileService.deleteFile(fileId);
        verify(fileRepositary).deleteById(fileId);
    }


    @Test
    void testArchiveFile() {
        Long fileId = 1L;
        FileEntity entity = new FileEntity();

        when(fileRepositary.findById(fileId)).thenReturn(Optional.of(entity));

        fileService.archieveFile(fileId);

        assertEquals("archieved", entity.getStatus());
        verify(fileRepositary).findById(fileId);
        verify(fileRepositary).save(entity);
    }

    @Test
    void testGetFile() {
        Long fileId = 1L;
        FileEntity entity = new FileEntity();

        when(fileRepositary.findById(fileId)).thenReturn(Optional.of(entity));

        FileEntity result = fileService.getFile(fileId);

        assertNotNull(result);
        verify(fileRepositary).findById(fileId);
    }
}
