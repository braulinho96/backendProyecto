package com.example.demo.repositories;

import com.example.demo.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    public ArrayList<DocumentEntity> findByIdLoan(Long id);
}
