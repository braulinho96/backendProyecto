package com.example.demo.repositories;

import com.example.demo.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    public List<LoanEntity> findByRut(String rut);
    List<LoanEntity> findBySolicitudeStateNot(String solicitudeState);


}
