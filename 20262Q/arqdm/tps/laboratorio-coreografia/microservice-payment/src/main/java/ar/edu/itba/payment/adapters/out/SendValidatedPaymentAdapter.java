package ar.edu.itba.payment.adapters.out;

import ar.edu.itba.payment.adapters.out.message.SaleMessage;
import ar.edu.itba.payment.application.core.domain.Sale;
import ar.edu.itba.payment.application.core.domain.enums.SaleEventEnum;
import ar.edu.itba.payment.application.ports.out.SendValidatedPaymentOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendValidatedPaymentAdapter implements SendValidatedPaymentOutputPort {

    private final KafkaTemplate<String, SaleMessage> kafkaTemplate;

    @Override
    public void execute(final Sale sale, final SaleEventEnum saleEventEnum) {
        log.info("SendValidatedPaymentAdapter - Sale event: [{}]", saleEventEnum);

        var key = UUID.randomUUID().toString();
        var saleMessage = new SaleMessage(sale, saleEventEnum);
        this.kafkaTemplate.send("tp-saga-sale", key, saleMessage);
    }
}
