package ar.edu.itba.inventory.adapters.in.consumer;

import ar.edu.itba.inventory.adapters.out.message.SaleMessage;
import ar.edu.itba.inventory.application.core.domain.enums.SaleEventEnum;
import ar.edu.itba.inventory.application.ports.in.DebitInventoryInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiveSaleToDebitInventoryConsumer {

    private final DebitInventoryInputPort debitInventoryInputPort;

    @KafkaListener(topics = "tp-saga-sale", groupId = "inventory-debit")
    public void receive(final SaleMessage saleMessage) {
        if (SaleEventEnum.CREATED_SALE.equals(saleMessage.getSaleEventEnum())) {
            log.info("ReceiveSaleToDebitInventoryConsumer - Start inventory separation");

            this.debitInventoryInputPort.execute(saleMessage.getSale());
        }
    }
}
