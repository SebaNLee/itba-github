package br.com.tmvolpato.ms;

import br.com.tmvolpato.ms.application.core.usecase.*;
import br.com.tmvolpato.ms.application.core.domain.Sale;
import br.com.tmvolpato.ms.application.core.domain.User;
import br.com.tmvolpato.ms.application.core.domain.enums.*;
import br.com.tmvolpato.ms.application.ports.in.FindUserByIdInputPort;
import br.com.tmvolpato.ms.application.ports.out.SavePaymentOutputPort;
import br.com.tmvolpato.ms.application.ports.out.SendFailedPaymentOutputPort;
import br.com.tmvolpato.ms.application.ports.out.SendValidatedPaymentOutputPort;
import br.com.tmvolpato.ms.application.ports.out.UpdateUserOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class SalePaymentUseCaseTest {

    @Mock
    private FindUserByIdInputPort findUserByIdInputPort;
    @Mock
    private UpdateUserOutputPort updateUserOutputPort;
    @Mock
    private SavePaymentOutputPort savePaymentOutputPort;
    @Mock
    private SendValidatedPaymentOutputPort sendValidatedPaymentOutputPort;
    @Mock
    private SendFailedPaymentOutputPort sendFailedPaymentOutputPort;

    private SalePaymentUseCase salePaymentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        salePaymentUseCase = new SalePaymentUseCase(
                findUserByIdInputPort,
                updateUserOutputPort,
                savePaymentOutputPort,
                sendValidatedPaymentOutputPort,
                sendFailedPaymentOutputPort
        );
    }

    @Test
    void shouldProcessPaymentSuccessfully() {
        // Arrange
        Long saleId = 1L;
        Long userId = 1L;
        Long productId = 1L;
        BigDecimal saleValue = new BigDecimal("50.00");
        int quantity = 1;
        User user = new User(userId, "John Doe", new BigDecimal("100.00"));
        Sale sale = new Sale(saleId, userId, productId, saleValue, SaleStatusEnum.PENDING, quantity);

        when(findUserByIdInputPort.execute(userId)).thenReturn(user);

        // Act
        salePaymentUseCase.execute(sale);

        // Assert
        verify(findUserByIdInputPort, times(1)).execute(userId);
        verify(updateUserOutputPort, times(1)).execute(user);
        verify(savePaymentOutputPort, times(1)).execute(any());
        verify(sendValidatedPaymentOutputPort, times(1)).execute(sale, SaleEventEnum.VALIDATED_PAYMENT);
        verify(sendFailedPaymentOutputPort, never()).execute(any(), any());
    }

    @Test
    void shouldFailPaymentWhenInsufficientBalance() {
        // Arrange
        Long saleId = 1L;
        Long userId = 1L;
        Long productId = 1L;
        BigDecimal saleValue = new BigDecimal("150.00");
        int quantity = 1;
        User user = new User(userId, "John Doe", new BigDecimal("100.00"));
        Sale sale = new Sale(saleId, userId, productId, saleValue, SaleStatusEnum.PENDING, quantity);

        when(findUserByIdInputPort.execute(userId)).thenReturn(user);

        // Act
        salePaymentUseCase.execute(sale);

        // Assert
        verify(findUserByIdInputPort, times(1)).execute(userId);
        verify(updateUserOutputPort, never()).execute(any());
        verify(savePaymentOutputPort, never()).execute(any());
        verify(sendValidatedPaymentOutputPort, never()).execute(any(), any());
        verify(sendFailedPaymentOutputPort, times(1)).execute(sale, SaleEventEnum.FAILED_PAYMENT);
    }
}