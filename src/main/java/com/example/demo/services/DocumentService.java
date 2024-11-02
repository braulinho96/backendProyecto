package com.example.demo.services;

import com.example.demo.entities.DocumentEntity;
import com.example.demo.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;
    public ArrayList<DocumentEntity> getDocumentByIdLoan(Long id){
        return documentRepository.findByIdLoan(id);
    }
    public DocumentEntity saveDocument(DocumentEntity uploadedDocument){
        return documentRepository.save(uploadedDocument);
    }
    public boolean deleteDocument(Long id) throws Exception{
        try{
            documentRepository.deleteById(id);
            return true;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
