package br.com.tmvolpato.ms.sale;

import br.com.tmvolpato.ms.sale.application.core.domain.Sale;
import br.com.tmvolpato.ms.sale.application.core.usecase.*;
import br.com.tmvolpato.ms.sale.application.core.domain.enums.*;
import br.com.tmvolpato.ms.sale.application.port.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FindSaleByIdUseCaseTest {

    @Mock
    private FindSaleByIdOutputPort findSaleByIdOutputPort;

    private FindSaleByIdUseCase findSaleByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findSaleByIdUseCase = new FindSaleByIdUseCase(findSaleByIdOutputPort);
    }

    @Test
    void shouldFindSaleSuccessfully() {
        // Arrange
        Long saleId = 1L;
        Sale expectedSale = new Sale();
        expectedSale.setId(saleId);
        expectedSale.setUserId(1L);
        expectedSale.setProductId(1L);
        expectedSale.setValue(BigDecimal.TEN);
        expectedSale.setStatus(SaleStatusEnum.PENDING);
        expectedSale.setQuantity(1);

        when(findSaleByIdOutputPort.execute(saleId)).thenReturn(Optional.of(expectedSale));

        // Act
        Sale result = findSaleByIdUseCase.execute(saleId);

        // Assert
        assertNotNull(result);
        assertEquals(saleId, result.getId());
        assertEquals(1L, result.getUserId());
        assertEquals(1L, result.getProductId());
        assertEquals(BigDecimal.TEN, result.getValue());
        assertEquals(SaleStatusEnum.PENDING, result.getStatus());
        assertEquals(1, result.getQuantity());

        verify(findSaleByIdOutputPort, times(1)).execute(saleId);
    }

    @Test
    void shouldThrowExceptionWhenSaleNotFound() {
        // Arrange
        Long saleId = 1L;
        when(findSaleByIdOutputPort.execute(saleId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            findSaleByIdUseCase.execute(saleId);
        });

        assertEquals("Sale not found.", exception.getMessage());
        verify(findSaleByIdOutputPort, times(1)).execute(saleId);
    }
}
