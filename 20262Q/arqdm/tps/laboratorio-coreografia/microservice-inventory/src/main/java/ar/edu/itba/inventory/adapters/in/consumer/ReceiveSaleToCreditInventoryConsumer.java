package ar.edu.itba.inventory.adapters.in.consumer;

import ar.edu.itba.inventory.adapters.out.message.SaleMessage;
import ar.edu.itba.inventory.application.core.domain.enums.SaleEventEnum;
import ar.edu.itba.inventory.application.ports.in.CreditInventoryInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiveSaleToCreditInventoryConsumer {

    private CreditInventoryInputPort creditInventoryInputPort;

    @KafkaListener(topics = "tp-saga-sale", groupId = "inventory-credit")
    public void execute(final SaleMessage saleMessage) {
        if (SaleEventEnum.FAILED_PAYMENT.equals(saleMessage.getSaleEventEnum())) {
            log.info("ReceiveSaleToCreditInventoryConsumer - Start credit inventory separation");

            this.creditInventoryInputPort.execute(saleMessage.getSale());
        }
    }

}
