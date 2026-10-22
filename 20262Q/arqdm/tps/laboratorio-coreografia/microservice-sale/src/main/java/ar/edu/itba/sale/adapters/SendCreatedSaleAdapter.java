package ar.edu.itba.sale.adapters;

import ar.edu.itba.sale.adapters.out.message.SaleMessage;
import ar.edu.itba.sale.application.core.domain.Sale;
import ar.edu.itba.sale.application.core.domain.enums.SaleEventEnum;
import ar.edu.itba.sale.application.port.out.SendCreateSaleOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendCreatedSaleAdapter implements SendCreateSaleOutputPort {

    private final KafkaTemplate<String, SaleMessage> kafkaTemplate;

    @Override
    public void execute(final Sale sale, final SaleEventEnum saleEventEnum) {
        log.info("SendCreatedSaleAdapter - Send created sale to kafka event: {}", saleEventEnum);

        var key = UUID.randomUUID().toString();
        final var saleMessage = new SaleMessage(sale, saleEventEnum);
        this.kafkaTemplate.send("tp-saga-sale", key, saleMessage);
    }
}
