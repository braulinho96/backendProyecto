package com.example.demo.controllers;

import com.example.demo.entities.DocumentEntity;
import com.example.demo.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentController {
    @Autowired
    DocumentService documentService;
    @PostMapping("/")
    public ResponseEntity<DocumentEntity> uploadDocument(@RequestParam("file") MultipartFile file,
                                                         @RequestParam("name") String name,
                                                         @RequestParam("idLoan") Long idLoan){

        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // bad_request to indicate that the file param is empty
        }

        try {
            DocumentEntity newDocument = new DocumentEntity();

            newDocument.setIdLoan(idLoan);
            newDocument.setName(name);
            newDocument.setContent(file.getBytes()); // Aquí debes asegurarte que se guarde correctamente
            DocumentEntity savedDocument = documentService.saveDocument(newDocument);

            System.out.println("Document content size: " + newDocument.getContent().length);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDocument);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    // Return list of the documents assossiated with the loan
    @GetMapping("/{idLoan}")
    public ResponseEntity<List<DocumentEntity>> getDocumentByIdLoan(@PathVariable Long idLoan){
        List<DocumentEntity> documents = documentService.getDocumentByIdLoan(idLoan);
        return ResponseEntity.ok(documents);
    }

}
