package ar.edu.itba.inventory.adapters.out;

import ar.edu.itba.inventory.adapters.out.message.SaleMessage;
import ar.edu.itba.inventory.application.core.domain.Sale;
import ar.edu.itba.inventory.application.core.domain.enums.SaleEventEnum;
import ar.edu.itba.inventory.application.ports.out.SendFailedInventoryOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendFailedInventoryAdapter implements SendFailedInventoryOutputPort {

    private final KafkaTemplate<String, SaleMessage> kafkaTemplate;

    @Override
    public void execute(final Sale sale, final SaleEventEnum saleEventEnum) {
        log.info("SendFailedInventoryAdapter - Send failed inventory");

        var key = UUID.randomUUID().toString();
        final var saleMessage = new SaleMessage(sale, saleEventEnum);
        this.kafkaTemplate.send("tp-saga-sale", key, saleMessage);
    }
}
