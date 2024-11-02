package com.example.demo.services;

import com.example.demo.entities.LoanEntity;
import com.example.demo.repositories.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLoans() {
        List<LoanEntity> loans = new ArrayList<>();
        loans.add(new LoanEntity()); // Añade ejemplos de préstamos
        when(loanRepository.findAll()).thenReturn(loans);

        List<LoanEntity> result = loanService.getLoans();

        assertEquals(1, result.size());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    public void testGetLoanById() {
        Long id = 1L;
        LoanEntity loan = new LoanEntity();
        when(loanRepository.findById(id)).thenReturn(Optional.of(loan));

        LoanEntity result = loanService.getLoanById(id);

        assertNotNull(result);
        verify(loanRepository, times(1)).findById(id);
    }

    @Test
    public void testGetLoanByRut() {
        String rut = "12345678-9";
        List<LoanEntity> loans = new ArrayList<>();
        loans.add(new LoanEntity()); // Añade ejemplos de préstamos
        when(loanRepository.findByRut(rut)).thenReturn(loans);

        List<LoanEntity> result = loanService.getLoanByRut(rut);

        assertEquals(1, result.size());
        verify(loanRepository, times(1)).findByRut(rut);
    }

    @Test
    public void testPostLoanSolicitude() {
        LoanEntity loan = new LoanEntity();
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        LoanEntity result = loanService.postLoanSolicitude(loan);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    public void testUpdateLoanSolicitude() {
        LoanEntity loan = new LoanEntity();
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        LoanEntity result = loanService.updateLoanSolicitude(loan);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    public void testCalculateMonthlyLoanPayment() {
        int loanAmount = 100000;
        double annualInterestRate = 5.0;
        int totalYears = 10;

        int monthlyPayment = loanService.calculateMonthlyLoanPayment(loanAmount, annualInterestRate, totalYears);

        assertTrue(monthlyPayment > 0);
    }

    @Test
    public void testGetPendingLoans() {
        List<LoanEntity> loans = new ArrayList<>();
        loans.add(new LoanEntity()); // Añade ejemplos de préstamos
        when(loanRepository.findBySolicitudeStateNot("E8")).thenReturn(loans);

        List<LoanEntity> result = loanService.getPendingLoans();

        assertEquals(1, result.size());
        verify(loanRepository, times(1)).findBySolicitudeStateNot("E8");
    }

    @Test
    public void testR1cuoteIncomeRelation() {
        // 1. Valid case where the quota-to-income ratio is <= 0.35
        assertTrue(loanService.R1cuoteIncomeRelation(350, 1000)); // 350 / 1000 = 0.35

        // 2. Case where the quota-to-income ratio is > 0.35
        assertFalse(loanService.R1cuoteIncomeRelation(500, 1000)); // 500 / 1000 = 0.5

        // 3. Case with income equal to 0 (should return false, as per logic)
        assertFalse(loanService.R1cuoteIncomeRelation(350, 0)); // Invalid input

        // 4. Case with negative income (should return false, as per logic)
        assertFalse(loanService.R1cuoteIncomeRelation(350, -1000)); // Invalid input

        // 5. Case with quota equal to 0 (invalid)
        assertFalse(loanService.R1cuoteIncomeRelation(0, 1000)); // Quota is 0, should return false

        // 6. Case with negative quota (invalid)
        assertFalse(loanService.R1cuoteIncomeRelation(-100, 1000)); // Negative quota, should return false

        // 7. Valid case with a high income and low quota (should still be true)
        assertTrue(loanService.R1cuoteIncomeRelation(100, 1000)); // 100 / 1000 = 0.1

        // 8. Valid case with a very low income (edge case)
        assertTrue(loanService.R1cuoteIncomeRelation(35, 100)); // 35 / 100 = 0.35

        // 9. Edge case with quota and income both positive, just over the limit
        assertFalse(loanService.R1cuoteIncomeRelation(351, 1000)); // 351 / 1000 = 0.351
    }

    @Test
    public void testR3evaluateEmploymentStability() {
        assertTrue(loanService.R3evaluateEmploymentStability(2, true));
        assertFalse(loanService.R3evaluateEmploymentStability(1, true));
        assertTrue(loanService.R3evaluateEmploymentStability(1, false));
    }

    @Test
    public void testR4ratioDebsIncome() {
        assertTrue(loanService.R4ratioDebsIncome(250, 500));
        assertFalse(loanService.R4ratioDebsIncome(400, 500));
    }

    @Test
    public void testR5maxAmount() {
        // Test for First Home (80% of property value)
        assertTrue(loanService.R5maxAmount(80000, 100000, 1)); // 80% of 100000 is 80000
        assertFalse(loanService.R5maxAmount(90000, 100000, 1)); // 90000 > 80000

        // Test for Second Home (70% of property value)
        assertTrue(loanService.R5maxAmount(70000, 100000, 2)); // 70% of 100000 is 70000
        assertFalse(loanService.R5maxAmount(80000, 100000, 2)); // 80000 > 70000

        // Test for Commercial Properties (60% of property value)
        assertTrue(loanService.R5maxAmount(60000, 100000, 3)); // 60% of 100000 is 60000
        assertFalse(loanService.R5maxAmount(70000, 100000, 3)); // 70000 > 60000

        // Test for Renovation (50% of property value)
        assertTrue(loanService.R5maxAmount(50000, 100000, 4)); // 50% of 100000 is 50000
        assertFalse(loanService.R5maxAmount(60000, 100000, 4)); // 60000 > 50000

        // Test for invalid property type (default case)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.R5maxAmount(100000, 100000, 5); // Invalid property type
        });
        assertEquals("Wrong type of property", exception.getMessage());
    }

    @Test
    public void testR6ageLimit() {
        assertTrue(loanService.R6ageLimit(60, 10));
        assertFalse(loanService.R6ageLimit(65, 10));
    }

    @Test
    public void testR7SavingCapacity() {
        LoanEntity loan = new LoanEntity();
        loan.setSolicitudeState("E1");

        // Define el comportamiento del mock
        when(loanRepository.save(any(LoanEntity.class))).thenAnswer(invocation -> {
            LoanEntity savedLoan = invocation.getArgument(0);
            // Asegúrate de que la lógica de tu método está actualizando el objeto
            return savedLoan;
        });

        // Prueba con criterio 5
        LoanEntity result = loanService.R7SavingCapacity(loan, 5);
        assertEquals("E4", result.getSolicitudeState());
        verify(loanRepository, times(1)).save(loan);

        // Reiniciar el estado del loan para la siguiente prueba
        loan.setSolicitudeState("E1");

        // Prueba con criterio 3
        result = loanService.R7SavingCapacity(loan, 3);
        assertEquals("R8", result.getEvaluationState());
        verify(loanRepository, times(2)).save(loan); // Incrementa el contador de invocaciones

        // Reiniciar el estado del loan para la siguiente prueba
        loan.setSolicitudeState("E1");

        // Prueba con criterio 1
        result = loanService.R7SavingCapacity(loan, 1);
        assertEquals("E7", result.getSolicitudeState());
        verify(loanRepository, times(3)).save(loan); // Incrementa el contador de invocaciones
    }

}
