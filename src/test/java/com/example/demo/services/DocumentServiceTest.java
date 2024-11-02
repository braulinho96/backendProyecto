package com.example.demo.services;
import com.example.demo.entities.DocumentEntity;
import com.example.demo.repositories.DocumentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetDocumentByIdLoan_thenReturnDocumentList() {
        // Given
        Long loanId = 1L;
        DocumentEntity document1 = new DocumentEntity();
        document1.setId(1L);
        document1.setIdLoan(loanId);

        DocumentEntity document2 = new DocumentEntity();
        document2.setId(2L);
        document2.setIdLoan(loanId);

        ArrayList<DocumentEntity> documents = new ArrayList<>();
        documents.add(document1);
        documents.add(document2);

        when(documentRepository.findByIdLoan(loanId)).thenReturn(documents);

        // When
        ArrayList<DocumentEntity> result = documentService.getDocumentByIdLoan(loanId);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).contains(document1, document2);
        verify(documentRepository, times(1)).findByIdLoan(loanId);
    }

    @Test
    void whenSaveDocument_thenReturnSavedDocument() {
        // Given
        DocumentEntity documentToSave = new DocumentEntity();
        documentToSave.setId(1L);

        when(documentRepository.save(documentToSave)).thenReturn(documentToSave);

        // When
        DocumentEntity savedDocument = documentService.saveDocument(documentToSave);

        // Then
        assertThat(savedDocument).isEqualTo(documentToSave);
        verify(documentRepository, times(1)).save(documentToSave);
    }

    @Test
    void whenDeleteDocument_thenReturnTrue() throws Exception {
        // Given
        Long documentId = 1L;

        // When
        boolean isDeleted = documentService.deleteDocument(documentId);

        // Then
        assertThat(isDeleted).isTrue();
        verify(documentRepository, times(1)).deleteById(documentId);
    }

    @Test
    void whenDeleteDocumentThrowsException_thenReturnFalse() {
        // Given
        Long documentId = 1L;
        doThrow(new RuntimeException("Document not found")).when(documentRepository).deleteById(documentId);

        // When
        Exception exception = null;
        try {
            documentService.deleteDocument(documentId);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Document not found");
        verify(documentRepository, times(1)).deleteById(documentId);
    }
}

