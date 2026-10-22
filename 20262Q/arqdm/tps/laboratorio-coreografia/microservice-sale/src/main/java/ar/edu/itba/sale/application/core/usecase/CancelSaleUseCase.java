package ar.edu.itba.sale.application.core.usecase;

import ar.edu.itba.sale.application.core.domain.Sale;
import ar.edu.itba.sale.application.port.in.CancelSaleInputPort;
import ar.edu.itba.sale.application.port.in.FindSaleByIdInputPort;
import ar.edu.itba.sale.application.port.out.SaveSaleOutputPort;

public class CancelSaleUseCase implements CancelSaleInputPort {

    private final FindSaleByIdInputPort findSaleByIdInputPort;
    private final SaveSaleOutputPort saveSaleOutputPort;

    public CancelSaleUseCase(final FindSaleByIdInputPort findSaleByIdInputPort,
                             final SaveSaleOutputPort saveSaleOutputPort) {
        this.findSaleByIdInputPort = findSaleByIdInputPort;
        this.saveSaleOutputPort = saveSaleOutputPort;
    }

    @Override
    public void execute(final Sale sale) {
        var saleResponse = this.findSaleByIdInputPort.execute(sale.getId());
        saleResponse.statusCanceled();

        this.saveSaleOutputPort.execute(saleResponse);
    }
}
