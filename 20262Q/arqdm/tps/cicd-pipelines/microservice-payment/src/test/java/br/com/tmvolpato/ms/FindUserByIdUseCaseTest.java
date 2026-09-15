package br.com.tmvolpato.ms;

import br.com.tmvolpato.ms.application.core.domain.User;
import br.com.tmvolpato.ms.application.ports.out.FindUserByIdOutputPort;
import br.com.tmvolpato.ms.application.core.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindUserByIdUseCaseTest {

    @Mock
    private FindUserByIdOutputPort findUserByIdOutputPort;

    private FindUserByIdUseCase findUserByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findUserByIdUseCase = new FindUserByIdUseCase(findUserByIdOutputPort);
    }

    @Test
    void shouldFindUserWhenUserExists() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User(userId, "John Doe", new BigDecimal("100.00"));
        when(findUserByIdOutputPort.execute(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = findUserByIdUseCase.execute(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(new BigDecimal("100.00"), result.getBalance());
        verify(findUserByIdOutputPort, times(1)).execute(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        when(findUserByIdOutputPort.execute(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            findUserByIdUseCase.execute(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(findUserByIdOutputPort, times(1)).execute(userId);
    }
}