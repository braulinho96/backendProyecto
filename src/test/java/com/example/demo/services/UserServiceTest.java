package com.example.demo.services;

import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setId(1L);
        user.setRut("12345678-9");
        user.setId_rol(2); // Assuming role 2 is allowed to create users
        user.setPassword("password");
        user.setSolicitude_state(true);
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserEntity result = userService.getUserById(1L);
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByRut() {
        when(userRepository.findByRut("12345678-9")).thenReturn(user);
        UserEntity result = userService.getUserByRut("12345678-9");
        assertEquals(user, result);
        verify(userRepository, times(1)).findByRut("12345678-9");
    }

    @Test
    void testSaveUser_Success() {
        // Configuración del mock para devolver null cuando se busca el RUT, indicando que el usuario no existe
        Mockito.when(userRepository.findByRut(user.getRut())).thenReturn(null);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        // Ejecución y verificación
        UserEntity result = userService.saveUser(user);
        Assertions.assertEquals(user, result);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void testSaveUser_AlreadyExists() {
        // Configuración del mock para devolver un usuario cuando se busca el RUT, indicando que el usuario ya existe
        Mockito.when(userRepository.findByRut(user.getRut())).thenReturn(user);

        // Ejecución y verificación de excepción
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(user);
        });
        Assertions.assertEquals("The user is already in the database", exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        doNothing().when(userRepository).deleteById(1L);
        boolean result = userService.deleteUser(1L);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_Failure() {
        doThrow(new RuntimeException("User not found")).when(userRepository).deleteById(1L);
        Exception exception = assertThrows(Exception.class, () -> {
            userService.deleteUser(1L);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testValidateUserLogin_Success() {
        when(userRepository.findByRut("12345678-9")).thenReturn(user);
        UserEntity result = userService.validateUserLogin("12345678-9", "password");
        assertEquals(user, result);
        verify(userRepository, times(1)).findByRut("12345678-9");
    }

    @Test
    void testValidateUserLogin_InvalidPassword() {
        when(userRepository.findByRut("12345678-9")).thenReturn(user);
        UserEntity result = userService.validateUserLogin("12345678-9", "wrongPassword");
        assertNull(result);
    }

    @Test
    void testValidateUserLogin_UserNotFound() {
        when(userRepository.findByRut("12345678-9")).thenReturn(null);
        UserEntity result = userService.validateUserLogin("12345678-9", "password");
        assertNull(result);
    }
}
