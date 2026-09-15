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

import static org.mockito.Mockito.*;


class CreateSaleUseCaseTest {

    @Mock
    private SaveSaleOutputPort saveSaleOutputPort;

    @Mock
    private SendCreateSaleOutputPort sendCreateSaleOutputPort;

    private CreateSaleUseCase createSaleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createSaleUseCase = new CreateSaleUseCase(saveSaleOutputPort, sendCreateSaleOutputPort);
    }

    @Test
    void shouldCreateSaleSuccessfully() {
        // Arrange
        Sale inputSale = new Sale();
        inputSale.setUserId(1L);
        inputSale.setProductId(1L);
        inputSale.setValue(BigDecimal.TEN);
        inputSale.setQuantity(1);

        Sale savedSale = new Sale();
        savedSale.setId(1L);
        savedSale.setUserId(1L);
        savedSale.setProductId(1L);
        savedSale.setValue(BigDecimal.TEN);
        savedSale.setStatus(SaleStatusEnum.PENDING);
        savedSale.setQuantity(1);

        when(saveSaleOutputPort.execute(any(Sale.class))).thenReturn(savedSale);

        // Act
        createSaleUseCase.execute(inputSale);

        // Assert
        verify(saveSaleOutputPort, times(1)).execute(argThat(sale -> 
            sale.getStatus() == SaleStatusEnum.PENDING &&
            sale.getUserId().equals(1L) &&
            sale.getProductId().equals(1L) &&
            sale.getValue().equals(BigDecimal.TEN) &&
            sale.getQuantity() == 1
        ));
        verify(sendCreateSaleOutputPort, times(1)).execute(eq(savedSale), eq(SaleEventEnum.CREATED_SALE));
    }
}