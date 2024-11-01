package com.example.demo.services;

import com.example.demo.entities.LoanEntity;
import com.example.demo.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    @Autowired
    LoanRepository loanRepository;

    // Getters of loans
    public List<LoanEntity> getLoans(){ return (List<LoanEntity>) loanRepository.findAll();}
    public LoanEntity getLoanById(Long id){ return loanRepository.findById(id).get();}
    public List<LoanEntity> getLoanByRut(String rut){ return loanRepository.findByRut(rut);}

    // Creation
    public LoanEntity postLoanSolicitude(LoanEntity loanSolicitude){return loanRepository.save(loanSolicitude);}
    // Update
    public LoanEntity updateLoanSolicitude(LoanEntity loanSolicitude){ return loanRepository.save(loanSolicitude);}

    // P1 to calculate the monthly payment of the loan
    public int calculateMonthlyLoanPayment(int loanAmount, double annualInterestRate, int totalYears) {
        int months = totalYears * 12;
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        double monthlyPayment = loanAmount * ((monthlyInterestRate * Math.pow(1 + monthlyInterestRate, months)) /
                (Math.pow(1 + monthlyInterestRate, months) - 1));
        return (int) Math.ceil(monthlyPayment);
    }

    // P4. For the solicitude revision
    public List<LoanEntity> getPendingLoans(){
        return loanRepository.findBySolicitudeStateNot("E8");
    }

    // R1.
    public boolean R1cuoteIncomeRelation(int quota, int income) {
        // Verify the values of the quota and income
        if (quota <= 0 || income <= 0) {
            return false;
        }
        // we convert the data type to float, so we can divide them
        float relation = (float) quota / income;
        return relation <= 0.35;
    }

    // R3
    public boolean R3evaluateEmploymentStability(int yearsOfEmployment, boolean isSelfEmployed) {
        if (isSelfEmployed) {
            return yearsOfEmployment >= 2; // Case of being self-employed
        } else {
            return yearsOfEmployment >= 1; // Case of being employed
        }
    }
    // R4
    public boolean R4ratioDebsIncome(int totalDebts, int monthlyIncome) {
        float ratio = (float) totalDebts / monthlyIncome;
        return !(ratio > 0.5);
    }

    public boolean R5maxAmount(int loanAmount, int propertyValue, int propertyType) {
        double maxAllowedLoan = switch (propertyType) {
            case 1 ->  // First Home
                    propertyValue * 0.8;
            case 2 ->  // Second Home
                    propertyValue * 0.7;
            case 3 ->  // Propiedades Comerciales
                    propertyValue * 0.6;
            case 4 ->  // Remodelación
                    propertyValue * 0.5;
            default ->  // Tipo de propiedad no válido
                    throw new IllegalArgumentException("Wrong type of property");
        };
        return loanAmount <= maxAllowedLoan;
    }
    // R6
    public boolean R6ageLimit(int age, int term) {
        return (term + age) <=  70;
    }

    // R7
    public LoanEntity R7SavingCapacity(LoanEntity evaluatedLoan, int numberApproved){
        if(numberApproved == 5) {
            evaluatedLoan.setSolicitudeState("E4");
        } else if(numberApproved == 3 | numberApproved == 4){
            evaluatedLoan.setEvaluationState("R8"); // To indicate that we need an additional revision
        } else{
            evaluatedLoan.setSolicitudeState("E7");
        }
        return loanRepository.save(evaluatedLoan);
    }



}
