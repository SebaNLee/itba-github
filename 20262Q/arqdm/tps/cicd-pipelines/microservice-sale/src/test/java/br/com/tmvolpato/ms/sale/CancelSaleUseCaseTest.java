package br.com.tmvolpato.ms.sale;

import br.com.tmvolpato.ms.sale.application.core.domain.Sale;
import br.com.tmvolpato.ms.sale.application.core.usecase.*;
import br.com.tmvolpato.ms.sale.application.core.domain.enums.SaleStatusEnum;
import br.com.tmvolpato.ms.sale.application.port.in.FindSaleByIdInputPort;
import br.com.tmvolpato.ms.sale.application.port.out.SaveSaleOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class CancelSaleUseCaseTest {

    @Mock
    private FindSaleByIdInputPort findSaleByIdInputPort;

    @Mock
    private SaveSaleOutputPort saveSaleOutputPort;

    private CancelSaleUseCase cancelSaleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cancelSaleUseCase = new CancelSaleUseCase(findSaleByIdInputPort, saveSaleOutputPort);
    }

    @Test
    void shouldCancelSaleSuccessfully() {
        // Arrange
        Long saleId = 1L;
        Sale initialSale = new Sale();
        initialSale.setId(saleId);
        initialSale.setUserId(1L);
        initialSale.setProductId(1L);
        initialSale.setValue(BigDecimal.TEN);
        initialSale.setStatus(SaleStatusEnum.PENDING);
        initialSale.setQuantity(1);

        when(findSaleByIdInputPort.execute(saleId)).thenReturn(initialSale);

        // Act
        cancelSaleUseCase.execute(initialSale);

        // Assert
        verify(findSaleByIdInputPort, times(1)).execute(saleId);
        verify(saveSaleOutputPort, times(1)).execute(argThat(sale -> 
            sale.getId().equals(saleId) && sale.getStatus() == SaleStatusEnum.CANCELED
        ));
    }
}