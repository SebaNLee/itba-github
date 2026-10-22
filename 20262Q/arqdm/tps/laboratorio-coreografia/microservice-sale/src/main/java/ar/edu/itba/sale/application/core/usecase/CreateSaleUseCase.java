package ar.edu.itba.sale.application.core.usecase;

import ar.edu.itba.sale.application.core.domain.Sale;
import ar.edu.itba.sale.application.core.domain.enums.SaleEventEnum;
import ar.edu.itba.sale.application.port.in.CreateSaleInputPort;
import ar.edu.itba.sale.application.port.out.SaveSaleOutputPort;
import ar.edu.itba.sale.application.port.out.SendCreateSaleOutputPort;

public class CreateSaleUseCase implements CreateSaleInputPort {

    private final SaveSaleOutputPort saveSaleOutputPort;
    private final SendCreateSaleOutputPort sendCreateSaleOutputPort;

    public CreateSaleUseCase(SaveSaleOutputPort saveSaleOutputPort, SendCreateSaleOutputPort sendCreateSaleOutputPort) {
        this.saveSaleOutputPort = saveSaleOutputPort;
        this.sendCreateSaleOutputPort = sendCreateSaleOutputPort;
    }

    @Override
    public void execute(final Sale sale) {
        sale.statusInitial();
        final var saleSaved = this.saveSaleOutputPort.execute(sale);
        this.sendCreateSaleOutputPort.execute(saleSaved, SaleEventEnum.CREATED_SALE);
    }
}
