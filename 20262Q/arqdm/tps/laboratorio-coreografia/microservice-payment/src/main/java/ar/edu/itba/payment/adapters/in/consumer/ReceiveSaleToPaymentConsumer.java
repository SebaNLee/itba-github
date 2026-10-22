package ar.edu.itba.payment.adapters.in.consumer;

import ar.edu.itba.payment.adapters.out.message.SaleMessage;
import ar.edu.itba.payment.application.core.domain.enums.SaleEventEnum;
import ar.edu.itba.payment.application.ports.in.SalePaymentInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiveSaleToPaymentConsumer {

    private final SalePaymentInputPort salePaymentInputPort;

    @KafkaListener(topics = "tp-saga-sale", groupId = "payment")
    public void execute(final SaleMessage saleMessage) {
        if (SaleEventEnum.UPDATED_INVENTORY.equals(saleMessage.getSaleEventEnum())) {
            log.info("ReceiveSaleToPaymentConsumer - Start payment");

            this.salePaymentInputPort.execute(saleMessage.getSale());
        }
    }

}
