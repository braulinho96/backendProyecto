package com.example.demo.controllers;

import com.example.demo.entities.LoanEntity;
import com.example.demo.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin("*")
public class LoanController {
    @Autowired
    LoanService loanService;

    @PostMapping("/")
    public ResponseEntity<LoanEntity> saveLoanSolicitude(@RequestBody LoanEntity loanSolicitude){
        LoanEntity Newloan = loanService.postLoanSolicitude(loanSolicitude);
        return ResponseEntity.ok(Newloan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanEntity> getLoanById(@PathVariable Long id) {
        LoanEntity loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }
    @GetMapping("/rut")
    public ResponseEntity<List<LoanEntity>> getLoanByRut(@RequestParam String rut) {
        List<LoanEntity> loan = loanService.getLoanByRut(rut);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/")
    public ResponseEntity<LoanEntity> updateLoanSolicitude(@RequestBody LoanEntity loanSolicitude) {
        LoanEntity updatedLoan = loanService.updateLoanSolicitude(loanSolicitude);
        return ResponseEntity.ok(updatedLoan);
    }

    //P1 is connected to loan.service -> calculatemonthlypayment
    @GetMapping("/calculate")
    public int calculateMonthlyPayment(
            @RequestParam int loanAmount,
            @RequestParam double annualInterestRate,
            @RequestParam int totalYears) {
        return loanService.calculateMonthlyLoanPayment(loanAmount, annualInterestRate, totalYears);
    }

    // P4.
    @GetMapping("/pending")
    public List<LoanEntity> getPendingLoans() {
        return loanService.getPendingLoans();
    }

    @PostMapping("/evaluate/R1")
    public ResponseEntity<Boolean> R1evaluateCuoteIncome(@RequestParam int quota, @RequestParam int income) {
        boolean result = loanService.R1cuoteIncomeRelation(quota, income);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/evaluate/R3")
    public ResponseEntity<Boolean> R3evaluateEmploymentStability(@RequestParam int yearsOfEmployment, @RequestParam boolean isSelfEmployed) {
        boolean isStable = loanService.R3evaluateEmploymentStability(yearsOfEmployment, isSelfEmployed);
        return ResponseEntity.ok(isStable);
    }

    @PostMapping("/evaluate/R4")
    public ResponseEntity<Boolean> R4ratioDebsIncome(
            @RequestParam int totalDebts,
            @RequestParam int monthlyIncome) {
        boolean isApproved = loanService.R4ratioDebsIncome(totalDebts, monthlyIncome);
        return ResponseEntity.ok(isApproved);
    }

    @PostMapping("/evaluate/R5")
    public ResponseEntity<Boolean> R5maxAmount(
            @RequestParam int loanAmount,
            @RequestParam int propertyValue,
            @RequestParam int propertyType) {

        boolean isApproved = loanService.R5maxAmount(loanAmount, propertyValue, propertyType);
        return ResponseEntity.ok(isApproved);
    }

    @PostMapping("/evaluate/R6")
    public ResponseEntity<Boolean> R6ageLimit(
            @RequestParam int age,
            @RequestParam int term) {

        boolean isApproved = loanService.R6ageLimit(age, term);
        return ResponseEntity.ok(isApproved);
    }

    @PostMapping("/evaluate/R7")
    public ResponseEntity<LoanEntity> R7SavingCapacity(@RequestBody LoanEntity loanSolicitude, @RequestParam int numberApproved){
        LoanEntity updatedLoan = loanService.R7SavingCapacity(loanSolicitude, numberApproved );
        return ResponseEntity.ok(updatedLoan);
    }






}
