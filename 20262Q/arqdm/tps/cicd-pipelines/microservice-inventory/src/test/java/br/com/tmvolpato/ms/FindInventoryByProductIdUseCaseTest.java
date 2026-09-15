package br.com.tmvolpato.ms;

import br.com.tmvolpato.ms.application.core.domain.Inventory;
import br.com.tmvolpato.ms.application.core.usecase.FindInventoryByProductIdUseCase;
import br.com.tmvolpato.ms.application.ports.out.FindInventoryByProductIdOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindInventoryByProductIdUseCaseTest {

    @Mock
    private FindInventoryByProductIdOutputPort findInventoryByProductIdOutputPort;

    private FindInventoryByProductIdUseCase findInventoryByProductIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findInventoryByProductIdUseCase = new FindInventoryByProductIdUseCase(findInventoryByProductIdOutputPort);
    }

    @Test
    void shouldFindInventoryWhenProductExists() {
        // Arrange
        Long productId = 1L;
        Inventory expectedInventory = new Inventory(1L, productId, 10);
        when(findInventoryByProductIdOutputPort.execute(productId)).thenReturn(Optional.of(expectedInventory));

        // Act
        Inventory result = findInventoryByProductIdUseCase.execute(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(10, result.getQuantity());
        verify(findInventoryByProductIdOutputPort, times(1)).execute(productId);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Arrange
        Long productId = 1L;
        when(findInventoryByProductIdOutputPort.execute(productId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            findInventoryByProductIdUseCase.execute(productId);
        });

        assertEquals("stock not found for product", exception.getMessage());
        verify(findInventoryByProductIdOutputPort, times(1)).execute(productId);
    }
}