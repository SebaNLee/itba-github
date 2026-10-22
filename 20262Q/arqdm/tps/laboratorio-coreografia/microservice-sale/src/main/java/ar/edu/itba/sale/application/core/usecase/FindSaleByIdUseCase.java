package ar.edu.itba.sale.application.core.usecase;

import ar.edu.itba.sale.application.core.domain.Sale;
import ar.edu.itba.sale.application.port.in.FindSaleByIdInputPort;
import ar.edu.itba.sale.application.port.out.FindSaleByIdOutputPort;

public class FindSaleByIdUseCase implements FindSaleByIdInputPort {

    private final FindSaleByIdOutputPort findSaleByIdOutputPort;

    public FindSaleByIdUseCase(final FindSaleByIdOutputPort findSaleByIdOutputPort) {
        this.findSaleByIdOutputPort = findSaleByIdOutputPort;
    }

    @Override
    public Sale execute(final Long id) {
        return this.findSaleByIdOutputPort.execute(id)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found."));
    }
}
