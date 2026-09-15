package br.com.tmvolpato.ms.sale;

import br.com.tmvolpato.ms.sale.application.core.domain.Sale;
import br.com.tmvolpato.ms.sale.application.core.usecase.*;
import br.com.tmvolpato.ms.sale.application.core.domain.enums.*;
import br.com.tmvolpato.ms.sale.application.port.out.*;
import br.com.tmvolpato.ms.sale.application.port.in.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;


class FinalizeSaleUseCaseTest {

    @Mock
    private FindSaleByIdInputPort findSaleByIdInputPort;

    @Mock
    private SaveSaleOutputPort saveSaleOutputPort;

    private FinalizeSaleUseCase finalizeSaleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        finalizeSaleUseCase = new FinalizeSaleUseCase(findSaleByIdInputPort, saveSaleOutputPort);
    }

    @Test
    void shouldFinalizeSaleSuccessfully() {
        // Arrange
        Long saleId = 1L;
        Sale inputSale = new Sale();
        inputSale.setId(saleId);

        Sale foundSale = new Sale();
        foundSale.setId(saleId);
        foundSale.setUserId(1L);
        foundSale.setProductId(1L);
        foundSale.setValue(BigDecimal.TEN);
        foundSale.setStatus(SaleStatusEnum.PENDING);
        foundSale.setQuantity(1);

        when(findSaleByIdInputPort.execute(saleId)).thenReturn(foundSale);

        // Act
        finalizeSaleUseCase.execute(inputSale);

        // Assert
        verify(findSaleByIdInputPort, times(1)).execute(saleId);
        verify(saveSaleOutputPort, times(1)).execute(argThat(sale -> 
            sale.getId().equals(saleId) &&
            sale.getStatus() == SaleStatusEnum.FINALIZED
        ));
    }

    @Test
    void shouldThrowExceptionWhenSaleNotFound() {
        // Arrange
        Long saleId = 1L;
        Sale inputSale = new Sale();
        inputSale.setId(saleId);

        when(findSaleByIdInputPort.execute(saleId)).thenThrow(new RuntimeException("Sale not found"));

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            finalizeSaleUseCase.execute(inputSale);
        });

        verify(findSaleByIdInputPort, times(1)).execute(saleId);
        verify(saveSaleOutputPort, never()).execute(any());
    }
}